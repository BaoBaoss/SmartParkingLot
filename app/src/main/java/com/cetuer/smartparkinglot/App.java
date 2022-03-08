/*
 *
 *  * Copyright 2018-present KunMinX
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.cetuer.smartparkinglot;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.Utils;

/**
 * Create by KunMinX at 2020/7/4
 */
public class App extends Application implements ViewModelStoreOwner {
    private static App sInstance;

    private ViewModelStore mAppViewModelStore;

    private ViewModelProvider mApplicationProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mAppViewModelStore = new ViewModelStore();
        Utils.init(this);
        KLog.init(true);
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    /**
     * 获得当前app运行的Application
     */
    public static App getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    public  <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((App) this.getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }
}
