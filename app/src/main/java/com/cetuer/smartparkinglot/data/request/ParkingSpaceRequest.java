package com.cetuer.smartparkinglot.data.request;

import com.cetuer.smartparkinglot.data.bean.ParkingSpace;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/6 21:49.
 * 车位 Request
 */
public class ParkingSpaceRequest implements BaseRequest {

    private final UnPeekLiveData<List<ParkingSpace>> parkingSpaceList = new UnPeekLiveData<>();
    private final UnPeekLiveData<Void> parking = new UnPeekLiveData<>();


    /**
     * 请求车位列表
     */
    public void requestParkingSpace(Integer parkingLotId) {
        DataRepository.getInstance().parkingSpaceByParkingId(parkingSpaceList::postValue, parkingLotId);
    }

    /**
     * 请求停车
     */
    public void requestParking(Integer spaceId) {
        DataRepository.getInstance().parking(parking::postValue, spaceId);
    }

    public UnPeekLiveData<List<ParkingSpace>> getParkingSpaceList() {
        return parkingSpaceList;
    }

    public UnPeekLiveData<Void> getParking() {
        return parking;
    }
}
