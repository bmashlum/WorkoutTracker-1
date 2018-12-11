package com.example.brad.fitaid;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.widget.AdapterView.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {

    DatabaseHelper myDb;
    private JournalViewModel viewModel = new JournalViewModel();
    private ArrayList<String> exercisesClicked = new ArrayList<>();
    //private ArrayList<String> exercisesClickedLong = new ArrayList<>();
    private ListView lvExercises;
    private Spinner spinWorkout;
    private ImageView imgTicker;
    private Button addToJournal;
    private String[] exercises, chest, back, abs, legs, biceps, triceps, shoulders;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private VideoView videoView;
    PopupWindow popUp;
    ConstraintLayout lapop;
    ConstraintLayout.LayoutParams parms;
    ConstraintLayout p;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_workout, container, false);

        exercises = getResources().getStringArray(R.array.exercises);
        chest = getResources().getStringArray(R.array.ex_chest);
        back = getResources().getStringArray(R.array.ex_back);
        abs = getResources().getStringArray(R.array.ex_abs);
        legs = getResources().getStringArray(R.array.ex_legs);
        biceps = getResources().getStringArray(R.array.ex_biceps);
        triceps = getResources().getStringArray(R.array.ex_triceps);
        shoulders = getResources().getStringArray(R.array.ex_shoulders);

        videoView = v.findViewById(R.id.videoView);
        lvExercises = v.findViewById(R.id.lvExercises);
        lvExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//hash map for videos by exercises
        final HashMap<String, Uri> uri = new HashMap<>();
        uri.put("Squat", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/squat"));
        uri.put("Leg Extension", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwhistle"));
        uri.put("Leg Press", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Seated Leg Curl", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Lying Leg Curl", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Calf Raise", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Seated Calf Raise", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Shoulder Press", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/shoulderpress"));
        uri.put("Dumbbell Fly", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Dumbbell Rear Fly", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Tricep Pushdown", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Rope Pushdown", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Bent-over Tricep Kickback", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Standing Dumbell Curl", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Standing Barbell Curl", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Seated Curl", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Incline Dumbbell Curl", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Bicep Face Pull", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Pullup", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/pullup"));
        uri.put("Lat Pulldown", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Barbell Row", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Dumbbell Row", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Seated Row", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Inverse Pulldown", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Barbell Bench Press", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Dumbbell Bench Press", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Incline Barbell Bench Press", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Incline Dumbbell Bench Press", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Chest Fly", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Pushup", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/pushup"));
        uri.put("Crunch", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Reverse Crunch", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Russian Twist", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));
        uri.put("Hanging Leg Raise", Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/trainwreck"));

        final VideoView vv = v.findViewById(R.id.videoView);
//final MediaController mediaController = new MediaController(getContext());
        final String date_n = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());

        imgTicker = v.findViewById(R.id.img_ticker);

        addToJournal = v.findViewById(R.id.btn_AddToJournal);
        addToJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.setExercises(exercisesClicked);
                System.out.println("TESTING VIEW" + exercisesClicked + " VIEW MODEL " + viewModel);

                //Navigate to Journal Frag when button is clicked
                WorkoutFragmentDirections.ActionNavWorkoutsToNavJournal actionNavWorkoutsToNavJournal =
                        WorkoutFragmentDirections.actionNavWorkoutsToNavJournal();
                Navigation.findNavController(v).navigate(actionNavWorkoutsToNavJournal);

                //USING NAV ACTION SAFE ARGS TO TRANSFER DATA TO JOURNAL
                /*
                 WorkoutFragmentDirections.ActionNavWorkoutsToNavJournal action =
                 WorkoutFragmentDirections.actionNavWorkoutsToNavJournal(exercisesClicked.toString());
                 action.setExercisesClicked(exercisesClicked.toString());
                 Navigation.findNavController(v).navigate(action);
                */

                //PUSH WORKOUTS TO FIREBASE
                /**
                 HashMap<String, String> exerciseMap = new HashMap<String, String>();
                 for (String exercise : exercisesClicked) {
                 exerciseMap.put(exercise, date_n);
                 }
                 ref.setValue(exerciseMap);

                 System.out.println(exerciseMap.toString());
                 */

                Toast.makeText(getContext(), "You are adding " + exercisesClicked.toString() + " to your journal", Toast.LENGTH_SHORT).show();

            }
        });

        String[] workoutArray = getResources().getStringArray(R.array.workouts);
        List<String> workoutList = Arrays.asList(workoutArray);
        Collections.sort(workoutList); //Workout array will be sorted

        spinWorkout = v.findViewById(R.id.sp_workout);
        ArrayAdapter adaptSpinWorkout = new ArrayAdapter(getContext(), R.layout.spinner_item, workoutList);
        adaptSpinWorkout.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinWorkout.setAdapter(adaptSpinWorkout);
        spinWorkout.setOnItemSelectedListener(new OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final String pos = parentView.getItemAtPosition(position).toString();
                final ArrayAdapter<String> adapter;
                // final ArrayAdapter<String> nAdapter = adapter;


                lvExercises.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String pos = parent.getItemAtPosition(position).toString();


                        if (lvExercises.isItemChecked(position)) {
                            exercisesClicked.remove(pos);
                            Toast.makeText(getContext(), "You have selected " + pos, Toast.LENGTH_SHORT).show();
                            exercisesClicked.add(pos);
                            System.out.println("EXERCISES CLICKED: " + exercisesClicked);
                        } else if (!lvExercises.isItemChecked(position)) {
                            exercisesClicked.remove(pos);
                            System.out.println("EXERCISES CLICKED: " + exercisesClicked);
                        }

                    }
                });
