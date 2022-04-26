package com.cetuer.smartparkinglot.ui.page.find_car;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.databinding.FragmentFindCarBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;

public class FindCarFragment extends BaseFragment<FragmentFindCarBinding> {

    private FindCarViewModel mState;
    private SharedViewModel mEvent;


    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(FindCarViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_find_car, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //是否可寻车
        mState.carRequest.canFindCar();
        mState.carRequest.getCanFindCar().observe(this.mActivity, canFindCar -> {
            if(!canFindCar) {
                DialogUtils.showBasicDialogNoCancel(this.mActivity, "提示", "无法寻车，没有车或者未停车").show();
            }
        });
    }
}