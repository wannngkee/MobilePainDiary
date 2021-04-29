package com.example.personalisedmobilepaindiary.fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.Notification;
import com.example.personalisedmobilepaindiary.R;
import com.example.personalisedmobilepaindiary.databinding.DataFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataFragment extends Fragment {
    private DataFragmentBinding dataBinding;
    public DataFragment(){}
    int painLevel;
    String location = "";
    String mood = "";
    String step = "";

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
        dataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = dataBinding.timePicker.getCurrentHour();
                int minute = dataBinding.timePicker.getCurrentMinute();
                painLevel = Math.round(dataBinding.level.getValue());
                location = dataBinding.location.getSelectedItem().toString();
                step = dataBinding.currentStep.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
                if (mood.isEmpty() || step.isEmpty() || location.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter all the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    dataBinding.timePicker.setEnabled(false);
                    dataBinding.level.setEnabled(false);
                    dataBinding.location.setEnabled(false);
                    for (int i = 0; i < dataBinding.mood.getChildCount(); i++) {
                        dataBinding.mood.getChildAt(i).setEnabled(false);
                    }
                    dataBinding.stepGoal.setEnabled(false);
                    dataBinding.currentStep.setEnabled(false);
                    Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            hour,
                            minute - 2,
                            0 );
                    setAlarm(calendar.getTimeInMillis());

                }
            }
        });

        dataBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dataBinding.timePicker.setEnabled(true);
                    dataBinding.level.setEnabled(true);
                    dataBinding.location.setEnabled(true);
                    for (int i = 0; i < dataBinding.mood.getChildCount(); i++) {
                        dataBinding.mood.getChildAt(i).setEnabled(true);
                    }
                    dataBinding.stepGoal.setEnabled(true);
                    dataBinding.currentStep.setEnabled(true);
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
            int importantce = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Pain Diary", name, importantce);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void setAlarm(long timeInMillis) {
        Intent intent = new Intent(getActivity(), Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
    }

}
