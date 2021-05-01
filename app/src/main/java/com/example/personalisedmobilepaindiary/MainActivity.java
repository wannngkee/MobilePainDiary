package com.example.personalisedmobilepaindiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.personalisedmobilepaindiary.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EmojiManager.install(new IosEmojiProvider());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
        return loginEmail;
    }
}