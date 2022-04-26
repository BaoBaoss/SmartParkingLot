package com.cetuer.smartparkinglot.data.request;

import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/26 23:53.
 * 停车场请求
 */
public class ParkingLotRequest implements BaseRequest {
    private final UnPeekLiveData<List<ParkingLot>> parkingLotList = new UnPeekLiveData<>();
    private final UnPeekLiveData<Integer> parkingLotId = new UnPeekLiveData<>();

    /**
     * 请求获得停车场列表
     */
    public void requestList() {
        DataRepository.getInstance().parkingLotList(parkingLotList::postValue);
    }

    /**
     * 请求获得停车场id
     */
    public void requestParkingIdByLatLng(Double longitude, Double latitude) {
        DataRepository.getInstance().parkingIdByLatLng(parkingLotId::postValue, longitude, latitude);
    }

    public UnPeekLiveData<List<ParkingLot>> getParkingLotList() {
        return parkingLotList;
    }

    public UnPeekLiveData<Integer> getParkingLotId() {
        return parkingLotId;
    }
}
