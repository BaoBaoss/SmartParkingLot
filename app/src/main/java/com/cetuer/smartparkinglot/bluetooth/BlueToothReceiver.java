package com.cetuer.smartparkinglot.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.utils.GpsUtils;
import com.cetuer.smartparkinglot.utils.KLog;
import com.cetuer.smartparkinglot.utils.ToastUtils;

public class BlueToothReceiver extends BroadcastReceiver {
    private SharedViewModel mEvent;


    public BlueToothReceiver() {
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    KLog.i("STATE_TURNING_OFF");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    KLog.i("STATE_OFF");
                    mEvent.openBluetooth.setValue(false);
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    KLog.i("STATE_TURNING_ON");
                    break;
                case BluetoothAdapter.STATE_ON:
                    KLog.i("STATE_ON");
                    mEvent.openBluetooth.setValue(true);
                    break;
            }
        } else if (action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
            mEvent.openGPS.setValue(GpsUtils.checkLocation(context));
        }
    }
}