package com.cetuer.smartparkinglot.ui.page.find_car;

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
import com.cetuer.smartparkinglot.databinding.FragmentFindCarBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FindCarFragment extends BaseFragment<FragmentFindCarBinding> {

    private FindCarViewModel mState;
    private SharedViewModel mEvent;
    //车位信息
    private ParkingSpace parkingSpace;
    //终点坐标
    private BeaconPoint endPoint;
    //保存坐标
    private List<List<TextView>> coordinate;
    private boolean mOpenBluetooth;
    private boolean mOpenGps;
    //导航状态：0->未开始导航,1->导航中,2->导航结束
    private Integer navState = 1;
    //扫描十次定位一次
    private int scanCount = 10;
    //上一个定位的坐标
    private BeaconPoint lastLocationPoint;
    //上一个定位的坐标颜色
    private Integer lastLocationColor;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(FindCarViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
        coordinate = new ArrayList<>();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_find_car, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //点击按钮开始寻车
        mBinding.restart.setOnClickListener(v -> mState.carRequest.canFindCar());
        mState.carRequest.getCanFindCar().observe(this.mActivity, canFindCar -> {
            if (!canFindCar) {
                DialogUtils.showBasicDialogNoCancel(this.mActivity, "提示", "无法寻车，没有车或者未停车").show();
                return;
            }
            mBinding.restart.setEnabled(false);
            //请求停车车位信息
            mState.spaceRequest.requestCarSpace();
        });
        //得到停车车位信息
        mState.spaceRequest.getCarSpace().observe(getViewLifecycleOwner(), parkingSpace -> {
            this.parkingSpace = parkingSpace;
            //获取对应的坐标终点
            mState.beaconRequest.requestEndPoint(parkingSpace.getParkingLotId());
        });
        //得到坐标终点，绘制地图
        mState.beaconRequest.getEndPoint().observe(getViewLifecycleOwner(), beaconPoint -> {
            endPoint = beaconPoint;
            drawMap();
        });

        mState.fingerprintRequest.getLocationPoint().observe(getViewLifecycleOwner(), point -> {
            //上一个位置还原颜色，当前位置变色
            if (lastLocationPoint != null) {
                coordinate.get(lastLocationPoint.getX()).get(lastLocationPoint.getY()).setBackgroundColor(lastLocationColor);
            }
            lastLocationPoint = point;
            lastLocationColor = ((ColorDrawable) coordinate.get(point.getX()).get(point.getY()).getBackground()).getColor();
            coordinate.get(point.getX()).get(point.getY()).setBackgroundColor(Color.BLUE);
            //如果正在导航，并且当前位置与停车车位坐标一致则寻车成功
            if (this.navState == 1 && point.getX().equals(parkingSpace.getX()) && point.getY().equals(parkingSpace.getY())) {
                //发送请求
                mState.spaceRequest.requestFindCar(parkingSpace.getId());
                //更改导航状态为已完成
                this.navState = 2;
            }
        });
        mState.spaceRequest.getFindCar().observe(getViewLifecycleOwner(), unused -> DialogUtils.showBasicDialogNoCancel(this.mActivity, "提示", "寻车成功！"));
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
        //x行
        for (int i = 0; i < endPoint.getX(); i++) {
            LinearLayout linearLayout = new LinearLayout(this.mActivity);
            //保存本行坐标
            ArrayList<TextView> points = new ArrayList<>();
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);
            //y列
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
        //给停车位置上色为紫色
        coordinate.get(parkingSpace.getX()).get(parkingSpace.getY()).setBackgroundColor(0XFFFF00FF);
        //定位
        mEvent.list.observe(getViewLifecycleOwner(), bleDevices -> {
            //导航完成不再定位
            if (navState == 2) {
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
        mEvent.beaconRequest.requestBeaconList(parkingSpace.getParkingLotId());
    }
}