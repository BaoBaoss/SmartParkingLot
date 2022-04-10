package com.cetuer.smartparkinglot.ui.page.mine;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class MineViewModel extends ViewModel {

    /**
     * 昵称
     */
    public ObservableField<String> nickname = new ObservableField<>("暂无");

    /**
     * 手机号
     */
    public ObservableField<String> phone = new ObservableField<>("暂无");
}