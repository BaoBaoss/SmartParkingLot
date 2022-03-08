package com.cetuer.smartparkinglot.data.bean;

/**
 * Created by Cetuer on 2022/3/6 21:38.
 * 信标设备实体
 */
public class BeaconDevice {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 物理地址
     */
    private String mac;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 主要标识
     */
    private Integer major;

    /**
     * 次要标识
     */
    private Integer minor;

    /**
     * x坐标
     */
    private Integer x;

    /**
     * y坐标
     */
    private Integer y;

    /**
     * rssi信号
     * */
    private Integer rssi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }
}
