package com.cetuer.smartparkinglot.ui.page.guidance;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.BeaconRequest;
import com.cetuer.smartparkinglot.data.request.FingerprintRequest;
import com.cetuer.smartparkinglot.data.request.ParkingLotRequest;

public class ParkingLotNavFragmentViewModel extends ViewModel {
    public final ObservableField<String> mText = new ObservableField<>();
    public final ParkingLotRequest parkingLotRequest = new ParkingLotRequest();
    public final FingerprintRequest fingerprintRequest = new FingerprintRequest();
}