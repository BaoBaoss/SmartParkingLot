package com.cetuer.smartparkinglot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.databinding.IbeaconItemBinding;

import java.util.List;

/**
 * Created by Cetuer on 2021/10/1 16:06.
 */
public class IBeaconAdapter extends RecyclerView.Adapter<IBeaconAdapter.IBeaconViewHolder> {
    private List<BleDevice> mList;
    private Context mContext;

    public IBeaconAdapter(Context context, List<BleDevice> list) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public IBeaconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IBeaconViewHolder(DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.ibeacon_item, parent, false));
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
