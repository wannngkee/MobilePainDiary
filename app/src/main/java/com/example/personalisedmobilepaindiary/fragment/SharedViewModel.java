package com.example.personalisedmobilepaindiary.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
        private MutableLiveData<String> mTemp;
        private MutableLiveData<String> mHumidity;
        private MutableLiveData<String> mPressure;
        private MutableLiveData<Integer> mStepGoal;
        private MutableLiveData<Integer> mStep;
        public SharedViewModel(){
            mTemp = new MutableLiveData<>();
            mHumidity = new MutableLiveData<>();
            mPressure = new MutableLiveData<>();
            mStepGoal = new MutableLiveData<>();
            mStep = new MutableLiveData<>();
        }

        public void setInfo(String temp, String humidity, String pressure) {
            mTemp.setValue(temp);
            mHumidity.setValue(humidity);
            mPressure.setValue(pressure);
        }

        public void setStep(int goal, int step){
            mStepGoal.setValue(goal);
            mStep.setValue(step);
        }

        public LiveData<String> getTemp() {
            return mTemp;
        }
        public LiveData<String> getHumidity() { return mHumidity; }
        public LiveData<String> getPressure() {
            return mPressure;
        }
        public LiveData<Integer> getStepGoal() {return  mStepGoal;}
        public LiveData<Integer> getStep() {return  mStep;}
};
