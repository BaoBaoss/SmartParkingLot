package com.cetuer.smartparkinglot.data.request;

import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.data.repository.DataRepository;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/6 21:49.
 * 信标 Request
 */
public class BeaconRequest implements BaseRequest {

    private final MutableLiveData<List<BeaconDevice>> beaconLiveData = new MutableLiveData<>();

    /**
     * 请求信标列表
     */
    public void requestBeaconList(Integer parkingLotId) {
        DataRepository.getInstance().listBeacon(beaconLiveData::postValue, parkingLotId);
    }
    public MutableLiveData<List<BeaconDevice>> getBeaconLiveData() {
        return beaconLiveData;
    }
}
