package com.cetuer.smartparkinglot.ui.page.find_car;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.databinding.FragmentFindCarBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;

public class FindCarFragment extends BaseFragment<FragmentFindCarBinding> {

    private FindCarViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(FindCarViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_find_car, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mState.getText().observe(getViewLifecycleOwner(), s -> {

        });
    }
}