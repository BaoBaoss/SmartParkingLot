package com.cetuer.smartparkinglot.ui.page.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleManager;
import com.cetuer.smartparkinglot.bluetooth.BlueToothReceiver;
import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.databinding.ActivityMainBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.adapter.FragmentViewPagerAdapter;
import com.cetuer.smartparkinglot.ui.page.BaseActivity;
import com.cetuer.smartparkinglot.ui.page.carport_query.CarportQueryFragment;
import com.cetuer.smartparkinglot.ui.page.find_car.FindCarFragment;
import com.cetuer.smartparkinglot.ui.page.guidance.GuidanceContainerFragment;
import com.cetuer.smartparkinglot.ui.page.mine.MineFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.GpsUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.permissionx.guolindev.PermissionX;

import java.util.stream.Collectors;

/**
 * 主Activity，切换Fragment
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private BlueToothReceiver mBlueToothReceiver;
    /**
     * 记录MainActivity状态
     */
    private MainActivityViewModel mState;
    private SharedViewModel mEvent;

    /**
     * 再按一次退出程序
     */
    private long mExitTime;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MainActivityViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mState)
                .addBindingParam(BR.listener, new HandleListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogUtils.initLoadingDialog(this);
        BleManager.getInstance().init(this);
        requestPermission();
        FragmentViewPagerAdapter viewPagerAdapter = new FragmentViewPagerAdapter(this);
        viewPagerAdapter.addFragment(new GuidanceContainerFragment());
        viewPagerAdapter.addFragment(new FindCarFragment());
        viewPagerAdapter.addFragment(new CarportQueryFragment());
        viewPagerAdapter.addFragment(new MineFragment());
        mBinding.mainViewPager.setUserInputEnabled(false);
        mBinding.mainViewPager.setAdapter(viewPagerAdapter);
        //关闭蓝牙，停止扫描
        mEvent.openBluetooth.observe(this, openBluetooth -> {
            if (!openBluetooth) {
                BleManager.getInstance().stopScan();
            }
        });
        //关闭位置信息，停止扫描
        mEvent.openGPS.observe(this, openGps -> {
            if (!openGps) {
                BleManager.getInstance().stopScan();
            }
        });
        //过滤信息改变，重新扫描
        mEvent.filterMacs.observe(this, macs -> {
            BleManager.getInstance().stopScan();
            BleManager.getInstance().refreshScanner();
            BleManager.getInstance().scanByFilter(macs);
        });
        mEvent.beaconRequest.getBeaconLiveData().observe(this, beaconDevices -> mEvent.filterMacs.postValue(beaconDevices.stream().map(BeaconDevice::getMac).collect(Collectors.toList())));
        BleManager.getInstance().getScanDeviceEvent().observe(this, bleDevices -> mEvent.list.setValue(bleDevices));
        //注册广播
        mBlueToothReceiver = new BlueToothReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        registerReceiver(mBlueToothReceiver, intentFilter);
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .onForwardToSettings((scope, deniedList) -> scope.showForwardToSettingsDialog(deniedList, "您永久拒绝了以下权限，是否需要重新打开？", "设置", "取消"))
                .onExplainRequestReason((scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList, "为了获取蓝牙定位需要如下权限", "确定", "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        ToastUtils.showShortToast(this, "权限被拒绝，无法搜索蓝牙");
                    } else {
                        boolean isOpenBlueTooth = BleManager.getInstance().isBlueEnable();
                        boolean isOpenGps = GpsUtils.checkLocation(this);
                        mEvent.openBluetooth.setValue(isOpenBlueTooth);
                        mEvent.openGPS.setValue(isOpenGps);
                        if (!isOpenBlueTooth) {
                            BleManager.getInstance().showOpenToothDialog();
                        }
                        if (!isOpenGps) {
                            ToastUtils.showShortToast(this, "需要打开位置权限才可以搜索到蓝牙设备");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册广播
        unregisterReceiver(mBlueToothReceiver);
    }

    /**
     * 再按一次退出程序
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime < 2000) {
            finish();
        } else {
            mExitTime = System.currentTimeMillis();
            ToastUtils.showShortToast(this, "再按一次退出应用");
        }
    }

    /**
     * 监听内部类
     */
    public class HandleListener extends ViewPager2.OnPageChangeCallback implements BottomNavigationView.OnNavigationItemSelectedListener {

        /**
         * nav选中时切换viewPage
         *
         * @param item 选中的item
         * @return 当前页面是否可用
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_parking_guidance) {
                mBinding.mainViewPager.setCurrentItem(0, false);
                return true;
            } else if (item.getItemId() == R.id.navigation_reverse_for_car) {
                mBinding.mainViewPager.setCurrentItem(1, false);
                return true;
            } else if (item.getItemId() == R.id.navigation_carport_query) {
                mBinding.mainViewPager.setCurrentItem(2, false);
                return true;
            } else if (item.getItemId() == R.id.navigation_mine) {
                mBinding.mainViewPager.setCurrentItem(3, false);
                return true;
            } else
                return false;
        }

        /**
         * viewPage选中时切换nav
         *
         * @param position 选中下标
         */
        @Override
        public void onPageSelected(int position) {
            mBinding.navView.getMenu().getItem(position).setChecked(true);
        }
    }
}