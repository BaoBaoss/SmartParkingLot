package com.cetuer.smartparkinglot.ui.page.carport_query;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.databinding.FragmentCarportQueryBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.adapter.CarportQueryAdapter;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;

import java.util.stream.Collectors;

public class CarportQueryFragment extends BaseFragment<FragmentCarportQueryBinding> {

    private SharedViewModel mEvent;
    private CarportQueryViewModel mState;
    private CarportQueryAdapter adapter;
    //需要查找的停车场名称
    private String searchParkingName;

    @Override
    protected void initViewModel() {
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
        mState = getFragmentScopeViewModel(CarportQueryViewModel.class);
        adapter = new CarportQueryAdapter(this.mActivity);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_carport_query, BR.vm, mState)
                .addBindingParam(BR.adapter, adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获得所有停车场信息
        mState.parkingLotRequest.requestList();
        mState.parkingLotRequest.getParkingLotList().observe(getViewLifecycleOwner(), parkingLots -> {
            if (parkingLots == null) {
                return;
            }
            //模糊匹配
            parkingLots = parkingLots.stream().filter(parkingLot -> {
                if (searchParkingName == null || searchParkingName.trim().isEmpty()) {
                    return true;
                }
                return parkingLot.getName().contains(searchParkingName);
            }).collect(Collectors.toList());
            mState.parkingLotList.setValue(parkingLots);
        });

        mBinding.btnSearch.setOnClickListener(v -> {
            //搜索条件
            searchParkingName = mBinding.editSearch.getText().toString();
            //发起查询所有停车场请求
            mState.parkingLotRequest.requestList();
        });
    }
}