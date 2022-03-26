package com.cetuer.smartparkinglot.data.bean;

/**
 * 信标rssi
 *
 * @author zhangqb
 * @date 2022/3/26 13:48
 */
public class BeaconRssi {
    /**
     * 物理地址
     */
    private String mac;
    /**
     * 信号强度
     */
    private Double rssi;

    public BeaconRssi(String mac, Double rssi) {
        this.mac = mac;
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Double getRssi() {
        return rssi;
    }

    public void setRssi(Double rssi) {
        this.rssi = rssi;
    }
}
