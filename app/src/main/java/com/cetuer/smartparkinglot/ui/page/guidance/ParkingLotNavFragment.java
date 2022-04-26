package com.cetuer.smartparkinglot.ui.page.guidance;

import android.Manifest;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.data.bean.BeaconPoint;
import com.cetuer.smartparkinglot.data.bean.BeaconRssi;
import com.cetuer.smartparkinglot.data.bean.ParkingSpace;
import com.cetuer.smartparkinglot.databinding.FragmentParkingLotNavBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 停车场导航
 */
public class ParkingLotNavFragment extends BaseFragment<FragmentParkingLotNavBinding> {

    private ParkingLotNavFragmentViewModel mState;
    private SharedViewModel mEvent;
    private boolean mOpenBluetooth;
    private boolean mOpenGps;
    //扫描十次定位一次
    private int scanCount = 10;
    //停车场id
    private Integer parkingId;
    //终点坐标
    private BeaconPoint endPoint;
    //停车位
    private List<ParkingSpace> parkingSpaces;
    //保存坐标
    private List<List<TextView>> coordinate;
    //上一个定位的坐标
    private BeaconPoint lastLocationPoint;
    //上一个定位的坐标颜色
    private Integer lastLocationColor;
    //导航状态：0->未开始导航,1->导航中,2->导航结束
    private Integer navState = 0;
    //导航的停车位
    private ParkingSpace parkingSpace;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(ParkingLotNavFragmentViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
        coordinate = new ArrayList<>();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_parking_lot_nav, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mState.parkingLotRequest.requestParkingIdByLatLng(getArguments().getDouble("longitude"), getArguments().getDouble("latitude"));
        mState.parkingLotRequest.getParkingLotId().observe(getViewLifecycleOwner(), parkingLotId -> {
            this.parkingId = parkingLotId;
            //获得停车场的终点坐标
            mState.beaconRequest.requestEndPoint(parkingLotId);
        });
        //获得终点坐标后获得停车位
        mState.beaconRequest.getEndPoint().observe(getViewLifecycleOwner(), beaconPoint -> {
            if(beaconPoint.getX() == 0 || beaconPoint.getY() == 0) {
                ToastUtils.showShortToast(this.mActivity, "此停车场没有信标");
                return;
            }
            endPoint = beaconPoint;
            mState.parkingSpaceRequest.requestParkingSpace(parkingId);
        });
        //获得停车位后绘制地图
        mState.parkingSpaceRequest.getParkingSpaceList().observe(getViewLifecycleOwner(), parkingSpaces -> {
            ParkingLotNavFragment.this.parkingSpaces = parkingSpaces;
            drawMap();
        });
        //定位
        mEvent.list.observe(getViewLifecycleOwner(), bleDevices -> {
            //导航完成不再定位
            if(navState == 3) {
                return;
            }
            if (scanCount < 10) {
                scanCount++;
                return;
            }
            scanCount = 0;
            List<BeaconRssi> RSSIs = bleDevices.stream().map(ble -> new BeaconRssi(ble.getDevice().getAddress(), ble.getRssi().doubleValue())).collect(Collectors.toList());
            mState.fingerprintRequest.requestLocation(RSSIs);
        });
        mState.fingerprintRequest.getLocationPoint().observe(getViewLifecycleOwner(), point -> {
            //上一个位置还原颜色，当前位置变色
            if (lastLocationPoint != null) {
                coordinate.get(lastLocationPoint.getX()).get(lastLocationPoint.getY()).setBackgroundColor(lastLocationColor);
            }
            lastLocationPoint = point;
            lastLocationColor = ((ColorDrawable) coordinate.get(point.getX()).get(point.getY()).getBackground()).getColor();
            coordinate.get(point.getX()).get(point.getY()).setBackgroundColor(Color.BLUE);
            //如果正在导航，并且当前位置与选中的车位坐标一致则停车成功
            if(this.navState == 1 && point.getX().equals(parkingSpace.getX()) && point.getY().equals(parkingSpace.getY())) {
                //发送请求
                mState.parkingSpaceRequest.requestParking(parkingSpace.getId());
                //改变当前车位颜色
                coordinate.get(point.getX()).get(point.getY()).setBackgroundColor(Color.RED);
                //更改导航状态为已完成
                this.navState = 2;
            }
        });
        mState.parkingSpaceRequest.getParking().observe(getViewLifecycleOwner(), unused -> DialogUtils.showBasicDialogNoCancel(this.mActivity, "提示", "停车成功！"));
    }

