package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.data.response.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Cetuer on 2022/3/6 15:57.
 * 信标服务
 */
public interface BeaconService {

    /**
     * 获取所有信标设备信息
     * @return 信标设备信息
     */
    @GET("beacon/list")
    Call<ResultData<List<BeaconDevice>>> list();
}
