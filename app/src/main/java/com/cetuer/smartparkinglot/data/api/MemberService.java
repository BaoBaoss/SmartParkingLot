package com.cetuer.smartparkinglot.data.api;

import com.cetuer.smartparkinglot.data.bean.Member;
import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.data.response.ResultData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
     * 登出
     */
    @DELETE("parking-auth/auth/logout")
    Call<ResultData<Void>> logout();

    /**
     * 注册
     * @param info 用户信息
     * @return 无
     */
    @POST("parking-app/member/register")
    Call<ResultData<Void>> register(@Body MemberLogin info);

    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    @GET("parking-app/member/getInfo")
    Call<ResultData<Member>> getInfo();

    /**
     * 修改密码
     * @param password 密码
     */
    @GET("parking-app/member/resetCurPwd")
    Call<ResultData<Void>> resetPwd(@Query("password") String password);

    /**
     * 检查密码是否匹配
     * @param password 密码
     */
    @GET("parking-app/member/checkMatchPwd")
    Call<ResultData<Boolean>> checkPwd(@Query("password") String password);

    @POST("parking-app/member/updateMember")
    Call<ResultData<Void>> updateMember(@Body Member member);
}
