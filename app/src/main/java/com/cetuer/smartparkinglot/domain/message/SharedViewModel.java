package com.cetuer.smartparkinglot.domain.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.bluetooth.BleDevice;
import com.cetuer.smartparkinglot.data.request.BeaconRequest;
import com.cetuer.smartparkinglot.data.request.FingerprintRequest;
import com.cetuer.smartparkinglot.data.request.MemberRequest;
import com.cetuer.smartparkinglot.data.request.NoticeRequest;
import com.cetuer.smartparkinglot.data.request.ParkingLotRequest;
import com.cetuer.smartparkinglot.data.request.ParkingSpaceRequest;

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
     * 指纹定位请求
     */
    public final FingerprintRequest fingerprintRequest = new FingerprintRequest();
    /**
     * 用户请求
     */
    public final MemberRequest memberRequest = new MemberRequest();
    /**
     * 停车场请求
     */
    public final ParkingLotRequest parkingLotRequest = new ParkingLotRequest();
    /**
     * 停车位请求
     */
    public final ParkingSpaceRequest parkingSpaceRequest = new ParkingSpaceRequest();
    /**
     * 公告请求
     */
    public final NoticeRequest noticeRequest = new NoticeRequest();
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
