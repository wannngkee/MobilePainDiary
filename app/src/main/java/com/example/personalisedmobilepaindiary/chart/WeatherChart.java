package com.example.personalisedmobilepaindiary.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.personalisedmobilepaindiary.LocationCount;
import com.example.personalisedmobilepaindiary.databinding.LocationChartBinding;
import com.example.personalisedmobilepaindiary.databinding.WeatherChartBinding;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherChart extends Fragment {
    private WeatherChartBinding binding;
    private PainRecordViewModel painRecordViewModel;
    private String start;
    private String end;

    public WeatherChart(){};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WeatherChartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        List<String> weathers = new ArrayList<String>();
        weathers.add("Temperature");
        weathers.add("Humidity");
        weathers.add("Pressure");

        final ArrayAdapter<String> spinnerAdapter = new
                ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, weathers);
        binding.weatherOption.setAdapter(spinnerAdapter);

        // popup date ranger picker
        MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        final  MaterialDatePicker materialDatePicker = builder.build();

        binding.startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "date_ranger_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        Long startDate = selection.first;
                        Long endDate = selection.second;
                        start = new SimpleDateFormat("dd/MM/yyy").format(new Date(startDate));
                        end = new SimpleDateFormat("dd/MM/yyy").format(new Date(endDate));
                        Log.i("date", start+end);
                        binding.startDate.setText(start);
                        binding.endDate.setText(end);
                    }
                });}
        }});

        binding.endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "date_ranger_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        Long startDate = selection.first;
                        Long endDate = selection.second;
                        start = new SimpleDateFormat("dd/MM/yyy").format(new Date(startDate));
                        end = new SimpleDateFormat("dd/MM/yyy").format(new Date(endDate));
                        Log.i("date", start+end);
                        binding.startDate.setText(start);
                        binding.endDate.setText(end);
                    }
                });}
            }});

//        List<PieEntry> entries = new ArrayList<>();
//        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.
//                getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);
//
//        painRecordViewModel.getLocationCount().observe(getViewLifecycleOwner(), new Observer<List<LocationCount>>() {
//            @Override
//            public void onChanged(@Nullable final List<LocationCount> locationCounts) {
//                for (LocationCount locationCount : locationCounts) {
//                    entries.add(new PieEntry(locationCount.getCount(), locationCount.getLocation()));
//                }
//                PieDataSet set = new PieDataSet(entries, " ");
//                set.setColors(ColorTemplate.COLORFUL_COLORS);
//                PieData data = new PieData(set);
//                data.setValueFormatter(new PercentFormatter(binding.weatherChart));
//                data.setValueTextSize(12);
//                binding.weatherChart.setData(data);
//                binding.weatherChart.notifyDataSetChanged();
//                binding.weatherChart.invalidate();
//            }
//
//        });
//        Description description = new Description();
//        description.setText("Pain Location Percentage");
//        description.setTextSize(15);
//        description.setPosition(650,100);
//        binding.weatherChart.setDescription(description);
        return view;
    }
}
