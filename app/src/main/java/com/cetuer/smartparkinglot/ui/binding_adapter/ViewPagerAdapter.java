package com.cetuer.smartparkinglot.ui.binding_adapter;

import androidx.databinding.BindingAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Created by Cetuer on 2021/9/7 21:45.
 */
public class ViewPagerAdapter {
    @BindingAdapter("bindPageChangeListener")
    public static void bindPageChangeListener(ViewPager2 viewPager, ViewPager2.OnPageChangeCallback callback) {
        viewPager.registerOnPageChangeCallback(callback);
    }
}
