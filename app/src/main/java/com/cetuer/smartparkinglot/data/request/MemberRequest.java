package com.cetuer.smartparkinglot.data.request;

import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.data.repository.DataRepository;

/**
 * Created by Cetuer on 2022/3/6 21:49.
 * 用户 Request
 */
public class MemberRequest implements BaseRequest {

    /**
     * 注册结果
     */
    private final MutableLiveData<Void> registerRes = new MutableLiveData<>();

    /**
     * token
     */
    private final MutableLiveData<String> token = new MutableLiveData<>();

    /**
     * 请求注册
     */
    public void requestRegister(MemberLogin memberLogin) {
        DataRepository.getInstance().register(registerRes::postValue, memberLogin);
    }

    /**
     * 登录
     * @param memberLogin 用户信息
     */
    public void requestLogin(MemberLogin memberLogin) {
        DataRepository.getInstance().login(token::postValue, memberLogin);
    }

    public MutableLiveData<Void> getRegisterRes() {
        return registerRes;
    }

    public MutableLiveData<String> getToken() {
        return token;
    }
}
