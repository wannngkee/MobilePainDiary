package com.example.personalisedmobilepaindiary.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.BuildConfig;
import com.example.personalisedmobilepaindiary.R;
import com.example.personalisedmobilepaindiary.databinding.MapFragmentBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.util.List;

public class MapFragment extends Fragment {
    private MapFragmentBinding mapBinding;
    String strAddress;
    LatLng latlng = null;
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
                strAddress = mapBinding.address.getText().toString();
                if (!strAddress.isEmpty() && strAddress != null) {
                    getLocationFromAddress(strAddress);
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

                                // add marker
                                style.addImage("location",
                                        BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_location)));
                                SymbolManager symbolManager = new SymbolManager(mapBinding.mapView,mapboxMap,style);
                                symbolManager.setIconAllowOverlap(true);
                                symbolManager.setTextAllowOverlap(true);
                                SymbolOptions symbolOptions = new SymbolOptions()
                                        .withLatLng(latlng)
                                        .withIconImage("location")
                                        .withIconSize(1.3f);
                                symbolManager.create(symbolOptions);
                            }
                        });
                    }});
                 } else {
                    Toast.makeText(getActivity(), "Please enter your address", Toast.LENGTH_SHORT).show();
                };
            }
        });
        return view;
    }

    public void getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
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
