package com.cetuer.smartparkinglot.ui.page.register;

import android.os.Bundle;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.databinding.ActivityRegisterBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseActivity;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    /**
     * 记录状态
     */
    private RegisterActivityViewModel mState;
    private SharedViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(RegisterActivityViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_register, BR.vm, mState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogUtils.initLoadingDialog(this);
        //点击注册按钮发送注册请求
        mBinding.register.setOnClickListener(v -> {
            String username = mState.username.get().trim();
            String password = mState.password.get().trim();
            String againPassword = mState.againPassword.get().trim();
            if(username.length() < 2 || username.length() > 20) {
                ToastUtils.showShortToast(RegisterActivity.this, "用户名长度在2-20之间");
                return;
            }
            if(!password.equals(againPassword)) {
                ToastUtils.showShortToast(RegisterActivity.this, "两次密码输入不一致");
                return;
            }
            if(password.length() < 5 || password.length() > 20) {
                ToastUtils.showLongToast(RegisterActivity.this, "密码长度在5-20之间");
                return;
            }
            //发送请求注册用户
            mEvent.memberRequest.requestRegister(new MemberLogin(username, password));
        });
        //注册成功关闭当前页面
        mEvent.memberRequest.getRegisterRes().observe(this, data -> {
            ToastUtils.showLongToast(RegisterActivity.this, "注册成功");
            finish();
        });
        //点击返回登录按钮结束当前Activity
        mBinding.returnLogin.setOnClickListener(v -> finish());
    }


}