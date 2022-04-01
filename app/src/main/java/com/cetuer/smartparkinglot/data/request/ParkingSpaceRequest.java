package com.cetuer.smartparkinglot.data.request;

import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.data.bean.ParkingSpace;
import com.cetuer.smartparkinglot.data.repository.DataRepository;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/6 21:49.
 * 车位 Request
 */
public class ParkingSpaceRequest implements BaseRequest {

    private final MutableLiveData<List<ParkingSpace>> parkingSpaceList = new MutableLiveData<>();

    /**
     * 请求车位列表
     */
    public void requestParkingSpace(Integer parkingLotId) {
        DataRepository.getInstance().parkingSpaceByParkingId(parkingSpaceList::postValue, parkingLotId);
    }

    public MutableLiveData<List<ParkingSpace>> getParkingSpaceList() {
        return parkingSpaceList;
    }
}
