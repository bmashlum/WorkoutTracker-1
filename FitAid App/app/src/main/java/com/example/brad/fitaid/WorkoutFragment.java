package com.example.brad.fitaid;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {

    DatabaseHelper myDb;
    //private FragmentAListener listener;
    private ArrayList<String> exercisesClicked = new ArrayList<>();
    private ListView lvExercises;
    private Spinner spinWorkout;
    private ImageView imgTicker;
    private Button addToJournal;
    private String [] exercises,chest,back,abs,legs,biceps,triceps,shoulders;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference( "/workoutsChosen");

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**public interface FragmentAListener {
        void onInputASent(ArrayList<String> input);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_workout, container, false);

        exercises = getResources().getStringArray(R.array.exercises);
        chest = getResources().getStringArray(R.array.ex_chest);
        back = getResources().getStringArray(R.array.ex_back);
        abs = getResources().getStringArray(R.array.ex_abs);
        legs = getResources().getStringArray(R.array.ex_legs);
        biceps = getResources().getStringArray(R.array.ex_biceps);
        triceps = getResources().getStringArray(R.array.ex_triceps);
        shoulders = getResources().getStringArray(R.array.ex_shoulders);


        lvExercises = v.findViewById(R.id.lvExercises);
        lvExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final String date_n = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());

        imgTicker = v.findViewById(R.id.img_ticker);

        addToJournal = v.findViewById(R.id.btn_AddToJournal);
        addToJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> exerciseMap = new HashMap<String, String>();
                for (String exercise : exercisesClicked) {
                    exerciseMap.put(exercise, date_n);
                }
                ref.setValue(exerciseMap);

                System.out.println(exerciseMap.toString());

                Toast.makeText(getContext(),"You are adding " + exercisesClicked.toString() +" to your journal", Toast.LENGTH_SHORT).show();

                //System.out.println("****listenerWorkout:" + listener);
                //listener.onInputASent(exercisesClicked);
            }
        });

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
                final String pos = parentView.getItemAtPosition(position).toString();
                final ArrayAdapter<String> adapter;

                lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String pos = parent.getItemAtPosition(position).toString();



                        if(lvExercises.isItemChecked(position)) {
                            exercisesClicked.remove(pos);
                            Toast.makeText(getContext(),"You have selected " + pos, Toast.LENGTH_SHORT).show();
                            exercisesClicked.add(pos);
                            System.out.println("EXERCISES CLICKED: " + exercisesClicked);
                        }
                        else if(!lvExercises.isItemChecked(position)){
                            exercisesClicked.remove(pos);
                            System.out.println("EXERCISES CLICKED: " + exercisesClicked);
                        }
                    }
                });

                if (pos.compareToIgnoreCase("Chest") == 0) {
                    imgTicker.setImageResource(R.drawable.chest);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, chest);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else if (pos.compareToIgnoreCase("Shoulders") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, shoulders);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Abs") == 0) {
                    imgTicker.setImageResource(R.drawable.abs);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, abs);
                    System.out.println("****** getContext: " + getContext());
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Biceps") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, biceps);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Triceps") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, triceps);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Legs") == 0) {
                    imgTicker.setImageResource(R.drawable.legs);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, legs);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Back") == 0) {
                    imgTicker.setImageResource(R.drawable.back);
                    adapter = new ArrayAdapter<>(getContext(),R.layout.list_view, back);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // Inflate the layout for this fragment
        return v;

    }}

   /** public void updateEditText(ArrayList<String> newtext) {
        editText.setText(newText);
    }*/

    /**@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("**** onAttachWorkout");
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("ONDETACH WORKOUT");
        //listener = null;
    }
}*/
