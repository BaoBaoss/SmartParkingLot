package com.cetuer.smartparkinglot.domain.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.data.request.BeaconRequest;

import java.util.List;

/**
 * Created by Cetuer on 2021/9/13 21:51.
 * 跨页面共享ViewModel
 */
public class SharedViewModel extends ViewModel {
    /**
     * 信标请求
     */
    public final BeaconRequest beaconRequest = new BeaconRequest();
    /**
     * 是否打开蓝牙
     */
    public final MutableLiveData<Boolean> openBluetooth = new MutableLiveData<>(false);

    /**
     * 是否打开GPS
     */
    public final MutableLiveData<Boolean> openGPS = new MutableLiveData<>(false);

    /**
     * 扫描到的蓝牙设备列表
     */
    public final MutableLiveData<List<BleDevice>> list = new MutableLiveData<>();

    /**
     * 需要扫描信标的mac
     */
    public final MutableLiveData<List<String>> filterMacs = new MutableLiveData<>();

    /**
     * 是否正在登录
     */
    public final MutableLiveData<Boolean> beLogin = new MutableLiveData<>();
}
