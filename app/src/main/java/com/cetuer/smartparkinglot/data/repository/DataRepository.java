package com.cetuer.smartparkinglot.data.repository;

import com.cetuer.smartparkinglot.data.api.BeaconService;
import com.cetuer.smartparkinglot.data.api.FingerprintService;
import com.cetuer.smartparkinglot.data.api.MemberService;
import com.cetuer.smartparkinglot.data.api.ParkingLotService;
import com.cetuer.smartparkinglot.data.api.ParkingSpaceService;
import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.data.bean.ParkingSpace;
import com.cetuer.smartparkinglot.data.response.ResultData;
import com.cetuer.smartparkinglot.data.response.callback.BaseCallBack;
import com.cetuer.smartparkinglot.utils.DialogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cetuer on 2022/3/5 22:13.
 * 数据仓库
 */
public class DataRepository {
    /**
     * 接口地址
     */
    private static final String BASE_URL = "http://192.168.0.106:9089/";
    /**
     * 超时时间10秒
     */
    private static final int DEFAULT_TIMEOUT = 10;
    /**
     * 单例
     */
    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();
    /**
     * retrofit
     */
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    /**
     * 登录
     * @param result 回调
     * @param memberLogin 登录信息
     */
    public void login(ResultData.Result<String> result, MemberLogin memberLogin) {
        retrofit.create(MemberService.class).login(memberLogin).enqueue(new BaseCallBack<String>() {
            @Override
            public void onSuccessful(String data) {
                result.onResult(data);
            }
        });
    }
    
    /**
     * 注册
     * @param result 回调
     * @param memberInfo 会员信息
     */
    public void register(ResultData.Result<Void> result, MemberLogin memberInfo) {
        DialogUtils.showLoadingDialog();
        retrofit.create(MemberService.class).register(memberInfo).enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 网络请求终点坐标
     * @param result 回调
     * @param parkingLotId 停车场编号
     */
    public void endPointByParkingLotId(ResultData.Result<BeaconPoint> result, Integer parkingLotId) {
        DialogUtils.showLoadingDialog();
        retrofit.create(BeaconService.class).endPointByParkingLotId(parkingLotId).enqueue(new BaseCallBack<BeaconPoint>() {
            @Override
            public void onSuccessful(BeaconPoint data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 网络请求信标信息
     * @param result 回调
     * @param parkingLotId 停车场编号
     */
    public void listBeacon(ResultData.Result<List<BeaconDevice>> result, Integer parkingLotId) {
        DialogUtils.showLoadingDialog();
        retrofit.create(BeaconService.class).listByParkingLotId(parkingLotId).enqueue(new BaseCallBack<List<BeaconDevice>>() {
            @Override
            public void onSuccessful(List<BeaconDevice> data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 定位
     * @param result 回调
     * @param RSSIs 信标强度列表
     */
    public void location(ResultData.Result<BeaconPoint> result, List<BeaconRssi> RSSIs) {
        retrofit.create(FingerprintService.class).location(RSSIs).enqueue(new BaseCallBack<BeaconPoint>() {
            @Override
            public void onSuccessful(BeaconPoint data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 停车场列表
     * @param result 回调
     */
    public void parkingLotList(ResultData.Result<List<ParkingLot>> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(ParkingLotService.class).list().enqueue(new BaseCallBack<List<ParkingLot>>() {
            @Override
            public void onSuccessful(List<ParkingLot> data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 根据经纬度查询停车场id
     * @param result 返回值
     * @param longitude 经度
     * @param latitude 纬度
     */
    public void parkingIdByLatLng(ResultData.Result<Integer> result, Double longitude, Double latitude) {
        DialogUtils.showLoadingDialog();
        retrofit.create(ParkingLotService.class).parkingIdByLatLng(longitude, latitude).enqueue(new BaseCallBack<Integer>() {
            @Override
            public void onSuccessful(Integer data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 根据停车场编号获得其车位列表
     * @param result 回调
     * @param parkingLotId 停车场编号
     */
    public void parkingSpaceByParkingId(ResultData.Result<List<ParkingSpace>> result, Integer parkingLotId) {
        DialogUtils.showLoadingDialog();
        retrofit.create(ParkingSpaceService.class).listByParkingId(parkingLotId).enqueue(new BaseCallBack<List<ParkingSpace>>() {
            @Override
            public void onSuccessful(List<ParkingSpace> data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 改变车位状态
     * @param result 回调
     * @param parkingId 停车场编号
     * @param x x坐标
     * @param y y坐标
     * @param status 状态
     */
    public void changeSpaceStatus(ResultData.Result<Void> result, Integer parkingId, Integer x, Integer y, Integer status) {
        DialogUtils.showLoadingDialog();
    }
}
