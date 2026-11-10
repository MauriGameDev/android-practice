package com.example.viewmodeldemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    // Fixed conversion rate
    private static final float RATE = 0.74f;

    // Observable conversion result
    private final MutableLiveData<Float> result =
            new MutableLiveData<>();

    // Convert dollars and update LiveData
    public void setAmount(String value) {

        float dollars = Float.parseFloat(value);

        result.setValue(dollars * RATE);
    }

    // Clear the displayed result
    public void clearResult() {
        result.setValue(null);
    }

    // Return observable conversion data
    public MutableLiveData<Float> getResult() {
        return result;
    }
}
