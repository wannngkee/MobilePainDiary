package com.example.personalisedmobilepaindiary.fragment;

import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.BuildConfig;
import com.example.personalisedmobilepaindiary.databinding.MapFragmentBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

public class MapFragment extends Fragment {
    private MapFragmentBinding mapBinding;
    String strAddress;
    LatLng latlng = new LatLng(-45.8696744, 170.50301439999998);
    public MapFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String token = BuildConfig.MAPBOX_ACCESS_TOKEN;
        Mapbox.getInstance(getActivity(),token);
        mapBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = mapBinding.getRoot();
        mapBinding.mapView.onCreate(savedInstanceState);

        //get input address
        mapBinding.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAddress = mapBinding.address.getText().toString();
                getLocationFromAddress(strAddress);
                Log.d("latlng", "value:" + latlng);
            }
        });

        // show on the map
        mapBinding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        CameraPosition position = new CameraPosition.Builder()
                                            .target(latlng)
                                            .zoom(13)
                                            .build();
                        mapboxMap.setCameraPosition(position);
                    }
                });
            }});

        return view;
    }

    public void getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                latlng = null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latlng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mapBinding.mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapBinding.mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapBinding.mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapBinding.mapView.onStop();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapBinding.mapView.onSaveInstanceState(outState);
    }
    @Override public void onLowMemory() {
        super.onLowMemory();
        mapBinding.mapView.onLowMemory();
    }
    @Override public void onDestroy() {
        super.onDestroy();
        mapBinding.mapView.onDestroy();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapBinding = null;
    }
}
