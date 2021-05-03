package com.example.personalisedmobilepaindiary.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
        private MutableLiveData<Float> mTemp;
        private MutableLiveData<Integer> mHumidity;
        private MutableLiveData<Integer> mPressure;
        private MutableLiveData<Integer> mStepGoal;
        private MutableLiveData<Integer> mStep;
        public SharedViewModel(){
            mTemp = new MutableLiveData<>();
            mHumidity = new MutableLiveData<>();
            mPressure = new MutableLiveData<>();
            mStepGoal = new MutableLiveData<>();
            mStep = new MutableLiveData<>();
        }

        public void setInfo(float temp, int humidity, int pressure) {
            mTemp.setValue(temp);
            mHumidity.setValue(humidity);
            mPressure.setValue(pressure);
        }

        public void setStep(int goal, int step){
            mStepGoal.setValue(goal);
            mStep.setValue(step);
        }

        public LiveData<Float> getTemp() {
            return mTemp;
        }
        public LiveData<Integer> getHumidity() { return mHumidity; }
        public LiveData<Integer> getPressure() { return mPressure; }
        public LiveData<Integer> getStepGoal() {return  mStepGoal;}
        public LiveData<Integer> getStep() {return  mStep;}
};
