package com.example.personalisedmobilepaindiary.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
        private MutableLiveData<String> mTemp;
        private MutableLiveData<String> mHumidity;
        private MutableLiveData<String> mPressure;
        public SharedViewModel(){
            mTemp = new MutableLiveData<>();
            mHumidity = new MutableLiveData<>();
            mPressure = new MutableLiveData<>();
        }

        public void setInfo(String temp, String humidity, String pressure) {
            mTemp.setValue(temp);
            mHumidity.setValue(humidity);
            mPressure.setValue(pressure);
        }
        public LiveData<String> getTemp() {
            return mTemp;
        }
        public LiveData<String> getHumidity() { return mHumidity; }
        public LiveData<String> getPressure() {
            return mPressure;
        }
};
