package com.cetuer.smartparkinglot.ui.page.login;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

/**
 * Created by Cetuer on 2022/4/1 14:47.
 * 登录ViewModel
 */
public class LoginActivityViewModel extends ViewModel {
    /**
     * 用户名
     */
    public ObservableField<String> username = new ObservableField<>("");

    /**
     * 密码
     */
    public ObservableField<String> password = new ObservableField<>("");
}
