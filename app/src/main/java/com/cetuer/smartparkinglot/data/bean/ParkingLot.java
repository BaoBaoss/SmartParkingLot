package com.cetuer.smartparkinglot.data.bean;

/**
 * 停车场实体类
 *
 * @TableName parking_lot
 */
public class ParkingLot {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 停车场名称
     */
    private String name;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 车位总数
     */
    private Integer carportCount;

    /**
     * 空余车位
     */
    private Integer emptyParking;

    /**
     * 收费标准  元/小时
     */
    private Double priceStandard;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getCarportCount() {
        return carportCount;
    }

    public void setCarportCount(Integer carportCount) {
        this.carportCount = carportCount;
    }

    public Integer getEmptyParking() {
        return emptyParking;
    }

    public void setEmptyParking(Integer emptyParking) {
        this.emptyParking = emptyParking;
    }

    public Double getPriceStandard() {
        return priceStandard;
    }

    public void setPriceStandard(Double priceStandard) {
        this.priceStandard = priceStandard;
    }
}