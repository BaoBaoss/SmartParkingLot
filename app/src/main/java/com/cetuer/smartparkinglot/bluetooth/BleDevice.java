package com.cetuer.smartparkinglot.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.cetuer.smartparkinglot.utils.NumConvertUtil;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Cetuer on 2021/9/4 21:39.
 * BLE设备实体类
 */
public class BleDevice implements Parcelable {
    /**
     * 最大间隔，超过此时长未更新则删除此设备，默认三秒
     */
    private final long MAX_INTERVAL = 3 * 1000;

    private BluetoothDevice device;
    private Integer major;
    private Integer minor;
    private String name;
    private Integer power;
    private Integer rssi;
    private byte[] scanData;
    private String uuid;
    private long updateTime;

    public BleDevice(BluetoothDevice bluetoothDevice, byte[] scanData) {
        this(bluetoothDevice, null, scanData);
    }

    public BleDevice(BluetoothDevice bluetoothDevice, Integer rssi, byte[] scanData) {
        this.device = bluetoothDevice;
        this.rssi = rssi;
        this.scanData = scanData;
        parseParams();
    }

    private void parseParams() {
        this.major = (scanData[26] & 255) + ((scanData[25] & 255) * 256);
        if (scanData[30] == 17 && scanData[31] == 9) {
            this.name = new String(scanData, 32, 16);
        }
        this.minor = (scanData[28] & 255) + ((scanData[27] & 255) * 256);
        this.power = (int) scanData[29];
        this.uuid = NumConvertUtil.byte2hex(scanData, 9, 4) + "-" +
                NumConvertUtil.byte2hex(scanData, 13, 2) + "-" +
                NumConvertUtil.byte2hex(scanData, 15, 2) + "-" +
                NumConvertUtil.byte2hex(scanData, 17, 2) + "-" +
                NumConvertUtil.byte2hex(scanData, 19, 6);
        this.updateTime = System.currentTimeMillis();
    }

    public void updateParams(Integer rssi, byte[] scanData) {
        this.rssi = rssi;
        this.scanData = scanData;
        parseParams();
    }

    public boolean isAvailable() {
        return  (System.currentTimeMillis() - this.updateTime) > MAX_INTERVAL;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public byte[] getScanData() {
        return scanData;
    }

    public void setScanData(byte[] scanData) {
        this.scanData = scanData;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @NonNull
    @Override
    public String toString() {
        return "BleDeviceEntity{" +
                "device=" + device +
                ",major=" + major +
                ", minor=" + minor +
                ", name='" + name + '\'' +
                ", power=" + power +
                ", rssi=" + rssi +
                ", scanData=" + Arrays.toString(scanData) +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    protected BleDevice(Parcel in) {
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        if (in.readByte() == 0) {
            major = null;
        } else {
            major = in.readInt();
        }
        if (in.readByte() == 0) {
            minor = null;
        } else {
            minor = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            power = null;
        } else {
            power = in.readInt();
        }
        if (in.readByte() == 0) {
            rssi = null;
        } else {
            rssi = in.readInt();
        }
        scanData = in.createByteArray();
        uuid = in.readString();
    }

    public static final Creator<BleDevice> CREATOR = new Creator<BleDevice>() {
        @Override
        public BleDevice createFromParcel(Parcel in) {
            return new BleDevice(in);
        }

        @Override
        public BleDevice[] newArray(int size) {
            return new BleDevice[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(device, flags);
        if (major == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(major);
        }
        if (minor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(minor);
        }
        dest.writeString(name);
        if (power == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(power);
        }
        if (rssi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rssi);
        }
        dest.writeByteArray(scanData);
        dest.writeString(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BleDevice bleDevice = (BleDevice) o;
        return Objects.equals(device, bleDevice.device) && Objects.equals(major, bleDevice.major) && Objects.equals(minor, bleDevice.minor) && Objects.equals(name, bleDevice.name) && Objects.equals(power, bleDevice.power) && Objects.equals(rssi, bleDevice.rssi) && Arrays.equals(scanData, bleDevice.scanData) && Objects.equals(uuid, bleDevice.uuid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(device, major, minor, name, power, rssi, uuid);
        result = 31 * result + Arrays.hashCode(scanData);
        return result;
    }
}
