package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.Notice;
import com.cetuer.smartparkinglot.data.response.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cetuer on 2022/4/18 22:57.
 * TODO()
 */
public interface NoticeService {
    /**
     * 根据停车场编号获取公告
     * @param parkingId 停车场编号
     */
    @GET("parking-app/notice/listByParking")
    Call<ResultData<List<Notice>>> listByParking(@Query("parkingLotId") Integer parkingId);
}
