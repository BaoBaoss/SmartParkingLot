package com.cetuer.smartparkinglot.ui.page.guidance;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.bluetooth.BleManager;
import com.cetuer.smartparkinglot.data.bean.BeaconDevice;
import com.cetuer.smartparkinglot.databinding.FragmentGuidanceBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.adapter.IBeaconAdapter;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.GpsUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;
import com.permissionx.guolindev.PermissionX;

import java.util.stream.Collectors;

public class GuidanceFragment extends BaseFragment<FragmentGuidanceBinding> {

    private GuidanceViewModel mState;
    private SharedViewModel mEvent;
    private IBeaconAdapter mIBeaconAdapter;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(GuidanceViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mIBeaconAdapter = new IBeaconAdapter(this.mActivity);
        mIBeaconAdapter.setOnItemClickListener((viewId, item, position) -> {
            DialogUtils.showBasicDialog(mActivity, "提示", "是否设置参数？")
                    .onPositive((dialog, which) -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt("devicePosition", position);
                        NavHostFragment.findNavController(this).navigate(R.id.action_guidance_to_configuration, bundle);
                    })
                    .show();
        });
        return new DataBindingConfig(R.layout.fragment_guidance, BR.vm, mEvent)
                .addBindingParam(BR.adapter, mIBeaconAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.list.setItemAnimator(null);
    }
}