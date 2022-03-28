package com.cetuer.smartparkinglot.ui.page.find_car;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindCarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FindCarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}