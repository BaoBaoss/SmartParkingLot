package com.cetuer.smartparkinglot.ui.page.guidance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuidanceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GuidanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}