package com.cetuer.smartparkinglot.ui.page.carport_query;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.databinding.FragmentCarportQueryBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;

public class CarportQueryFragment extends BaseFragment<FragmentCarportQueryBinding> {

    private CarportQueryViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(CarportQueryViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        return new DataBindingConfig(R.layout.fragment_carport_query, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mState.getText().observe(getViewLifecycleOwner(), s -> {

        });
    }
}