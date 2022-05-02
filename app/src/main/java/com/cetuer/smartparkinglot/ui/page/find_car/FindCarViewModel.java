package com.cetuer.smartparkinglot.ui.page.find_car;

import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.BeaconRequest;
import com.cetuer.smartparkinglot.data.request.CarRequest;
import com.cetuer.smartparkinglot.data.request.FingerprintRequest;
import com.cetuer.smartparkinglot.data.request.ParkingSpaceRequest;

public class FindCarViewModel extends ViewModel {
    /**
     * 车辆请求
     */
    public final CarRequest carRequest = new CarRequest();

    /**
     * 信标请求
     */
    public final BeaconRequest beaconRequest = new BeaconRequest();

    /**
     * 车位请求
     */
    public final ParkingSpaceRequest spaceRequest = new ParkingSpaceRequest();

    /**
     * 定位请求
     */
    public final FingerprintRequest fingerprintRequest = new FingerprintRequest();
}