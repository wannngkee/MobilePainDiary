package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalisedmobilepaindiary.MainActivity;
import com.example.personalisedmobilepaindiary.adapter.RecyclerViewAdapter;
import com.example.personalisedmobilepaindiary.databinding.RecordFragmentBinding;
import com.example.personalisedmobilepaindiary.entity.PainRecord;
import com.example.personalisedmobilepaindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {
    private RecordFragmentBinding recordBinding;
    private PainRecordViewModel painRecordViewModel;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainActivity activity = (MainActivity) getActivity();
//    String email = activity.getEmail();
    String email = "aa@gmail.com";

    public RecordFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recordBinding = RecordFragmentBinding.inflate(inflater, container, false);
        View view = recordBinding.getRoot();
        adapter = new RecyclerViewAdapter();
        recordBinding.record.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recordBinding.record.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recordBinding.record.setLayoutManager(layoutManager);
        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.
                getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);
        painRecordViewModel.getAllPainRecords(email).observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(@Nullable final List<PainRecord> painRecords) {
                adapter.setData(painRecords);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recordBinding = null;
    }
}
