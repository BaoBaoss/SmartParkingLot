package com.cetuer.smartparkinglot.bluetooth.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Created by Cetuer on 2022/3/2 22:03.
 * 平均值滤波
 */
public class MeanFilter implements RSSIFilter {

    /**
     * 最大支持多少元素滤波
     */
    public static final int MAX_COUNT = 10;

    /**
     * 滑动记录数据
     */
    private final List<Double> rssiList = new LinkedList<>();

    @Override
    public double doFilter(double rssi) {
        rssiList.add(rssi);
        if (rssiList.size() > MAX_COUNT + 1) {
            Collections.sort(rssiList);
            rssiList.remove(0);
            rssiList.remove(rssiList.size() - 1);
        }
        return rssiList.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}
