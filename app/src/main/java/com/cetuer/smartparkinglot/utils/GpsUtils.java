package com.cetuer.smartparkinglot.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by Cetuer on 2021/9/25 21:56.
 * GPS工具类
 */
public class GpsUtils {

    /**
     * 检测GPS是否打开
     * @param context 上下文
     * @return true打开 false未打开
     */
    public static boolean checkLocation(Context context) {
        return ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
