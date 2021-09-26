package com.cetuer.smartparkinglot.ui.page.carport_query;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarportQueryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CarportQueryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}