package com.cetuer.smartparkinglot.ui.page.mine;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.CarRequest;
import com.cetuer.smartparkinglot.data.request.MemberRequest;

public class MineViewModel extends ViewModel {

    /**
     * 用户请求
     */
    public final MemberRequest memberRequest = new MemberRequest();

    /**
     * 车辆请求
     */
    public final CarRequest carRequest = new CarRequest();

    /**
     * 昵称
     */
    public ObservableField<String> nickname = new ObservableField<>("暂无");

    /**
     * 手机号
     */
    public ObservableField<String> phone = new ObservableField<>("暂无");
}