package com.example.brad.fitaid;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.R.color.holo_orange_light;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lvExercises;
    Spinner spinWorkout;
    List exercise_list;
    ArrayAdapter exerArrayAdapter;
    ImageView imgTicker;
    boolean[] chestCheck,backCheck,absCheck,legsCheck,bicepCheck,tricepCheck,shoulderCheck;

    public WorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_workout, container, false);


        final String [] chest;
        final String [] back;
        final String [] abs;
        final String [] legs;
        final String [] biceps;
        final String [] triceps;
        final String [] shoulders;

        chest = getResources().getStringArray(R.array.ex_chest);
        back = getResources().getStringArray(R.array.ex_back);
        abs = getResources().getStringArray(R.array.ex_abs);
        legs = getResources().getStringArray(R.array.ex_legs);
        biceps = getResources().getStringArray(R.array.ex_biceps);
        triceps = getResources().getStringArray(R.array.ex_triceps);
        shoulders = getResources().getStringArray(R.array.ex_shoulders);

        chestCheck = new boolean [chest.length];

        lvExercises = v.findViewById(R.id.lvExercises);
        exercise_list  = new ArrayList();


        exerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                exercise_list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };


        lvExercises.setAdapter(exerArrayAdapter);
        lvExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //lvExercises.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                lvExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                lvExercises.setItemsCanFocus(false);
                exerArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                String pos = parentView.getItemAtPosition(position).toString();

                if (pos.compareToIgnoreCase("Chest") == 0) {
                    imgTicker.setImageResource(R.drawable.chest);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, chest);
                    lvExercises.setAdapter(adapter);
                } else if (pos.compareToIgnoreCase("Shoulders") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, shoulders);
                    lvExercises.setAdapter(adapter);
                } else if (pos.compareToIgnoreCase("Abs") == 0) {
                    imgTicker.setImageResource(R.drawable.abs);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, abs);
                    lvExercises.setAdapter(adapter);
                } else if (pos.compareToIgnoreCase("Biceps") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, biceps);
                    lvExercises.setAdapter(adapter);
                } else if (pos.compareToIgnoreCase("Triceps") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, triceps);
                    lvExercises.setAdapter(adapter);
                } else if (pos.compareToIgnoreCase("Legs") == 0) {
                    imgTicker.setImageResource(R.drawable.legs);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, legs);
                    lvExercises.setAdapter(adapter);
                } else if (pos.compareToIgnoreCase("Back") == 0) {
                    imgTicker.setImageResource(R.drawable.back);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, back);
                    lvExercises.setAdapter(adapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
