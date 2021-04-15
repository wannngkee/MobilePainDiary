package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.databinding.RecordFragmentBinding;

public class RecordFragment extends Fragment {
    private RecordFragmentBinding recordBinding;

    public RecordFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recordBinding = RecordFragmentBinding.inflate(inflater, container, false);
        View view = recordBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recordBinding = null;
    }
}
