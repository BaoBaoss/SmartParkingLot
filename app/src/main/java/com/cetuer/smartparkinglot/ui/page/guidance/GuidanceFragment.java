package com.cetuer.smartparkinglot.ui.page.guidance;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.bluetooth.BleManager;
import com.cetuer.smartparkinglot.databinding.FragmentGuidanceBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.adapter.IBeaconAdapter;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.CalculateUtil;
import com.cetuer.smartparkinglot.utils.GpsUtils;
import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.ToastUtils;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GuidanceFragment extends BaseFragment<FragmentGuidanceBinding> {

    private GuidanceViewModel mState;
    private SharedViewModel mEvent;
    private boolean mOpenBluetooth;
    private boolean mOpenGps;
    private IBeaconAdapter mIBeaconAdapter;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(GuidanceViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_guidance, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPermission();
        mBinding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        BleManager.getInstance().getScanDeviceEvent().observe(this.mActivity, bleDevices -> {
            if(mIBeaconAdapter == null) {
                mIBeaconAdapter = new IBeaconAdapter(getContext(), bleDevices);
                mBinding.list.setAdapter(mIBeaconAdapter);
                return;
            }
            mIBeaconAdapter.notifyDataSetChanged();
        });
        mEvent.isOpenBluetooth().observe(mActivity, openBluetooth -> {
            mOpenBluetooth = openBluetooth;
            controlBluetooth();
        });
        mEvent.isOpenGPS().observe(mActivity, openGps -> {
            mOpenGps = openGps;
            controlBluetooth();
        });
        getObserve();
    }

    private void getObserve() {
        mState.getText().observe(getViewLifecycleOwner(), s -> {

        });
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .onForwardToSettings((scope, deniedList) -> scope.showForwardToSettingsDialog(deniedList, "您永久拒绝了以下权限，是否需要重新打开？", "设置", "取消"))
                .onExplainRequestReason((scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList, "为了获取蓝牙定位需要如下权限", "确定", "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        ToastUtils.showShortToast(this.getContext(), "权限被拒绝，无法搜索蓝牙");
                    } else {
                        boolean isOpenBlueTooth = BleManager.getInstance().isBlueEnable();
                        boolean isOpenGps = GpsUtils.checkLocation(mActivity);
                        mEvent.isOpenBluetooth().setValue(isOpenBlueTooth);
                        mEvent.isOpenGPS().setValue(isOpenGps);
                        if(!isOpenBlueTooth) {
                            BleManager.getInstance().showOpenToothDialog();
                        }
                        if(!isOpenGps) {
                            ToastUtils.showShortToast(mActivity, "需要打开位置权限才可以搜索到蓝牙设备");
                        }
                    }
                });
    }

    /**
     * 控制蓝牙开启或关闭
     */
    public void controlBluetooth() {
        if (mOpenBluetooth
                && mOpenGps
                && PermissionX.isGranted(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                && PermissionX.isGranted(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            BleManager.getInstance().refreshScanner();
            BleManager.getInstance().scanByFilter();
        }
        if (!mOpenBluetooth || !mOpenGps) {
            BleManager.getInstance().stopScan();
        }
    }
}