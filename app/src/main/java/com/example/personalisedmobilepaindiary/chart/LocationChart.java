package com.example.personalisedmobilepaindiary.chart;

import android.os.Bundle;
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
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class LocationChart extends Fragment {
    private LocationChartBinding binding;
    private PainRecordViewModel painRecordViewModel;
    public LocationChart(){};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LocationChartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        List<PieEntry> entries = new ArrayList<>();
        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.
                getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);

        painRecordViewModel.getLocationCount().observe(getViewLifecycleOwner(), new Observer<List<LocationCount>>() {
            @Override
            public void onChanged(@Nullable final List<LocationCount> locationCounts) {
                for (LocationCount locationCount : locationCounts) {
                    entries.add(new PieEntry(locationCount.getCount(), locationCount.getLocation()));
                }
                PieDataSet set = new PieDataSet(entries, " ");
                set.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData data = new PieData(set);
                data.setValueFormatter(new PercentFormatter(binding.locationPieChart));
                data.setValueTextSize(12);
                binding.locationPieChart.setData(data);
                binding.locationPieChart.notifyDataSetChanged();
                binding.locationPieChart.invalidate();
            }

        });
        Description description = new Description();
        description.setText("Pain Location Percentage");
        description.setTextSize(15);
        description.setPosition(650,100);
        binding.locationPieChart.setHoleRadius(0);
        binding.locationPieChart.setTransparentCircleRadius(0);
        binding.locationPieChart.setDescription(description);
        binding.locationPieChart.setUsePercentValues(true);
        return view;
    }
}
