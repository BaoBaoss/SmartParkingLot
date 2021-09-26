package com.cetuer.smartparkinglot.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by Cetuer on 2021/9/25 21:56.
 */
public class GpsUtils {
    /**
     * 检查gps是否打开
     */
    public static boolean checkLocation(Context context) {
        return ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
