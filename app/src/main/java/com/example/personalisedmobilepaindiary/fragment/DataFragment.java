package com.example.personalisedmobilepaindiary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.personalisedmobilepaindiary.databinding.DataFragmentBinding;
import com.vanniktech.emoji.EmojiTextView;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

public class DataFragment extends Fragment {
    private DataFragmentBinding dataBinding;
    public DataFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();
//        dataBinding.emojiGood.setText(new String(Character.toChars(0x1F60A)));;

        dataBinding.locationGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup group, RadioButton button) {
                Toast.makeText(getActivity(),
                        button.getText() + " was clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

//    public void checkButton(View view){
//        int radioId = dataBinding.locationGroup.getCheckedRadioButtonId();
//        RadioButton radioButton = dataBinding.locationGroup.findViewById(radioId);
//        String location = radioButton.getText().toString();
//        Log.i("location",location);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBinding = null;
    }
}
