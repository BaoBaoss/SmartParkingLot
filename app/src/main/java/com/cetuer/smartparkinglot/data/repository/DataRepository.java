package com.cetuer.smartparkinglot.data.repository;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.data.api.BeaconService;
import com.cetuer.smartparkinglot.data.api.CarService;
import com.cetuer.smartparkinglot.data.api.FingerprintService;
import com.cetuer.smartparkinglot.data.api.MemberService;
import com.cetuer.smartparkinglot.data.api.NoticeService;
import com.cetuer.smartparkinglot.data.api.ParkingLotService;
import com.cetuer.smartparkinglot.data.api.ParkingSpaceService;
import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.data.bean.Car;
import com.cetuer.smartparkinglot.data.bean.Member;
import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.data.bean.Notice;
import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.data.bean.ParkingSpace;
import com.cetuer.smartparkinglot.data.interceptor.TokenHeaderInterceptor;
import com.cetuer.smartparkinglot.data.response.ResultData;
import com.cetuer.smartparkinglot.data.response.callback.BaseCallBack;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
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
    private static final String BASE_URL = "http://app.cetuer.com/";
    /**
     * 超时时间10秒
     */
    private static final int DEFAULT_TIMEOUT = 10;

    private SharedViewModel mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
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
                    .addNetworkInterceptor(new TokenHeaderInterceptor())
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
     *
     * @param result      回调
     * @param memberLogin 登录信息
     */
    public void login(ResultData.Result<String> result, MemberLogin memberLogin) {
        retrofit.create(MemberService.class).login(memberLogin).enqueue(new BaseCallBack<String>() {
            @Override
            public void onSuccessful(String data) {
                result.onResult(data);
                mEvent.beLogin.setValue(false);
            }

            @Override
            protected void onFail(Integer code, Throwable t) {
                super.onFail(code, t);
                mEvent.beLogin.setValue(false);
            }
        });
    }

    /**
     * 登出
     *
     * @param result 回调
     */
    public void logout(ResultData.Result<Void> result) {
        retrofit.create(MemberService.class).logout().enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 注册
     *
     * @param result     回调
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
     *
     * @param result       回调
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
     *
     * @param result       回调
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
     *
     * @param result 回调
     * @param RSSIs  信标强度列表
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
     *
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
     *
     * @param result    返回值
     * @param longitude 经度
     * @param latitude  纬度
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
     *
     * @param result       回调
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
     *
     * @param result    回调
     * @param parkingId 停车场编号
     * @param x         x坐标
     * @param y         y坐标
     * @param status    状态
     */
    public void changeSpaceStatus(ResultData.Result<Void> result, Integer parkingId, Integer x, Integer y, Integer status) {
        DialogUtils.showLoadingDialog();
    }

    /**
     * 获取当前用户信息
     *
     * @param result 回调
     */
    public void getMemberInfo(ResultData.Result<Member> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(MemberService.class).getInfo().enqueue(new BaseCallBack<Member>() {
            @Override
            public void onSuccessful(Member data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 修改密码
     *
     * @param pwd    新密码
     * @param result 回调
     */
    public void resetPwd(ResultData.Result<Void> result, String pwd) {
        DialogUtils.showLoadingDialog();
        retrofit.create(MemberService.class).resetPwd(pwd).enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 检查密码是否匹配
     *
     * @param pwd    密码
     * @param result 回调
     */
    public void checkPwd(ResultData.Result<Boolean> result, String pwd) {
        DialogUtils.showLoadingDialog();
        retrofit.create(MemberService.class).checkPwd(pwd).enqueue(new BaseCallBack<Boolean>() {
            @Override
            public void onSuccessful(Boolean data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param member 用户信息
     * @param result 回调
     */
    public void updateMember(ResultData.Result<Void> result, Member member) {
        DialogUtils.showLoadingDialog();
        retrofit.create(MemberService.class).updateMember(member).enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 根据停车场获取公告
     *
     * @param parkingId 停车场编号
     * @param result    回调
     */
    public void listNoticeByParking(ResultData.Result<List<Notice>> result, Integer parkingId) {
        DialogUtils.showLoadingDialog();
        retrofit.create(NoticeService.class).listByParking(parkingId).enqueue(new BaseCallBack<List<Notice>>() {
            @Override
            public void onSuccessful(List<Notice> data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 获取车辆信息
     */
    public void getCarInfo(ResultData.Result<Car> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(CarService.class).getInfo().enqueue(new BaseCallBack<Car>() {
            @Override
            public void onSuccessful(Car data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 新增或修改车辆信息
     *
     * @param car    车辆信息
     * @param result 回调
     */
    public void addOrEditCar(ResultData.Result<Void> result, Car car) {
        DialogUtils.showLoadingDialog();
        retrofit.create(CarService.class).addOrEditCar(car).enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 停车
     *
     * @param result 回调
     * @param spaceId 车位编号
     */
    public void parking(ResultData.Result<Void> result, Integer spaceId) {
        DialogUtils.showLoadingDialog();
        retrofit.create(ParkingSpaceService.class).parking(spaceId).enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 是否可以停车
     *
     * @param result 回调
     */
    public void canParking(ResultData.Result<Boolean> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(CarService.class).canParking().enqueue(new BaseCallBack<Boolean>() {
            @Override
            public void onSuccessful(Boolean data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 获取停车车位
     *
     * @param result 回调
     */
    public void carSpace(ResultData.Result<ParkingSpace> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(ParkingSpaceService.class).carSpace().enqueue(new BaseCallBack<ParkingSpace>() {
            @Override
            public void onSuccessful(ParkingSpace data) {
                result.onResult(data);
            }
        });
    }

    /**
     * 是否可以寻车
     *
     * @param result 回调
     */
    public void canFindCar(ResultData.Result<Boolean> result) {
        DialogUtils.showLoadingDialog();
        retrofit.create(CarService.class).canFindCar().enqueue(new BaseCallBack<Boolean>() {
            @Override
            public void onSuccessful(Boolean data) {
                result.onResult(data);
            }
        });
    }
    /**
     * 寻车
     *
     * @param spaceId 之前停车位置
     * @param result 回调
     */
    public void findCar(ResultData.Result<Void> result, Integer spaceId) {
        DialogUtils.showLoadingDialog();
        retrofit.create(ParkingSpaceService.class).findCar(spaceId).enqueue(new BaseCallBack<Void>() {
            @Override
            public void onSuccessful(Void data) {
                result.onResult(data);
            }
        });
    }
}
