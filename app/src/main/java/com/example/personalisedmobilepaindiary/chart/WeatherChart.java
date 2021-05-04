package com.example.personalisedmobilepaindiary.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.personalisedmobilepaindiary.databinding.WeatherChartBinding;
import com.example.personalisedmobilepaindiary.entity.PainRecord;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WeatherChart extends Fragment {
    private WeatherChartBinding binding;
    private PainRecordViewModel painRecordViewModel;
    private String start;
    private String end;
    Long startDate = null;
    Long endDate = null;
    long realDate;
    //    String email = activity.getEmail();
    String email = "aa@gmail.com";
    String weatherOption;
    double pValue;
    double rValue;

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

        weatherOption = binding.weatherOption.getSelectedItem().toString();

        binding.weatherOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weatherOption = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // popup date ranger picker
        MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        final  MaterialDatePicker materialDatePicker = builder.build();
        binding.dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "date_ranger_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        startDate = selection.first;
                        endDate = selection.second;
                        start = new SimpleDateFormat("dd/MM/yyyy").format(new Date(startDate));
                        end = new SimpleDateFormat("dd/MM/yyyy").format(new Date(endDate));
                        binding.dateBtn.setText(start + "-" + end);
                    }
                });
            }
        });

        binding.chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDate == null && endDate == null){
                    Toast.makeText(getActivity(),"Please select the date period", Toast.LENGTH_SHORT).show();
                }
                else{
                    binding.weatherChart.setVisibility(View.VISIBLE);
                    binding.correlationBtn.setVisibility(View.VISIBLE);
                    List<Entry> levelEntries = new ArrayList<>();
                    List<Entry> tempEntries = new ArrayList<>();
                    List<Entry> humidityEntries = new ArrayList<>();
                    List<Entry> pressureEntries = new ArrayList<>();

                    // lists for correlation
                    List<Double> levels = new ArrayList<>();
                    List<Double> temps = new ArrayList<>();
                    List<Double> humidities = new ArrayList<>();
                    List<Double> pressures = new ArrayList<>();
                    painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.
                            getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);
                    painRecordViewModel.getAllPainRecords(email).observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
                        @Override
                        public void onChanged(@Nullable final List<PainRecord> painRecords) {
                            for (PainRecord painRecord : painRecords){
                                try {
                                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                                    Date d = f.parse(painRecord.date);
                                    realDate = d.getTime();
                                    if (startDate <= realDate && realDate <= endDate){
                                        levelEntries.add(new Entry(realDate,painRecord.level));
                                        tempEntries.add(new Entry(realDate,painRecord.temp));
                                        humidityEntries.add(new Entry(realDate,painRecord.humidity));
                                        pressureEntries.add(new Entry(realDate,painRecord.pressure));
                                        levels.add((double) painRecord.level);
                                        temps.add((double) painRecord.temp);
                                        humidities.add((double) painRecord.humidity);
                                        pressures.add((double) painRecord.pressure);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            LineDataSet set = new LineDataSet(levelEntries,"Pain Level");
                            Collections.sort(levelEntries, new EntryXComparator());
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);
                            set.setColor(ColorTemplate.rgb("#ca0020"));
                            set.setCircleColor(ColorTemplate.rgb("#ca0020"));
                            LineDataSet set2;
                            switch (weatherOption){
                                case "Humidity":
                                    set2 = new LineDataSet(humidityEntries,"Humidity");
                                    Collections.sort(humidityEntries, new EntryXComparator());
                                    testCorrelation( levels, humidities);
                                    break;
                                case "Pressure":
                                    set2 = new LineDataSet(pressureEntries,"Pressure");
                                    Collections.sort(pressureEntries, new EntryXComparator());
                                    testCorrelation( levels, pressures);
                                    break;
                                default:
                                    set2 = new LineDataSet(tempEntries,"Temperature");
                                    Collections.sort(tempEntries, new EntryXComparator());
                                    testCorrelation( levels, temps);
                                    break;
                            }
                            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
                            set2.setColor(ColorTemplate.rgb("#0571b0"));
                            set2.setCircleColor(ColorTemplate.rgb("#0571b0"));
                            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(set);
                            dataSets.add(set2);
                            LineData data = new LineData(dataSets);
                            binding.weatherChart.setData(data);
                            binding.weatherChart.notifyDataSetChanged();
                            binding.weatherChart.invalidate();
                            Legend legend = binding.weatherChart.getLegend();
                            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                            legend.setDrawInside(true);
                            legend.setYOffset(20);
                        }
                    });

                    ValueFormatter formatter = new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            Date date = new Date((long) value);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM");
                            String strDate = df.format(date);
                            return strDate;
                        }
                    };
                    XAxis xAxis = binding.weatherChart.getXAxis();
                    xAxis.setValueFormatter(formatter);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    Description description = new Description();
                    description.setText("Pain and " + weatherOption +" Line Chart");
                    description.setTextSize(15);
                    description.setPosition(780,30);
                    binding.weatherChart.setDescription(description);
                }
            }
        });

        binding.correlationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rValue.setText("Correlation R value:\n" + rValue);
                binding.pValue.setText("P value:\n"+ + pValue);
            }
        });

        return view;

    }
    public void testCorrelation(List<Double> list1, List<Double>  list2){
        double[][] data = new double[list1.size()][2];
        for (int i = 0; i < list1.size(); i++) {
            data[i][0] = list1.get(i);
            data[i][1] = list2.get(i);

        };

        // create a realmatrix
        RealMatrix m = MatrixUtils.createRealMatrix(data);

        // correlation test (another method): x-y
        PearsonsCorrelation pc = new PearsonsCorrelation(m);
            RealMatrix corM = pc.getCorrelationMatrix();

        // significant test of the correlation coefficient (p-value)
        RealMatrix pM = pc.getCorrelationPValues();
        pValue=pM.getEntry(0, 1);
        rValue=corM.getEntry(0, 1);
    }
}
