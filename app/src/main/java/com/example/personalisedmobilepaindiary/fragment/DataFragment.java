package com.example.personalisedmobilepaindiary.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.R;
import com.example.personalisedmobilepaindiary.databinding.DataFragmentBinding;
import com.google.android.material.slider.Slider;
import com.vanniktech.emoji.EmojiTextView;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataFragment extends Fragment {
    private DataFragmentBinding dataBinding;
    public DataFragment(){}
    int painLevel;
    String location = "";
    String mood = "";
    String step = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataBinding = DataFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();
//        dataBinding.emojiGood.setText(new String(Character.toChars(0x1F60A)));;

        List<String> list = new ArrayList<String>();
        list.add("");
        list.add("Back");
        list.add("Neck");
        list.add("Head");
        list.add("Knees");
        list.add("Heaps");
        list.add("Abdomen");
        list.add("Elbows");
        list.add("Shoulders");
        list.add("Shins");
        list.add("Jaw");
        list.add("Facial");

        final ArrayAdapter<String> spinnerAdapter = new
                ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, list);
        dataBinding.location.setAdapter(spinnerAdapter);

        dataBinding.mood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.very_low:
                        mood = "Very Low";
                        break;
                    case R.id.low:
                        mood = "Low";
                        break;
                    case R.id.average:
                        mood = "Average";
                        break;
                    case R.id.good:
                        mood = "Good";
                        break;
                    case R.id.very_good:
                        mood = "Very Good";
                        break;
                    default:
                        mood = null;
                        break;
                }
            }
        });
        dataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                painLevel = Math.round(dataBinding.level.getValue());
                location = dataBinding.location.getSelectedItem().toString();
                step = dataBinding.currentStep.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
                if (mood.isEmpty() || step.isEmpty() || location.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter all the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    dataBinding.level.setEnabled(false);
                    dataBinding.location.setEnabled(false);
                    for (int i = 0; i < dataBinding.mood.getChildCount(); i++) {
                        dataBinding.mood.getChildAt(i).setEnabled(false);
                    }
                    dataBinding.stepGoal.setEnabled(false);
                    dataBinding.currentStep.setEnabled(false);
                    Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dataBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dataBinding.level.setEnabled(true);
                    dataBinding.location.setEnabled(true);
                    for (int i = 0; i < dataBinding.mood.getChildCount(); i++) {
                        dataBinding.mood.getChildAt(i).setEnabled(true);
                    }
                    dataBinding.stepGoal.setEnabled(true);
                    dataBinding.currentStep.setEnabled(true);
                }
            }
        );

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBinding = null;
    }

}
