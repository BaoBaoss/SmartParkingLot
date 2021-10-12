package com.cetuer.smartparkinglot.ui.page.guidance;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.databinding.FragmentConfigurationBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;

/**
 * 配置参数
 */
public class ConfigurationFragment extends BaseFragment<FragmentConfigurationBinding> {

    private ConfigurationViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(ConfigurationViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_configuration, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}