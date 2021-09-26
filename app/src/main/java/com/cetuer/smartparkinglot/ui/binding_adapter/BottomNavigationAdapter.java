package com.cetuer.smartparkinglot.ui.binding_adapter;

import androidx.databinding.BindingAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Created by Cetuer on 2021/9/7 22:18.
 */
public class BottomNavigationAdapter {
    @BindingAdapter("bindItemSelectedListener")
    public static void bindItemSelectedListener(BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }
}
