package com.cetuer.smartparkinglot.bluetooth.kalman;

import com.cetuer.smartparkinglot.bluetooth.BleDevice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cetuer on 2021/10/16 18:56.
 * 获取卡尔曼每个设备对应的滤波
 */
public class KalmanSingleton {
    private static final Map<String, KalmanFilter> kalmanFilterMap = new HashMap<>();

    private KalmanSingleton() {
    }

    public static KalmanFilter getKalman(String address) {
        if(!kalmanFilterMap.containsKey(address)) {
            KalmanFilter kalmanFilter = new KalmanFilter(10, 100);
            kalmanFilterMap.put(address, kalmanFilter);
            return kalmanFilter;
        }
        return kalmanFilterMap.get(address);
    }
}
