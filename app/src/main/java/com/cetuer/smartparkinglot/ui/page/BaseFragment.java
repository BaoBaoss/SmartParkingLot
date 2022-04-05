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

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;


/**
 * Create by KunMinX at 19/7/11
 */
public abstract class BaseFragment<V extends ViewDataBinding> extends Fragment {
    protected AppCompatActivity mActivity;
    protected V mBinding;
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity) context;
    }

    protected abstract void initViewModel();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initViewModel();
    }

    protected abstract DataBindingConfig getDataBindingConfig();

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
        mBinding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        mBinding.setLifecycleOwner(this.getViewLifecycleOwner());
        mBinding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        int i = 0;

        for (int length = bindingParams.size(); i < length; ++i) {
            mBinding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }

        return mBinding.getRoot();
    }


    public void onDestroyView() {
        super.onDestroyView();
        this.mBinding.unbind();
        this.mBinding = null;
    }

    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity);
        }
        return mActivityProvider.get(modelClass);
    }

}
