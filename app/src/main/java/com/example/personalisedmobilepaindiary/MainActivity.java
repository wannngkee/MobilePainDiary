package com.example.personalisedmobilepaindiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.personalisedmobilepaindiary.databinding.ActivityMainBinding;
import com.example.personalisedmobilepaindiary.entity.PainRecord;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    public static String email = "aa@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        long currentTime = System.currentTimeMillis();
        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        long scheduledTime = calendar.getTimeInMillis();
        long delay = scheduledTime - currentTime;
        //work manager
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(DatabaseWorker.class,24,TimeUnit.HOURS)
                .setInitialDelay(delay, TimeUnit.MINUTES)
                .addTag("send_record")
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("send_record", ExistingPeriodicWorkPolicy.REPLACE,workRequest);

        // fragments
        setSupportActionBar(binding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_data_fragment,
                R.id.nav_record_fragment,
                R.id.nav_report_fragment,
                R.id.nav_map_fragment
        ).setOpenableLayout(binding.drawerLayout).build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.navView,navController);
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController, mAppBarConfiguration);
    }

    public String getEmail(){
        Intent intent = getIntent();
        String loginEmail = intent.getStringExtra("loginEmail");
        email = loginEmail;
        return loginEmail;
    }
}