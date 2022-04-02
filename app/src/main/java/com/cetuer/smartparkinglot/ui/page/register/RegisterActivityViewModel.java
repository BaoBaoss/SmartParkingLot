package com.cetuer.smartparkinglot.ui.page.register;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

/**
 * Created by Cetuer on 2022/4/1 14:47.
 * 注册ViewModel
 */
public class RegisterActivityViewModel extends ViewModel {
    /**
     * 用户名
     */
    public ObservableField<String> username = new ObservableField<>("");

    /**
     * 密码
     */
    public ObservableField<String> password = new ObservableField<>("");

    /**
     * 确认密码
     */
    public ObservableField<String> againPassword = new ObservableField<>("");
}
