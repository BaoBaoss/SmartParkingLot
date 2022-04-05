package com.cetuer.smartparkinglot.ui.page.login;

import android.content.Intent;
import android.os.Bundle;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.data.bean.MemberLogin;
import com.cetuer.smartparkinglot.databinding.ActivityLoginBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseActivity;
import com.cetuer.smartparkinglot.ui.page.main.MainActivity;
import com.cetuer.smartparkinglot.ui.page.register.RegisterActivity;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.SPUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    /**
     * 记录状态
     */
    private LoginActivityViewModel mState;
    private SharedViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(LoginActivityViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_login, BR.vm, mState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogUtils.initLoadingDialog(this);
        //登录
        mBinding.login.setOnClickListener(v -> {
            String username = mState.username.get().trim();
            String password = mState.password.get().trim();
            if(username.length() < 2 || username.length() > 20) {
                ToastUtils.showShortToast(LoginActivity.this, "用户名长度在2-20之间");
                return;
            }
            if(password.length() < 5 || password.length() > 20) {
                ToastUtils.showShortToast(LoginActivity.this, "密码长度在5-20之间");
                return;
            }
            mBinding.login.setEnabled(false);
            mEvent.memberRequest.requestLogin(new MemberLogin(username, password));
        });
        //登录完成，将token保存到SharedPreferences后跳转到主界面
        mEvent.memberRequest.getToken().observe(this, s -> {
            mBinding.login.setEnabled(true);
            SPUtils.getInstance().put("token", s);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        mBinding.register.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        //有token直接进主页
        if(!SPUtils.getInstance().getString("token").isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}