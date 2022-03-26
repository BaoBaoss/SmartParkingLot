package com.cetuer.smartparkinglot.data.bean;

import java.util.Objects;

/**
 * Created by Cetuer on 2022/3/12 14:45.
 * 信标坐标
 */
public class BeaconPoint {
    /**
     * x轴坐标
     */
    private Integer x;
    /**
     * y轴坐标
     */
    private Integer y;

    public BeaconPoint() {
    }

    public BeaconPoint(Integer x, Integer y) {
        this.x = x;
        this.y = y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeaconPoint that = (BeaconPoint) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "BeaconPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
