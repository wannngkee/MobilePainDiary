package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.personalisedmobilepaindiary.R;
import com.example.personalisedmobilepaindiary.chart.LocationChart;
import com.example.personalisedmobilepaindiary.chart.StepChart;
import com.example.personalisedmobilepaindiary.databinding.ReportFragmentBinding;

public class ReportFragment extends Fragment {
    private ReportFragmentBinding reportBinding;

    public ReportFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reportBinding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = reportBinding.getRoot();
        replaceFragment(new LocationChart());
        reportBinding.graphOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.location_chart:
                        replaceFragment(new LocationChart());
                        break;
                    case R.id.step_chart:
                        replaceFragment(new StepChart());
                        break;
                    case R.id.weather_chart:
//                        replaceFragment(new LocationChart());
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reportBinding = null;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.graph_view, nextFragment);
        fragmentTransaction.commit();
    };
};
