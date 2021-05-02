package com.example.personalisedmobilepaindiary.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.personalisedmobilepaindiary.LocationCount;
import com.example.personalisedmobilepaindiary.dao.PainRecordDAO;
import com.example.personalisedmobilepaindiary.databinding.LocationChartBinding;
import com.example.personalisedmobilepaindiary.databinding.StepChartBinding;
import com.example.personalisedmobilepaindiary.fragment.SharedViewModel;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StepChart extends Fragment {
    private StepChartBinding binding;
    private int stepGoal;
    private int step;
    public StepChart(){};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StepChartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        List<PieEntry> entries = new ArrayList<>();

        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getStepGoal().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                stepGoal = integer;
            }
        });
        model.getStep().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                step = i;
                entries.add(new PieEntry(step,"Steps Taken"));
                entries.add(new PieEntry(stepGoal - step, "Steps Remaining"));
                PieDataSet set = new PieDataSet(entries, "");
                PieData data = new PieData(set);
                set.setColors(ColorTemplate.JOYFUL_COLORS);
                data.setValueTextSize(12);
                binding.stepChart.setData(data);
                binding.stepChart.notifyDataSetChanged();
                binding.stepChart.invalidate();

            }
        });
        Description description = new Description();
        description.setText("Steps Taken VS Remaining Steps");
        description.setTextSize(15);
        description.setPosition(750,100);
        binding.stepChart.setHoleRadius(30);
        binding.stepChart.setTransparentCircleRadius(10);
        binding.stepChart.setDescription(description);

        return view;
    }
}
