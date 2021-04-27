package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;

public class DataFragment extends Fragment {
    private DataFragmentBinding dataBinding;
    public DataFragment(){}
    int painLevel;
    String location;
    String mood;
    String step;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();
//        dataBinding.emojiGood.setText(new String(Character.toChars(0x1F60A)));;
//        dataBinding.level.addOnChangeListener(new Slider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//                painLevel = Math.round(value);
//            }
//        });
        dataBinding.locationGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup group, RadioButton button) {
                location = button.getText().toString();
            }
        });


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
                step = dataBinding.currentStep.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBinding = null;
    }

}
