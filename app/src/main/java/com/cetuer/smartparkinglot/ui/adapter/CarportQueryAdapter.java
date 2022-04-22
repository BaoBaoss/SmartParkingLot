package com.cetuer.smartparkinglot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.databinding.ItemParkingSpaceBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cetuer on 2021/10/1 16:06.
 * 车位查询列表适配器
 */
public class CarportQueryAdapter extends ListAdapter<ParkingLot, CarportQueryAdapter.CarportQueryViewHolder> {
    private final Context mContext;

    public CarportQueryAdapter(Context context) {
        super(new DiffUtil.ItemCallback<ParkingLot>() {
            @Override
            public boolean areItemsTheSame(@NonNull ParkingLot oldItem, @NonNull ParkingLot newItem) {
                return oldItem.getId() != null && oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ParkingLot oldItem, @NonNull ParkingLot newItem) {
                return oldItem.getName() != null && oldItem.getName().equals(newItem.getName());
            }
        });
        this.mContext = context;
    }

    public void submitList(List<ParkingLot> list) {
        super.submitList(list == null ? new ArrayList<>() : new ArrayList<>(list));
    }

    @NonNull
    @Override
    public CarportQueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_parking_space, parent, false);
        return new CarportQueryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CarportQueryViewHolder holder, int position) {
        ItemParkingSpaceBinding binding = DataBindingUtil.getBinding(holder.itemView);
        if (binding != null) {
            binding.setParkingLot(getItem(position));
            binding.executePendingBindings();
        }
    }


    public static class CarportQueryViewHolder extends RecyclerView.ViewHolder {
        public CarportQueryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
