package com.cetuer.smartparkinglot.bluetooth.filter;

/**
 * Created by Cetuer on 2022/3/2 22:14.
 * RSSI滤波
 */
public interface RSSIFilter {

    /**
     * 进行滤波
     * @param rssi 当前rssi值
     * @return 滤波后的值
     */
    double doFilter(double rssi);
}
