package com.cetuer.smartparkinglot.ui.page.carport_query;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.bean.ParkingLot;

import java.util.List;

public class CarportQueryViewModel extends ViewModel {

    public MutableLiveData<List<ParkingLot>> parkingLotList = new MutableLiveData<>();
}