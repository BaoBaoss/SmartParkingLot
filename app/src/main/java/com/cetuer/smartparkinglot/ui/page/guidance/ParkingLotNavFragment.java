package com.cetuer.smartparkinglot.ui.page.guidance;

import android.Manifest;
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
import com.cetuer.smartparkinglot.databinding.FragmentParkingLotNavBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.KLog;
import com.permissionx.guolindev.PermissionX;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 停车场导航
 */
public class ParkingLotNavFragment extends BaseFragment<FragmentParkingLotNavBinding> {

    private ParkingLotNavFragmentViewModel mState;
    private SharedViewModel mEvent;
    private boolean mOpenBluetooth;
    private boolean mOpenGps;
    //扫描十次定位一次
    private int scanCount = 10;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(ParkingLotNavFragmentViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_parking_lot_nav, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mState.parkingLotRequest.requestParkingIdByLatLng(getArguments().getDouble("longitude"), getArguments().getDouble("latitude"));
        mState.parkingLotRequest.getParkingLotId().observe(getViewLifecycleOwner(), parkingLotId -> {
            mEvent.openBluetooth.observe(getViewLifecycleOwner(), openBluetooth -> {
                mOpenBluetooth = openBluetooth;
                requestBeaconList(parkingLotId);
            });
            mEvent.openGPS.observe(getViewLifecycleOwner(), openGps -> {
                mOpenGps = openGps;
                requestBeaconList(parkingLotId);
            });
        });
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
            mState.mText.set("坐标为：(" + point.getX() + "," + point.getY() + ")");
        });
    }

    /**
     * 获取此停车场蓝牙mac地址
     *
     * @param parkingLotId 停车场id
     */
    public void requestBeaconList(Integer parkingLotId) {
        if (mOpenBluetooth
                && mOpenGps
                && PermissionX.isGranted(this.mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                && PermissionX.isGranted(this.mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mEvent.beaconRequest.requestBeaconList(parkingLotId);
        }
    }
}