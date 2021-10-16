package com.cetuer.smartparkinglot.bluetooth;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.location.LocationManager;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cetuer.smartparkinglot.bluetooth.kalman.KalmanSingleton;
import com.cetuer.smartparkinglot.ui.page.guidance.GuidanceFragment;
import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.MaterialDialogUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Cetuer on 2021/9/21 10:08.
 * BLE管理器
 */
public class BleManager {

    /**
     * 唯一实例
     */
    private static BleManager instance;

    /**
     * 外部获取的蓝牙设备列表
     */
    private final MutableLiveData<List<BleDevice>> bleDevicesData = new MutableLiveData<>();

    /**
     * 默认过滤蓝牙地址
     */
    private final String[] defaultAddress = {"60:77:71:C3:2B:CB", "60:77:71:C3:27:72", "60:77:71:C3:2E:E7"};

    /**
     * rssi阈值(暂未使用)
     */
    public final int MIN_RSSI = -90;

    /**
     * 蓝牙适配器
     */
    private BluetoothAdapter bluetoothAdapter;

    /**
     * 蓝牙管理器
     */
    private BluetoothManager bluetoothManager;

    /**
     * 上下文
     */
    private Activity context;

    /**
     * 内部设备列表
     */
    private Map<String, BleDevice> devices;

    /**
     * 过滤构造器
     */
    private ScanFilter.Builder filterBuilder;

    /**
     * 过滤设备列表
     */
    private List<ScanFilter> scanFilters;

    /**
     * 过滤设置
     */
    private ScanSettings.Builder scanSettings;

    /**
     * 扫描器
     */
    private BluetoothLeScanner bluetoothLeScanner;

    /**
     * 是否正在扫描
     */
    private boolean scanning;
    private Handler handler;

    private Runnable task;

    private volatile boolean shouldRefresh;
    /**
     * 扫描回调
     */
    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            //refreshDevice();
            int rssi = result.getRssi();
            String address = result.getDevice().getAddress();
            BleDevice bleDevice = new BleDevice(result.getDevice(), (int) KalmanSingleton.getKalman(address).doFilter(rssi), result.getScanRecord().getBytes());
            devices.put(address, bleDevice);
            bleDevicesData.setValue(new ArrayList<>(devices.values()));
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            KLog.w("扫描设备失败，错误码：" + errorCode);
        }
    };

    private BleManager() {
    }

    /**
     * 获取扫描到的设备列表
     *
     * @return 设备列表
     */
    public LiveData<List<BleDevice>> getScanDeviceEvent() {
        return this.bleDevicesData;
    }

    /**
     * 获得实例
     *
     * @return 实例
     */
    public static synchronized BleManager getInstance() {
        if (instance == null) {
            instance = new BleManager();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param app 上下文
     */
    public void init(Activity app) {
        if (context == null && app != null) {
            context = app;
            bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            filterBuilder = new ScanFilter.Builder();
            scanSettings = new ScanSettings.Builder();
            devices = new HashMap<>();
            scanFilters = new ArrayList<>();
            scanning = false;
            handler = new Handler();
            /*task = new Runnable() {
                @Override
                public void run() {
                    shouldRefresh = true;
                    handler.postDelayed(this, 5*1000);
                }
            };
            handler.post(task);*/
        }
    }

    /**
     * 刷新设备列表，清除掉线或无用设备
     */
 /*   private void refreshDevice() {
        if(!shouldRefresh) return;
        Iterator<Map.Entry<String, BleDevice>> iterator = devices.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, BleDevice> entry = iterator.next();
            if(!entry.getValue().isAvailable()) {
                iterator.remove();
            }
        }
        bleDevicesData.setValue(new ArrayList<>(devices.values()));
        shouldRefresh = false;
    }*/

    /**
     * 刷新扫描器
     */
    public void refreshScanner() {
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    /**
     * 打开蓝牙
     */
    private void enableBluetooth() {
        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
        }
    }

    /**
     * 显示是否打开蓝牙对话框
     */
    public void showOpenToothDialog() {
        MaterialDialogUtils.showBasicDialog(context, "打开蓝牙", "检测到您未打开蓝牙，是否打开蓝牙？")
                .onPositive((dialog, which) -> enableBluetooth())
                .onNegative((dialog, which) -> ToastUtils.showShortToast(context, "请打开蓝牙"))
                .show();
    }

    /**
     * 关闭蓝牙
     */
    public void disableBluetooth() {
        scanning = false;
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
            }
        }
    }

    /**
     * 蓝牙是否启用
     *
     * @return true 启用，false 未启用
     */
    public boolean isBlueEnable() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    /**
     * 扫描所有设备
     */
    public void scan() {
        scanByFilter(null);
    }

    /**
     * 根据默认地址扫描
     */
    public void scanByFilter() {
        scanByFilter(defaultAddress);
    }

    /**
     * 根据地址列表扫描设备
     *
     * @param addresses 地址列表
     */
    public void scanByFilter(String[] addresses) {
        if(scanning) {
            KLog.w("已经在扫描了...");
            return;
        }

        if (!isBlueEnable()) {
            KLog.w("还未开启蓝牙...");
            return;
        }
        scanFilters.clear();
        if (addresses != null) {
            for (String address : addresses) {
                scanFilters.add(buildScanFilterByAddress(address));
            }
        }
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.startScan(scanFilters, buildDefaultScanSettings(), scanCallback);
            scanning = true;
        }
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        if(!scanning) {
            KLog.w("没有开始扫描，无法停止...");
            return;
        }
        scanning = false;
        refreshScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(scanCallback);
        }
    }

    /**
     * 根据地址生成扫描过滤项
     *
     * @param address 设备地址
     * @return 扫描过滤项
     */
    private ScanFilter buildScanFilterByAddress(String address) {
        return filterBuilder.setDeviceAddress(address).build();
    }

    /**
     * 创建默认扫描设置（耗电较高）
     *
     * @return 扫描设置
     */
    private ScanSettings buildDefaultScanSettings() {
        return scanSettings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
    }
}
