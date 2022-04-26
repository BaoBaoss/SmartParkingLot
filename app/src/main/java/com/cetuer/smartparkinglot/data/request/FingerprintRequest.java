package com.cetuer.smartparkinglot.data.request;

import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/19 19:39.
 * 指纹请求
 */
public class FingerprintRequest implements BaseRequest {
    /**
     * 定位坐标
     */
    private final UnPeekLiveData<BeaconPoint> locationPoint =  new UnPeekLiveData<>();

    /**
     * 请求定位
     * @param RSSIs 信标强度列表
     */
    public void requestLocation(List<BeaconRssi> RSSIs) {
        DataRepository.getInstance().location(locationPoint::postValue, RSSIs);
    }
    public UnPeekLiveData<BeaconPoint> getLocationPoint() {
        return locationPoint;
    }

}
