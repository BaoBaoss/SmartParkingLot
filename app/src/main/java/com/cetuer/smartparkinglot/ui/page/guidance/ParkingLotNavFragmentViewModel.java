package com.cetuer.smartparkinglot.ui.page.guidance;

import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.BeaconRequest;
import com.cetuer.smartparkinglot.data.request.FingerprintRequest;
import com.cetuer.smartparkinglot.data.request.ParkingLotRequest;
import com.cetuer.smartparkinglot.data.request.ParkingSpaceRequest;

public class ParkingLotNavFragmentViewModel extends ViewModel {
    //信标请求
    public final BeaconRequest beaconRequest = new BeaconRequest();
    //停车场请求
    public final ParkingLotRequest parkingLotRequest = new ParkingLotRequest();
    //指纹定位请求
    public final FingerprintRequest fingerprintRequest = new FingerprintRequest();
    //停车位请求
    public final ParkingSpaceRequest parkingSpaceRequest = new ParkingSpaceRequest();
}