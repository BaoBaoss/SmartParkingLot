package com.cetuer.smartparkinglot.data.request;

import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.data.bean.Notice;
import com.cetuer.smartparkinglot.data.repository.DataRepository;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/26 23:53.
 * 停车场请求
 */
public class NoticeRequest implements BaseRequest {
    private final MutableLiveData<List<Notice>> noticeList = new MutableLiveData<>();

    /**
     * 根据停车场请求获得公告列表
     */
    public void requestNoticeByParking(Integer parkingId) {
        DataRepository.getInstance().listNoticeByParking(noticeList::postValue, parkingId);
    }

    public MutableLiveData<List<Notice>> getNoticeList() {
        return noticeList;
    }
}
