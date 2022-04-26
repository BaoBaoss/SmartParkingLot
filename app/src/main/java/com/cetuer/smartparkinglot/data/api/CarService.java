package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.Car;
import com.cetuer.smartparkinglot.data.response.ResultData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Cetuer on 2022/4/23 16:22.
 * 车辆服务
 */
public interface CarService {
    /**
     * 新增或修改车辆信息
     * @param car 车辆信息
     */
    @POST("parking-app/car/addOrUpdate")
    Call<ResultData<Void>> addOrEditCar(@Body Car car);

    /**
     * 获取车辆信息
     */
    @GET("parking-app/car/getInfo")
    Call<ResultData<Car>> getInfo();

    /**
     * 是否可以停车
     */
    @GET("parking-app/car/canParking")
    Call<ResultData<Boolean>> canParking();

    /**
     * 是否可以寻车
     */
    @GET("parking-app/car/canFindCar")
    Call<ResultData<Boolean>> canFindCar();
}
