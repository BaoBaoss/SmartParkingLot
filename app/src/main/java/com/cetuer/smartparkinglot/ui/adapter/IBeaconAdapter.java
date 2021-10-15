package com.cetuer.smartparkinglot.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.databinding.IbeaconItemBinding;
import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.MaterialDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cetuer on 2021/10/1 16:06.
 */
public class IBeaconAdapter extends ListAdapter<BleDevice, IBeaconAdapter.IBeaconViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private final Context mContext;

    public IBeaconAdapter(Context context) {
        super(new DiffUtil.ItemCallback<BleDevice>() {
            @Override
            public boolean areItemsTheSame(@NonNull BleDevice oldItem, @NonNull BleDevice newItem) {
                return oldItem.getDevice().getAddress().equals(newItem.getDevice().getAddress());
            }

            @Override
            public boolean areContentsTheSame(@NonNull BleDevice oldItem, @NonNull BleDevice newItem) {
                return oldItem.getRssi().equals(newItem.getRssi());
            }
        });
        this.mContext = context;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void submitList(List<BleDevice> list) {
        super.submitList(list == null ? new ArrayList<>() : new ArrayList<>(list));
    }

    @NonNull
    @Override
    public IBeaconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.ibeacon_item, parent, false);
        IBeaconViewHolder holder = new IBeaconViewHolder(binding.getRoot());
        holder.itemView.setOnClickListener(v -> {
            if(this.mOnItemClickListener != null) {
                int position = holder.getBindingAdapterPosition();
                this.mOnItemClickListener.onItemClick(holder.itemView.getId(), getItem(position), position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IBeaconViewHolder holder, int position) {
        IbeaconItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        if(binding != null) {
            binding.setBleDevice(getItem(position));
            binding.executePendingBindings();
        }
    }


    static class IBeaconViewHolder extends RecyclerView.ViewHolder {
        public IBeaconViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int viewId, BleDevice item, int position);
    }
}
