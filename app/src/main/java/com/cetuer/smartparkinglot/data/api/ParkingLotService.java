package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.data.response.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by Cetuer on 2022/3/26 23:51.
 * 停车场服务
 */
public interface ParkingLotService {
    /**
     * 获取停车场列表
     * @return 停车场列表
     */
    @GET("parkingLot/list")
    Call<ResultData<List<ParkingLot>>> list();
}
