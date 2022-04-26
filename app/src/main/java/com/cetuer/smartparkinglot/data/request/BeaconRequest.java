package com.cetuer.smartparkinglot.data.request;

import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/6 21:49.
 * 信标 Request
 */
public class BeaconRequest implements BaseRequest {

    private final UnPeekLiveData<List<BeaconDevice>> beaconLiveData = new UnPeekLiveData<>();

    private final UnPeekLiveData<BeaconPoint> endPoint = new UnPeekLiveData<>();
    /**
     * 请求信标列表
     */
    public void requestBeaconList(Integer parkingLotId) {
        DataRepository.getInstance().listBeacon(beaconLiveData::postValue, parkingLotId);
    }

    /**
     * 请求终点坐标
     */
    public void requestEndPoint(Integer parkingLotId) {
        DataRepository.getInstance().endPointByParkingLotId(endPoint::postValue, parkingLotId);
    }

    public UnPeekLiveData<List<BeaconDevice>> getBeaconLiveData() {
        return beaconLiveData;
    }

    public UnPeekLiveData<BeaconPoint> getEndPoint() {
        return endPoint;
    }
}
