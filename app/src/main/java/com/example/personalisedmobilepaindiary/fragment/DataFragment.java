package com.example.personalisedmobilepaindiary.fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.personalisedmobilepaindiary.MainActivity;
import com.example.personalisedmobilepaindiary.Notification;
import com.example.personalisedmobilepaindiary.R;
import com.example.personalisedmobilepaindiary.adapter.RecyclerViewAdapter;
import com.example.personalisedmobilepaindiary.databinding.DataFragmentBinding;
import com.example.personalisedmobilepaindiary.entity.PainRecord;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataFragment extends Fragment {
    private DataFragmentBinding dataBinding;
    private PainRecordViewModel painRecordViewModel;
    private MainActivity activity = (MainActivity) getActivity();
    private RecyclerViewAdapter adapter;
    private List<PainRecord> records;
    public DataFragment(){}
    int painLevel;
    String location = "";
    String mood = "";
    float temp;
    int humidity;
    int pressure;
//    String email = activity.getEmail();
    String email = "aa@gmail.com";
    int step;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createNotificationChannel();
        dataBinding = DataFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();

        dataBinding.timePicker.setIs24HourView(true);
        dataBinding.timePicker.setCurrentHour(16);
        dataBinding.timePicker.setCurrentMinute(0);


        List<String> list = new ArrayList<String>();
        list.add("Back");
        list.add("Neck");
        list.add("Head");
        list.add("Knees");
        list.add("Heaps");
        list.add("Abdomen");
        list.add("Elbows");
        list.add("Shoulders");
        list.add("Shins");
        list.add("Jaw");
        list.add("Facial");

        final ArrayAdapter<String> spinnerAdapter = new
                ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, list);
        dataBinding.location.setAdapter(spinnerAdapter);

        dataBinding.mood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.very_low:
                        mood = "Very Low";
                        break;
                    case R.id.low:
                        mood = "Low";
                        break;
                    case R.id.average:
                        mood = "Average";
                        break;
                    case R.id.good:
                        mood = "Good";
                        break;
                    case R.id.very_good:
                        mood = "Very Good";
                        break;
                    default:
                        mood = null;
                        break;
                }
            }
        });

        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                getActivity().getApplication()).create(PainRecordViewModel.class);
        dataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strStepGoal = dataBinding.stepGoal.getText().toString();
                int stepGoal = Integer.parseInt(strStepGoal);
                int hour = dataBinding.timePicker.getCurrentHour();
                int minute = dataBinding.timePicker.getCurrentMinute();
                painLevel = Math.round(dataBinding.level.getValue());
                location = dataBinding.location.getSelectedItem().toString();
                String strStep = dataBinding.currentStep.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
                if (mood.isEmpty() || strStep.isEmpty() || location.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter all the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    getWeather();
                    Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
                    setReminderTime(hour,minute - 2);
                    step = Integer.parseInt(strStep);
                    // pass step goal and current step to viewModel
                    setStep(stepGoal, step);
                    setEnable(false);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        CompletableFuture<PainRecord> painRecordCompletableFuture = painRecordViewModel.findByDateFuture(date,email);
                        painRecordCompletableFuture.thenApply(painRecord -> {
                            if (painRecord != null) {
                                painRecord.level = painLevel;
                                painRecord.location = location;
                                painRecord.mood = mood;
                                painRecord.step = step;
                                painRecord.temp = temp;
                                painRecord.humidity = humidity;
                                painRecord.pressure = pressure;
                                painRecordViewModel.update(painRecord);
                            } else {
                                PainRecord record = new PainRecord(painLevel, location, mood, step, date, temp, humidity, pressure, email);
                                painRecordViewModel.insert(record);
                            }
                            return painRecord;
                        });
                    }
                }
                }
            });

        dataBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnable(true);
                }
            }
        );

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBinding = null;
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PainDiaryChannel";
            String description = "Channel for Pain Diary";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Pain Diary", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    // set reminding time
    private void setReminderTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                hour,
                minute,
                0 );
        setAlarm(calendar.getTimeInMillis());
    }

    private void setAlarm(long timeInMillis) {
        Intent intent = new Intent(getActivity(), Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    // get temp, humidity, pressure form viewModel
    public void getWeather() {
        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getTemp().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                temp = aFloat;
            }
        });
        model.getHumidity().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                humidity = integer;
            }
        });
        model.getPressure().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                pressure = integer;
            }
        });
    }

    // set step goal and current steps
    public void setStep(int goal, int step){
        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.setStep(goal,step);
    }

    public void setEnable(boolean enable) {
        dataBinding.timePicker.setEnabled(enable);
        dataBinding.level.setEnabled(enable);
        dataBinding.location.setEnabled(enable);
        for (int i = 0; i < dataBinding.mood.getChildCount(); i++) {
            dataBinding.mood.getChildAt(i).setEnabled(enable);
        }
        dataBinding.stepGoal.setEnabled(enable);
        dataBinding.currentStep.setEnabled(enable);
    }


}
