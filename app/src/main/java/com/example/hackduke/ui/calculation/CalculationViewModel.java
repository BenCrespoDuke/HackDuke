package com.example.hackduke.ui.calculation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalculationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calculation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}