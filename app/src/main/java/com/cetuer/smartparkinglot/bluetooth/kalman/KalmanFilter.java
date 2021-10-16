package com.cetuer.smartparkinglot.bluetooth.kalman;

/**
 * Created by Cetuer on 2021/10/2 19:36.
 * Kalman滤波, 参考：https://blog.csdn.net/qq_34193940/article/details/80569397
 */
public class KalmanFilter {
    /**
     * k-1 时刻的滤波，即是 k-1 时刻的值
     */
    private double filterValue;

    /**
     * Kalman增益
     */
    private double kalmanGain;

    /**
     * x(n) = A * x(n-1) + u(n), u(n) ~ N(0,Q)
     */
    private double A;

    /**
     * z(n) = H * x(n) + w(n), w(n) ~ N(0,R)
     */
    private double H;

    /**
     * 预测过程噪声偏差的方差
     */
    private double Q;

    /**
     * 测量噪声偏差
     */
    private double R;

    /**
     * 估计误差协方差
     */
    private double P;

    /**
     * 初始化
     * @param Q 预测噪声方差 由系统外部测定给定
     * @param R 测量噪声方差 由系统外部测定给定
     */
    public KalmanFilter(double Q, double R) {
        //标量卡尔曼
        this.A = 1;
        this.H = 1;
        //后验状态估计值误差的方差的初始值（不要为0问题不大）
        this.P = 10;
        //预测（过程）噪声方差 影响收敛速率，可以根据实际需求给出
        this.Q = Q;
        //测量（观测）噪声方差 可以通过实验手段获得
        this.R = R;
        //测量的初始值
        this.filterValue = -50;
    }

    public double doFilter(double rssi) {
        //预测下一时刻的值
        double predictValue = this.A * this.filterValue;
        //求协方差，公式: p(n|n-1) = A^2 * p(n-1|n-1) + q
        this.P = this.A * this.A * this.P + this.Q;
        //计算kalman增益，公式: Kg(k) = P(k|k-1) H’ / (H P(k|k-1) H’ + R)
        this.kalmanGain = this.P * this.H / (this.P * this.H * this.H + this.R);
        //修正结果，即计算滤波值，公式: X(k|k) = X(k|k-1) + Kg(k) (Z(k) - H X(k|k-1))
        this.filterValue = predictValue + (rssi - predictValue) * this.kalmanGain;
        //更新后验估计，公式: P[n|n] = (1 - K[n] * H) * P[n|n-1]
        this.P = (1 - this.kalmanGain * this.H) * this.P;
        return this.filterValue;
    }
}
