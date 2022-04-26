package com.cetuer.smartparkinglot.ui.page.carport_query;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.data.request.ParkingLotRequest;

import java.util.List;

public class CarportQueryViewModel extends ViewModel {
    /**
     * 停车场请求
     */
    public final ParkingLotRequest parkingLotRequest = new ParkingLotRequest();
    public MutableLiveData<List<ParkingLot>> parkingLotList = new MutableLiveData<>();
}