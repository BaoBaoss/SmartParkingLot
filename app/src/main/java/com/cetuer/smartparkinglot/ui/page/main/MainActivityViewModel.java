package com.cetuer.smartparkinglot.ui.page.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.BeaconRequest;

/**
 * Created by Cetuer on 2021/9/6 16:12.
 */
public class MainActivityViewModel extends ViewModel {
    public final BeaconRequest beaconRequest = new BeaconRequest();
}
