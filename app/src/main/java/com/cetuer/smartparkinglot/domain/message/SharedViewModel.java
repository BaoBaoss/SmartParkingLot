package com.cetuer.smartparkinglot.domain.message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Cetuer on 2021/9/13 21:51.
 * 跨页面共享ViewModel
 */
public class SharedViewModel extends ViewModel {
    /**
     * 是否打开蓝牙
     */
    private final MutableLiveData<Boolean> openBluetooth = new MutableLiveData<>();

    /**
     * 是否打开GPS
     */
    private final MutableLiveData<Boolean> openGPS = new MutableLiveData<>();

    public MutableLiveData<Boolean> isOpenBluetooth() {
        return openBluetooth;
    }

    public MutableLiveData<Boolean> isOpenGPS() {
        return openGPS;
    }
}