//long press for popUp video
                lvExercises.setOnItemLongClickListener(new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String pos = parent.getItemAtPosition(position).toString();
                        if (lvExercises.isLongClickable()) {
                            Toast.makeText(getContext(), "You have selected " + pos, Toast.LENGTH_SHORT).show();
                            System.out.println("EXERCISES CLICKED long: " + pos);
                        }

                        Set<String> keys = uri.keySet();
                        for (String key : keys) {
                            //System.out.println( key );

                            if (pos.equals(key)) {
                                final PopupWindow pw = new PopupWindow(getActivity());
                                final VideoView vv = new VideoView(getActivity());
                                LayoutParams linearparams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                                vv.setLayoutParams(linearparams1);
                                final MediaController mediaController = new MediaController(getActivity());
                                mediaController.setAnchorView(vv);
                                vv.setMediaController(mediaController);
                                Uri myUri = uri.get(key);
                                vv.setVideoURI(myUri);
                                vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                             @Override
                                                             public void onPrepared(MediaPlayer mp) {
                                                                 vv.requestFocus();
                                                                 vv.start();
                                                             }
                                                         }
                                );
                                pw.setContentView(vv);
                                pw.setWidth(600);
                                pw.setHeight(1000);
                                pw.setTouchable(true);
                                pw.setFocusable(true);
                                pw.setOutsideTouchable(true);
                                pw.showAtLocation(lvExercises, Gravity.BOTTOM, 20, 1000);
                                pw.update();
                                System.out.println(key + " , " + uri.get(key));
                            }
                        }


                        return false;
                    }
                });


                if (pos.compareToIgnoreCase("Chest") == 0) {
                    imgTicker.setImageResource(R.drawable.chest);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, chest);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else if (pos.compareToIgnoreCase("Shoulders") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, shoulders);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Abs") == 0) {
                    imgTicker.setImageResource(R.drawable.abs);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, abs);
                    System.out.println("****** getContext: " + getContext());
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Biceps") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, biceps);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Triceps") == 0) {
                    imgTicker.setImageResource(R.drawable.arms);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, triceps);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Legs") == 0) {
                    imgTicker.setImageResource(R.drawable.legs);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, legs);
                    lvExercises.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (pos.compareToIgnoreCase("Back") == 0) {
                    imgTicker.setImageResource(R.drawable.back);
                    adapter = new ArrayAdapter<>(getContext(), R.layout.list_view, back);
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

    }

    // ViewModel LiveData Observer Implementation for exercises in lists
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("ONACTIVITY CREATED");

        viewModel = ViewModelProviders.of(getActivity()).get(JournalViewModel.class);
        viewModel.getExercises().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                System.out.println("ONCHANGE CREATED");
                //exercisesClicked.addAll(strings);
            }
        });

    }
}
