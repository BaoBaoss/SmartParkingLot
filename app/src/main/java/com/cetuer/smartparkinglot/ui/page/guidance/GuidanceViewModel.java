package com.cetuer.smartparkinglot.ui.page.guidance;

import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.CarRequest;
import com.cetuer.smartparkinglot.data.request.NoticeRequest;
import com.cetuer.smartparkinglot.data.request.ParkingLotRequest;

public class GuidanceViewModel extends ViewModel {
    /**
     * 停车场请求
     */
    public final ParkingLotRequest parkingLotRequest = new ParkingLotRequest();
    /**
     * 公告请求
     */
    public final NoticeRequest noticeRequest = new NoticeRequest();
    /**
     * 车辆请求
     */
    public final CarRequest carRequest = new CarRequest();
}