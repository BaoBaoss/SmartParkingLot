package com.cetuer.smartparkinglot.ui.page.find_car;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.databinding.FragmentFindCarBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.KLog;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindCarFragment extends BaseFragment<FragmentFindCarBinding> {

    private FindCarViewModel mState;
    private SharedViewModel mEvent;
    private int scanCount = 10;

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
        mEvent.list.observe(getViewLifecycleOwner(), bleDevices -> {
            if(scanCount < 10) {
                scanCount++;
                return;
            }
            scanCount = 0;
            List<BeaconRssi> RSSIs = bleDevices.stream().map(ble -> new BeaconRssi(ble.getDevice().getAddress(), ble.getRssi().doubleValue())).collect(Collectors.toList());
            mState.fingerprintRequest.requestLocation(RSSIs);
        });
        mState.fingerprintRequest.getLocationPoint().observe(getViewLifecycleOwner(), point -> {
            mState.getText().setValue("坐标为：(" + point.getX() + "," + point.getY() + ")");
        });
        mState.getText().observe(getViewLifecycleOwner(), s -> {

        });
    }
}