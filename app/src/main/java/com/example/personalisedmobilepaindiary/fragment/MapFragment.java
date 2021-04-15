package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.databinding.MapFragmentBinding;

public class MapFragment extends Fragment {
    private MapFragmentBinding mapBinding;

    public MapFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = mapBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapBinding = null;
    }
}
