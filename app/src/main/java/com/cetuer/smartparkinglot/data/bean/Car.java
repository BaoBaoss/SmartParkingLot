package com.cetuer.smartparkinglot.data.bean;

public class Car {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String carId;

    /**
     * 用户id
     */
    private Integer memberId;

    /**
     * 车位编号，-1则未停车
     */
    private Integer spaceId;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 购入价格
     */
    private Integer price;

    /**
     * 颜色
     */
    private String color;

    public Car() {
    }

    public Car(String carId, String brand, Integer price, String color) {
        this.carId = carId;
        this.brand = brand;
        this.price = price;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}