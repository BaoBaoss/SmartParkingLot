package com.cetuer.smartparkinglot.data.request;


import com.cetuer.smartparkinglot.data.bean.Notice;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import java.util.List;

/**
 * Created by Cetuer on 2022/3/26 23:53.
 * 公告请求
 */
public class NoticeRequest implements BaseRequest {
    private final UnPeekLiveData<List<Notice>> noticeList = new UnPeekLiveData<>();

    /**
     * 根据停车场请求获得公告列表
     */
    public void requestNoticeByParking(Integer parkingId) {
        DataRepository.getInstance().listNoticeByParking(noticeList::postValue, parkingId);
    }

    public UnPeekLiveData<List<Notice>> getNoticeList() {
        return noticeList;
    }
}
