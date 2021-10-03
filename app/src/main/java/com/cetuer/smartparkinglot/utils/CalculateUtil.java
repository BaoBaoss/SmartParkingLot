package com.cetuer.smartparkinglot.utils;

/**
 * Created by Cetuer on 2021/10/2 11:14.
 * 计算工具类，用来计算各种数学公式， 参考：https://blog.csdn.net/u011958166/article/details/106410888
 */
public class CalculateUtil {
    /**
     * 默认N
     */
    private static final Double DEFAULT_N = 1.9931568569324174;
    /**
     * 默认A
     */
    private static final Double DEFAULT_A = 65.0;

    /**
     *
     * 根据RSSI计算距离公式:
     *           abs(RSSI) - A
     *           -------------
     * d = 10 ^      10 * n
     *
     * @param RSSI 接收信号强度(单位:dB)
     * @param A 发射端和接收端相隔1米时的信号强度
     * @param n 环境衰减因子
     * @return 计算所得距离(单位:m)
     */
    public static double calculationDistanceByRSSI(int RSSI, int A, double n) {
        return Math.pow(10, (Math.abs(RSSI) - A) / (10 * n));
    }

    public static double calculationDistanceByRSSI(int RSSI) {
        return Math.pow(10, (Math.abs(RSSI) - DEFAULT_A) / (10 * DEFAULT_N));
    }

    /**
     * 根据距离与信号强度计算n值公式:
     *     abs(RSSI1) - abs(RSSI2)
     * n = -----------------------
     *        10 * (lgd1 - lgd2)
     * @param d1 距离1
     * @param RSSI1 信号强度1
     * @param d2 距离2
     * @param RSSI2 信号强度2
     * @return n值
     */
    public static double calculateN(double d1, int RSSI1, double d2, int RSSI2) {
        return (Math.abs(RSSI1) - Math.abs(RSSI2)) / (10 * (Math.log10(d1) - Math.log10(d2)));
    }

    /**
     * 根据距离与信号强度计算A值公式:
     *     lgd1 * abs(RSSI2) - lgd2 * abs(RSSI1)
     * A = -------------------------------------
     *                  lgd1 - lgd2
     * @param d1 距离1
     * @param RSSI1 信号强度1
     * @param d2 距离2
     * @param RSSI2 信号强度2
     * @return A值
     */
    public static double calculateA(double d1, int RSSI1, double d2, int RSSI2) {
        return (Math.log10(d1) * Math.abs(RSSI2) - Math.log10(d2) * Math.abs(RSSI1)) / (Math.log10(d1) - Math.log10(d2));
    }

    /**
     * 当n值确定后计算A值公式:
     * A = abs(RSSI) - 10 * n * lgd
     * @param RSSI 信号强度
     * @param d 距离
     * @param n 衰减因子
     * @return A值
     */
    public static double calculateA(double d, int RSSI, double n) {
        return Math.abs(RSSI) - 10 * n * Math.log10(d);
    }
}
