package com.cetuer.smartparkinglot.ui.page.find_car;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cetuer.smartparkinglot.data.request.FingerprintRequest;

public class FindCarViewModel extends ViewModel {
    public final FingerprintRequest fingerprintRequest = new FingerprintRequest();
    private MutableLiveData<String> mText;

    public FindCarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}