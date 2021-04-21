package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.BuildConfig;
import com.example.personalisedmobilepaindiary.Retrofit.Main;
import com.example.personalisedmobilepaindiary.Retrofit.RetrofitClient;
import com.example.personalisedmobilepaindiary.Retrofit.RetrofitInterface;
import com.example.personalisedmobilepaindiary.Retrofit.SearchResponse;
import com.example.personalisedmobilepaindiary.databinding.HomeFragmentBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeBinding;
    private static final String API_KEY = BuildConfig.WEATHER_API_KEY;
    private RetrofitInterface retrofitInterface;

    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();

        // retrofit
        retrofitInterface = RetrofitClient.getRetrofitService();
        Call<SearchResponse> callAsync = retrofitInterface.weatherSearch("Melbourne", "metric", API_KEY);
        callAsync.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Main main = response.body().main;
                    float temp = main.getTemp();
                    int humidity =  main.getHumidity();
                    int pressure = main.getPressure();
                    homeBinding.weatherData
                            .setText("     "+temp + "                 " + humidity + "%              " + pressure + "hPa");
                }
                else {
                    Log.i("Error", "Response failed");
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_SHORT);
            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeBinding = null;
    }
}
