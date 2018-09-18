package com.example.brad.fitaid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {
    /**
     * Spinners
     **/
    Spinner spinWorkout;

    /**
     * Adapters
     **/

    ImageView imgTicker;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_workout,container,false);
        imgTicker = v.findViewById(R.id.img_ticker);

        String[] workoutArray = getResources().getStringArray(R.array.workouts);
        List<String> workoutList = Arrays.asList(workoutArray);
        Collections.sort(workoutList); //Workout array will be sorted

        spinWorkout = v.findViewById(R.id.sp_workout);
        ArrayAdapter adaptSpinWorkout = new ArrayAdapter(getContext(), R.layout.spinner_item, workoutList);
        adaptSpinWorkout.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinWorkout.setAdapter(adaptSpinWorkout);
        spinWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinWorkout.getSelectedItem().toString().equals("Chest")) {
                    imgTicker.setImageResource(R.drawable.chest);
                } else if (spinWorkout.getSelectedItem().toString().equals("Shoulders")) {
                    imgTicker.setImageResource(R.drawable.arms);
                } else if (spinWorkout.getSelectedItem().toString().equals("Abs")) {
                    imgTicker.setImageResource(R.drawable.abs);
                } else if (spinWorkout.getSelectedItem().toString().equals("Biceps")) {
                    imgTicker.setImageResource(R.drawable.arms);
                } else if (spinWorkout.getSelectedItem().toString().equals("Triceps")) {
                    imgTicker.setImageResource(R.drawable.arms);
                } else if (spinWorkout.getSelectedItem().toString().equals("Legs")) {
                    imgTicker.setImageResource(R.drawable.legs);
                } else if (spinWorkout.getSelectedItem().toString().equals("Back")) {
                    imgTicker.setImageResource(R.drawable.back);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        // Inflate the layout for this fragment
        return v;

    }

}
