package com.cetuer.smartparkinglot.utils;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.cetuer.smartparkinglot.R;

import scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView;

/**
 * Created by Cetuer on 2022/3/7 21:39.
 * 网络等待对话框
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        ((Kawaii_LoadingView)findViewById(R.id.loading)).startMoving();
    }
}
