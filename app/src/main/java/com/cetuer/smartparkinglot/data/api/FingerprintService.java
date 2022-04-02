package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.data.response.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Cetuer on 2022/3/23 22:04.
 * 指纹服务
 */
public interface FingerprintService {

    /**
     * 定位
     * @param RSSIs 当前位置rssi列表
     * @return 当前位置
     */
    @POST("parking-app/fingerprint/location")
    Call<ResultData<BeaconPoint>> location(@Body List<BeaconRssi> RSSIs);
}
