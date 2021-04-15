package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.databinding.DataFragmentBinding;

public class DataFragment extends Fragment {
    private DataFragmentBinding dataBinding;

    public DataFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBinding = null;
    }
}
