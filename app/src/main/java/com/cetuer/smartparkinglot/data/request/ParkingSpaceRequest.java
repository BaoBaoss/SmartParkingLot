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
    private final UnPeekLiveData<Void> findCar = new UnPeekLiveData<>();
    private final UnPeekLiveData<ParkingSpace> carSpace = new UnPeekLiveData<>();


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

    /**
     * 请求停车车位
     */
    public void requestCarSpace() {
        DataRepository.getInstance().carSpace(carSpace::postValue);
    }

    /**
     * 请求寻车
     * @param spaceId 之前车位id
     */
    public void requestFindCar(Integer spaceId) {
        DataRepository.getInstance().findCar(findCar::postValue, spaceId);
    }

    public UnPeekLiveData<List<ParkingSpace>> getParkingSpaceList() {
        return parkingSpaceList;
    }

    public UnPeekLiveData<Void> getParking() {
        return parking;
    }

    public UnPeekLiveData<ParkingSpace> getCarSpace() {
        return carSpace;
    }

    public UnPeekLiveData<Void> getFindCar() {
        return findCar;
    }
}
