package com.cetuer.smartparkinglot.bluetooth.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cetuer on 2022/3/3 21:16.
 * 滤波单例
 */
public class FilterSingleton {
    private static final Map<String, RSSIFilter> filterMap = new HashMap<>();

    private FilterSingleton() {
    }

    /**
     * 根据设备获取滤波类
     * @param address 设备地址
     * @return 滤波类
     */
    public static RSSIFilter getFilter(String address) {
        if(!filterMap.containsKey(address)) {
            // KalmanFilter kalmanFilter = new KalmanFilter(10, 100);
            MeanFilter meanFilter = new MeanFilter();
            filterMap.put(address, meanFilter);
            return meanFilter;
        }
        return filterMap.get(address);
    }
}
