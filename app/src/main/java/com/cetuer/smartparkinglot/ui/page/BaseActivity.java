/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cetuer.smartparkinglot.ui.page;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.data.response.manager.NetworkStateManager;

/**
 * Create by KunMinX at 19/8/1
 */
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {

    protected V mBinding;
    private ViewModelProvider mActivityProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initViewModel();
        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
        mBinding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        mBinding.setLifecycleOwner(this);
        mBinding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        int i = 0;
        for(int length = bindingParams.size(); i < length; ++i) {
            mBinding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        getLifecycle().addObserver(NetworkStateManager.getInstance());
    }

    /**
     * 初始化ViewModel
     */
    protected abstract void initViewModel();

    /**
     * 初始化DataBinding配置
     * @return DataBindingConfig
     */
    protected abstract DataBindingConfig getDataBindingConfig();

    protected void onDestroy() {
        super.onDestroy();
        this.mBinding.unbind();
        this.mBinding = null;
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

}
