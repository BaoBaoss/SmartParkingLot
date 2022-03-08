package com.cetuer.smartparkinglot.data.repository;

import com.cetuer.smartparkinglot.data.api.BeaconService;
import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
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
    private static final String BASE_URL = "http://192.168.0.175:9089/app/parking-app/";
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
     * 网络请求信标信息
     * @param result 回调
     */
    public void listBeacon(ResultData.Result<List<BeaconDevice>> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(BeaconService.class).list().enqueue(new BaseCallBack<List<BeaconDevice>>() {
            @Override
            public void onSuccessful(List<BeaconDevice> data) {
                result.onResult(data);
            }
        });
    }
}
