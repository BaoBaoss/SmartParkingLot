package com.cetuer.smartparkinglot.data.interceptor;

import androidx.annotation.NonNull;

import com.cetuer.smartparkinglot.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Cetuer on 2022/4/5 14:27.
 * Token拦截器 携带token
 */
public class TokenHeaderInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //获取保存在本地的token
        String token = SPUtils.getInstance().getString("token");
        if (!token.isEmpty()) {
            return chain.proceed(chain.request().newBuilder().header("Authorization", "Bearer " + token).build());
        }
        return chain.proceed(chain.request());
    }
}
