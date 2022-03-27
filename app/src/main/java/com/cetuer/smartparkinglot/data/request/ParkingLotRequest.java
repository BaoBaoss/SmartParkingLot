package com.cetuer.smartparkinglot.data.request;

import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.data.repository.DataRepository;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/26 23:53.
 * 停车场请求
 */
public class ParkingLotRequest implements BaseRequest {
    private MutableLiveData<List<ParkingLot>> parkingLotList = new MutableLiveData<>();

    /**
     * 请求获得停车场列表
     */
    public void requestList() {
        DataRepository.getInstance().parkingLotList(parkingLotList::postValue);
    }

    public MutableLiveData<List<ParkingLot>> getParkingLotList() {
        return parkingLotList;
    }
}
