package com.example.personalisedmobilepaindiary.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalisedmobilepaindiary.R;
import com.example.personalisedmobilepaindiary.databinding.RvLayoutBinding;
import com.example.personalisedmobilepaindiary.entity.PainRecord;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.System.getString;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {
    private List<PainRecord> records = new ArrayList<>();

    public void setData(List<PainRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutBinding binding = RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder (@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position){
        PainRecord record = records.get(position);
        viewHolder.binding.rvDate.setText(record.date);
        viewHolder.binding.rvEmail.setText(record.email);
        viewHolder.binding.rvTemp.setText(record.temp + "°C");
        viewHolder.binding.rvHumidity.setText(record.humidity+"%");
        viewHolder.binding.rvPressure.setText(record.pressure+"hPa");
        viewHolder.binding.rvLevel.setText("Pain Level " + record.level);
        viewHolder.binding.rvLocation.setText(record.location);
        viewHolder.binding.rvMood.setText(record.mood + " Mood");
        viewHolder.binding.rvStep.setText(record.step + " Steps");
    }
    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}