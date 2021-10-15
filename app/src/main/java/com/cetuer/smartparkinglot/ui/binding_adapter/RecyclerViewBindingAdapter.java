package com.cetuer.smartparkinglot.ui.binding_adapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Cetuer on 2021/10/12 15:44.
 */
public class RecyclerViewBindingAdapter {
    @BindingAdapter(
            value = {"adapter"},
            requireAll = false
    )
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(
            value = {"submitList"},
            requireAll = false
    )
    public static void submitList(RecyclerView recyclerView, List list) {
        if (recyclerView.getAdapter() != null) {
            ListAdapter adapter = (ListAdapter)recyclerView.getAdapter();
            adapter.submitList(list);
        }

    }
}
