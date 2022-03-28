package com.cetuer.smartparkinglot.ui.page.guidance;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.fragment.NavHostFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.data.bean.ParkingLot;
import com.cetuer.smartparkinglot.databinding.FragmentGuidanceBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.KLog;

public class GuidanceFragment extends BaseFragment<FragmentGuidanceBinding> {

    private GuidanceViewModel mState;
    private AMap mAMap;
    //是否为首次定位
    private boolean firstLocation = true;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(GuidanceViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_guidance, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.map.onCreate(savedInstanceState);
        initMap();
        addLifecycleListener();
        mState.parkingLotRequest.requestList();
        mState.parkingLotRequest.getParkingLotList().observe(getViewLifecycleOwner(), parkingLots -> {
            for (ParkingLot parkingLot : parkingLots) {
                mAMap.addMarker(new MarkerOptions().
                        position(new LatLng(parkingLot.getLatitude(), parkingLot.getLongitude()))
                        .title(parkingLot.getName()));
            }
        });
    }

    public void initMap() {
        this.mAMap = mBinding.map.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        myLocationStyle.interval(1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(true);
        //关闭放大缩小按钮
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        //打开定位当前位置按钮
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        //首次定位将地图移动到当前位置
        mAMap.setOnMyLocationChangeListener(location -> {
            if (!firstLocation) {
                return;
            }
            firstLocation = false;
            mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 18, 30, 0)));
        });
        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.layout_info_window, null);
                TextView title = view.findViewById(R.id.title);
                title.setText(marker.getTitle());
                RelativeLayout nav = view.findViewById(R.id.navigation);
                //调起导航
                nav.setOnClickListener(v -> startAMapNavi(marker));
                return view;
            }
        });
        //点击标记回调
        mAMap.setOnMarkerClickListener(marker -> {
            Location myLocation = GuidanceFragment.this.mAMap.getMyLocation();
            LatLng markerLocation = marker.getOptions().getPosition();
            //点击当前位置不显示导航弹出框
            return myLocation.getLongitude() == markerLocation.longitude && myLocation.getLatitude() == markerLocation.latitude;
        });
    }

    public void addLifecycleListener() {
        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            void onResume() {
                mBinding.map.onResume();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            void onPause() {
                mBinding.map.onPause();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void onDestroy() {
                mBinding.map.onDestroy();
            }
        });
    }

    private MaterialDialog dialog;

    //导航
    private void startAMapNavi(Marker marker) {
        AmapNaviParams params = new AmapNaviParams(new Poi("我的位置", new LatLng(mAMap.getMyLocation().getLatitude(), mAMap.getMyLocation().getLongitude()), ""), null, new Poi(marker.getTitle(), marker.getPosition(), ""), AmapNaviType.DRIVER);
        params.setUseInnerVoice(true);
        AmapNaviPage.getInstance().showRouteActivity(this.mActivity, params, new INaviInfoCallback() {
            @Override
            public void onInitNaviFailure() {
            }

            @Override
            public void onGetNavigationText(String s) {
            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
            }

            @Override
            public void onArriveDestination(boolean b) {
            }

            @Override
            public void onStartNavi(int i) {
            }

            @Override
            public void onCalculateRouteSuccess(int[] ints) {
            }

            @Override
            public void onCalculateRouteFailure(int i) {
            }

            @Override
            public void onStopSpeaking() {
            }

            @Override
            public void onReCalculateRoute(int i) {
            }

            @Override
            public void onExitPage(int i) {
                if (dialog != null && dialog.isShowing()) return;
                dialog = DialogUtils.showBasicDialog(GuidanceFragment.this.mActivity, "提示", "是否进入停车场内部导航？")
                        .onPositive((dialog, which) -> {
                            //传递当前停车场经纬度
                            Bundle args = new Bundle();
                            args.putDouble("longitude", marker.getPosition().longitude);
                            args.putDouble("latitude", marker.getPosition().latitude);
                            NavHostFragment.findNavController(GuidanceFragment.this).navigate(R.id.action_guidance_to_configuration, args);
                        })
                        .onNegative((dialog, which) -> dialog.dismiss())
                        .show();
            }

            @Override
            public void onStrategyChanged(int i) {
            }

            @Override
            public void onArrivedWayPoint(int i) {
            }

            @Override
            public void onMapTypeChanged(int i) {
            }

            @Override
            public void onNaviDirectionChanged(int i) {
            }

            @Override
            public void onDayAndNightModeChanged(int i) {
            }

            @Override
            public void onBroadcastModeChanged(int i) {
            }

            @Override
            public void onScaleAutoChanged(boolean b) {
            }

            @Override
            public View getCustomMiddleView() {
                return null;
            }

            @Override
            public View getCustomNaviView() {
                return null;
            }

            @Override
            public View getCustomNaviBottomView() {
                return null;
            }
        });
    }
}