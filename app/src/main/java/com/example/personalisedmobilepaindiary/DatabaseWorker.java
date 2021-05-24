package com.example.personalisedmobilepaindiary;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.personalisedmobilepaindiary.dao.PainRecordDAO;
import com.example.personalisedmobilepaindiary.database.PainRecordDatabase;
import com.example.personalisedmobilepaindiary.entity.PainRecord;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatabaseWorker extends Worker {
    PainRecord painRecord;
    public DatabaseWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dailyRecord = database.getReference();
        PainRecord record = PainRecordDatabase.getInstance(getApplicationContext()).painRecordDAO().findByDate(date, MainActivity.email);
        if (record != null) {
            dailyRecord.child("record").push().setValue(record);
            Log.i("record send", ""+record.date+ " level" + record.level + " "+record.location);
        } else {
            dailyRecord.child("record").push().setValue("No record for"+ date);
            Log.i("No record for", " "+ date);
        }

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
