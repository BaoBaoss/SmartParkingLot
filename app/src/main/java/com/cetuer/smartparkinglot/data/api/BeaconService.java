package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.data.response.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Cetuer on 2022/3/6 15:57.
 * 信标服务
 */
public interface BeaconService {

    /**
     * 根据停车场编号获取其所有信标设备信息
     * @param parkingLotId 停车场编号
     * @return 信标设备信息
     */
    @GET("beacon/listByParkingLotId/{parkingLotId}")
    Call<ResultData<List<BeaconDevice>>> listByParkingLotId(@Path("parkingLotId") Integer parkingLotId);
}