    /**
     * 绘制地图
     */
    public void drawMap() {
        //生成顶部边框
        LinearLayout topLinearLayout = new LinearLayout(this.mActivity);
        for (int i = 0; i < endPoint.getY(); i++) {
            View topLine = new View(this.mActivity);
            topLine.setLayoutParams(new LinearLayout.LayoutParams(202, 2));
            topLine.setBackgroundColor(Color.BLACK);
            topLinearLayout.addView(topLine);
        }
        mBinding.map.addView(topLinearLayout);
        //y行
        for (int i = 0; i < endPoint.getX(); i++) {
            LinearLayout linearLayout = new LinearLayout(this.mActivity);
            //保存本行坐标
            ArrayList<TextView> points = new ArrayList<>();
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);
            //x列
            for (int j = 0; j < endPoint.getY(); j++) {
                if (j == 0) {
                    //左侧边框
                    View leftLine = new View(this.mActivity);
                    leftLine.setLayoutParams(new LinearLayout.LayoutParams(2, 202));
                    leftLine.setBackgroundColor(Color.BLACK);
                    linearLayout.addView(leftLine);
                }
                //文字信息
                TextView textView = new TextView(this.mActivity);
                textView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                textView.setGravity(Gravity.CENTER);
                textView.setBackground(new ColorDrawable(Color.WHITE));
                points.add(textView);
                linearLayout.addView(textView);
                View verticalLine = new View(this.mActivity);
                verticalLine.setLayoutParams(new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.WRAP_CONTENT));
                verticalLine.setBackgroundColor(Color.BLACK);
                linearLayout.addView(verticalLine);
            }
            View horizontalLine = new View(this.mActivity);
            horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            horizontalLine.setBackgroundColor(Color.BLACK);
            mBinding.map.addView(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBinding.map.addView(horizontalLine);
            coordinate.add(points);
        }
        colourParkingSpace();
    }

    /**
     * 给停车位上色
     */
    public void colourParkingSpace() {
        parkingSpaces.forEach(space -> {
            TextView textView = coordinate.get(space.getX()).get(space.getY());
            //车位可用
            if (space.getAvailable() == 1) {
                textView.setOnClickListener(v -> {
                    //不是还未开始导航则不进行导航
                    if(navState != 0) return;
                    DialogUtils.showBasicDialog(ParkingLotNavFragment.this.mActivity, "提示", "您确定要选定此车位作为停车位吗？")
                            .onPositive((dialog, which) -> navigation(space))
                            .onNegative((dialog, which) -> dialog.dismiss())
                            .show();
                });
            }
            textView.setBackgroundColor(space.getAvailable() == 1 ? Color.GREEN : Color.RED);
        });
        //定位
        mEvent.openBluetooth.observe(getViewLifecycleOwner(), openBluetooth -> {
            mOpenBluetooth = openBluetooth;
            requestBeaconList(parkingId);
        });
        mEvent.openGPS.observe(getViewLifecycleOwner(), openGps -> {
            mOpenGps = openGps;
            requestBeaconList(parkingId);
        });
    }

    /**
     * 根据车位坐标导航
     * @param parkingSpace 导航车位
     */
    public void navigation(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        this.navState = 1;
    }

    /**
     * 获取此停车场蓝牙mac地址
     *
     * @param parkingLotId 停车场id
     */
    public void requestBeaconList(Integer parkingLotId) {
        if (mOpenBluetooth
                && mOpenGps
                && PermissionX.isGranted(this.mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                && PermissionX.isGranted(this.mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mState.beaconRequest.requestBeaconList(parkingLotId);
        }
    }
}