package com.cetuer.smartparkinglot.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.databinding.IbeaconItemBinding;
import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.MaterialDialogUtils;

import java.util.List;

/**
 * Created by Cetuer on 2021/10/1 16:06.
 */
public class IBeaconAdapter extends RecyclerView.Adapter<IBeaconAdapter.IBeaconViewHolder> {
    private final List<BleDevice> mList;
    private final AppCompatActivity mActivity;

    public IBeaconAdapter(AppCompatActivity activity, List<BleDevice> list) {
        this.mList = list;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public IBeaconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IBeaconViewHolder holder = new IBeaconViewHolder(DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.ibeacon_item, parent, false));
        holder.itemView.setOnClickListener(v -> {
            MaterialDialogUtils.showBasicDialog(mActivity, "提示", "是否设置参数？")
                    .onPositive((dialog, which) -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt("devicePosition", holder.getBindingAdapterPosition());
                        //mActivity.getSupportFragmentManager()
                    })
                    .show();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IBeaconViewHolder holder, int position) {
        holder.binding.setBleDevice(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class IBeaconViewHolder extends RecyclerView.ViewHolder {
        public IbeaconItemBinding binding;
        public IBeaconViewHolder(IbeaconItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
