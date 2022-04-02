package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.data.response.ResultData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Cetuer on 2022/4/2 17:58.
 * 用户服务
 */
public interface MemberService {

    /**
     * 登录
     * @param info 用户信息
     * @return token
     */
    @POST("parking-auth/auth/appLogin")
    Call<ResultData<String>> login(@Body MemberLogin info);

    /**
     * 注册
     * @param info 用户信息
     * @return 无
     */
    @POST("parking-app/member/register")
    Call<ResultData<Void>> register(@Body MemberLogin info);
}
