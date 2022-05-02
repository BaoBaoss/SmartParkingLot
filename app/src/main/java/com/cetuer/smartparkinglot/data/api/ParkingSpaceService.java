package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.ParkingSpace;
import com.cetuer.smartparkinglot.data.response.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Cetuer on 2022/3/26 23:51.
 * 停车位服务
 */
public interface ParkingSpaceService {
    /**
     * 根据停车场编号获取停车位列表
     * @param parkingLotId 停车场编号
     * @return 停车位列表
     */
    @GET("parking-app/parkingSpace/listByParkingId/{parkingLotId}")
    Call<ResultData<List<ParkingSpace>>> listByParkingId(@Path("parkingLotId") Integer parkingLotId);

    /**
     * 停车
     * @param spaceId 车位id
     * @return 无
     */
    @GET("parking-app/parkingSpace/parking")
    Call<ResultData<Void>> parking(@Query("spaceId") Integer spaceId);

    /**
     * 获取停车车位
     * @return 停车车位，如果没有则为null
     */
    @GET("parking-app/parkingSpace/carSpace")
    Call<ResultData<ParkingSpace>> carSpace();

    /**
     * 寻车
     */
    @GET("parking-app/parkingSpace/findCar")
    Call<ResultData<Void>> findCar(@Query("spaceId") Integer spaceId);
}
