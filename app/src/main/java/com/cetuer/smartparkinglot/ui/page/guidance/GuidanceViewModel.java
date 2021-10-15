package com.cetuer.smartparkinglot.ui.page.guidance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.bluetooth.BleDevice;

import java.util.List;

public class GuidanceViewModel extends ViewModel {

    public final MutableLiveData<List<BleDevice>> list = new MutableLiveData<>();
}