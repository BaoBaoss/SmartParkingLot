package com.cetuer.smartparkinglot.data.request;

import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.data.bean.Member;
import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

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
    private final UnPeekLiveData<String> token = new UnPeekLiveData<>();

    /**
     * 用户信息
     */
    private final UnPeekLiveData<Member> memberInfo = new UnPeekLiveData<>();

    /**
     * 修改密码结果
     */
    private final MutableLiveData<Void> resetPwd = new MutableLiveData<>();

    /**
     * 密码是否匹配
     */
    private final UnPeekLiveData<Boolean> isMatchPwd = new UnPeekLiveData<>();

    /**
     * 修改个人信息结果
     */
    private final MutableLiveData<Void> userInfo = new MutableLiveData<>();

    /**
     * 登出
     */
    private final MutableLiveData<Void> logout = new MutableLiveData<>();

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

    /**
     * 登出
     */
    public void requestLogout() {
        DataRepository.getInstance().logout(logout::postValue);
    }

    /**
     * 获取当前用户信息
     */
    public void requestMemberInfo() {
        DataRepository.getInstance().getMemberInfo(memberInfo::postValue);
    }

    /**
     * 修改密码
     */
    public void requestResetPwd(String password) {
        DataRepository.getInstance().resetPwd(resetPwd::postValue, password);
    }

    /**
     * 检查密码是否匹配
     */
    public void requestMatchPwd(String password) {
        DataRepository.getInstance().checkPwd(isMatchPwd::postValue, password);
    }

    /**
     * 修改个人信息
     */
    public void requestUpdateInfo(Member member) {
        DataRepository.getInstance().updateMember(userInfo::postValue, member);
    }

    public MutableLiveData<Void> getRegisterRes() {
        return registerRes;
    }

    public UnPeekLiveData<String> getToken() {
        return token;
    }

    public UnPeekLiveData<Member> getMemberInfo() {
        return memberInfo;
    }

    public MutableLiveData<Void> getResetPwd() {
        return resetPwd;
    }

    public UnPeekLiveData<Boolean> getIsMatchPwd() {
        return isMatchPwd;
    }

    public MutableLiveData<Void> getLogout() {
        return logout;
    }

    public MutableLiveData<Void> getUserInfo() {
        return userInfo;
    }
}
