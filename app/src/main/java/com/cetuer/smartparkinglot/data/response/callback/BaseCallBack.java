package com.cetuer.smartparkinglot.data.response.callback;

import androidx.annotation.NonNull;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.data.response.ResultData;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.SPUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cetuer on 2022/3/6 16:47.
 * 统一响应处理
 */
public abstract class BaseCallBack<T> implements Callback<ResultData<T>> {
    @Override
    public final void onResponse(@NonNull Call<ResultData<T>> call, @NonNull Response<ResultData<T>> response) {
        DialogUtils.dismissLoadingDialog();
        if(response.isSuccessful() && 200 == response.code()) {
            ResultData<T> data = response.body();
            if(null != data && data.getStatus() == 20000) {
                onSuccessful(data.getData());
                return;
            }
            onFail(data == null ? -1 : data.getStatus(), new RuntimeException(data == null ? "未知错误" : data.getMessage()));
            return;
        }
        onFail(-1, new RuntimeException(response.message()));
    }

    @Override
    public void onFailure(@NonNull Call<ResultData<T>> call, @NonNull Throwable t) {
        DialogUtils.dismissLoadingDialog();
        onFail(-1, t);
    }

    /**
     * 请求成功
     * @param data 数据
     */
    public abstract void onSuccessful(T data);

    /**
     * 请求失败
     * @param code 错误码
     * @param t 错误信息
     */
    protected void onFail(Integer code, Throwable t) {
        KLog.e("请求失败：" + t.getMessage());
        //如果code为以下几种则token不可用
        if(code == 40311 || code == 40342 || code == 40343) {
            //清除token
            SPUtils.getInstance().remove("token");
            ToastUtils.showLongToast(App.getInstance(), "token失效，请退出app后重新登录");
            return;
        }
        ToastUtils.showLongToast(App.getInstance(), "请求失败：" + t.getMessage());
    }
}
