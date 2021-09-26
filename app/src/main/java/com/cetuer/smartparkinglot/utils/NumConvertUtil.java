package com.cetuer.smartparkinglot.utils;

import android.text.TextUtils;

/**
 * Created by Cetuer on 2021/9/4 22:18.
 * 进制转换工具类
 */
public class NumConvertUtil {

    /**
     * 字符转byte
     *
     * @param c 字符
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte转16进制
     *
     * @param src    源数据
     * @param offset 偏移量
     * @param len    长度
     * @return 转换后的16进制
     */
    public static String byte2hex(byte[] src, int offset, int len) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < len; i++) {
            hex.append(Integer.toHexString(src[offset + i] & 255));
        }
        return hex.toString();
    }

    /**
     * 16进制字符串转为byte
     *
     * @param hexString 16进制字符串
     * @return byte数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (TextUtils.isEmpty(hexString)) return null;
        String upperHexString = hexString.toUpperCase();
        int len = upperHexString.length() / 2;
        char[] hexChars = upperHexString.toCharArray();
        byte[] d = new byte[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
}
