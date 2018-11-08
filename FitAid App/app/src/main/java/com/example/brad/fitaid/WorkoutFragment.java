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

import static android.widget.AdapterView.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {

    DatabaseHelper myDb;
    //private FragmentAListener listener;
    private ArrayList<String> exercisesClicked = new ArrayList<>();
    //private ArrayList<String> exercisesClickedLong = new ArrayList<>();
    private ListView lvExercises;
    private Spinner spinWorkout;
    private ImageView imgTicker;
    private Button addToJournal;
    private String[] exercises, chest, back, abs, legs, biceps, triceps, shoulders;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/workoutsChosen");
private VideoView videoView;
    PopupWindow popUp;
    ConstraintLayout lapop;
    ConstraintLayout.LayoutParams parms;
    ConstraintLayout p;
    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * public interface FragmentAListener {
     * void onInputASent(ArrayList<String> input);
     * }
     */

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

        final Uri uri = Uri.parse("android.resource://"+ getContext().getPackageName() +"/raw/trainwreck");

       final VideoView vv = v.findViewById(R.id.videoView);
//final MediaController mediaController = new MediaController(getContext());
        final String date_n = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());

        imgTicker = v.findViewById(R.id.img_ticker);

        addToJournal = v.findViewById(R.id.btn_AddToJournal);
        addToJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    WorkoutFragmentDirections.ActionNavWorkoutsToNavJournal action =
                            WorkoutFragmentDirections.actionNavWorkoutsToNavJournal(exercisesClicked.toString());
                    action.setExercisesClicked(exercisesClicked.toString());
                    Navigation.findNavController(v).navigate(action);


                HashMap<String, String> exerciseMap = new HashMap<String, String>();
                for (String exercise : exercisesClicked) {
                    exerciseMap.put(exercise, date_n);
                }
                ref.setValue(exerciseMap);

                System.out.println(exerciseMap.toString());

                Toast.makeText(getContext(), "You are adding " + exercisesClicked.toString() + " to your journal", Toast.LENGTH_SHORT).show();

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

                lvExercises.setOnItemLongClickListener(new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String pos = parent.getItemAtPosition(position).toString();

                        lapop = new ConstraintLayout(getContext());
                        popUp = new PopupWindow(50, 100);

                        if (lvExercises.isLongClickable()) {
                            //exercisesClickedLong.remove(pos);
                            Toast.makeText(getContext(), "You have selected " + pos, Toast.LENGTH_SHORT).show();
                            //exercisesClickedLong.add(pos);
                            System.out.println("EXERCISES CLICKED long: " + pos);


                        }
                        if (pos.equals("Squat")) {

                            System.out.println("Squat");
                        } else if (pos.equals("Leg Extension")) {
                            System.out.println("Leg Extension");
                        } else if (pos.equals("Leg Press")) {
                            System.out.println("Leg Press");
                        } else if (pos.equals("Seated Leg Curl")) {
                            System.out.println("Seated Leg Curl");
                        } else if (pos.equals("Lying Leg Curl")) {
                            System.out.println("Lying Leg Curl");
                        } else if (pos.equals("Calf Raise")) {
                            System.out.println("Calf Raise");
                        } else if (pos.equals("Seated Calf Raise")) {
                            System.out.println("Seated Calf Raise");
                        } else if (pos.equals("Shoulder Press")) {
                            System.out.println("Shoulder Press");
                        } else if (pos.equals("Dumbbell Fly")) {
                            System.out.println("Dumbbell Fly");
                        } else if (pos.equals("Dumbbell Rear Fly")) {
                            System.out.println("Dumbbell Rear Fly");
                        } else if (pos.equals("Tricep Pushdown")) {
                            System.out.println("Tricep Pushdown");
                        } else if (pos.equals("Rope Pushdown")) {
                            System.out.println("Rope Pushdown");
                        } else if (pos.equals("Bent-over Tricep Kickback")) {
                            System.out.println("Bent-over Tricep Kickback");
                        } else if (pos.equals("Standing Dumbell Curl")) {
                            System.out.println("Standing Dumbell Curl");
                        } else if (pos.equals("Standing Barbell Curl")) {
                            System.out.println("Standing Barbell Curl");
                        } else if (pos.equals("Seated Curl")) {
                            System.out.println("Seated Curl");
                        } else if (pos.equals("Incline Dumbbell Curl")) {
                            System.out.println("Incline Dumbbell Curl");
                        } else if (pos.equals("Bicep Face Pull")) {
                            System.out.println("Bicep Face Pull");
                        } else if (pos.equals("Pullup")) {
                            System.out.println("Pullup");
                        } else if (pos.equals("Lat Pulldown")) {
                            System.out.println("Lat Pulldown");
                        } else if (pos.equals("Barbell Row")) {
                            System.out.println("Barbell Row");
                        } else if (pos.equals("Dumbbell Row")) {
                            System.out.println("Dumbbell Row");
                        } else if (pos.equals("Seated Row")) {
                            System.out.println("Seated Row");
                        } else if (pos.equals("Inverse Pulldown")) {
                            System.out.println("Inverse Pulldown");
                        } else if (pos.equals("Barbell Bench Press")) {
                            System.out.println("Barbell Bench Press");
                        } else if (pos.equals("umbbell Bench Press")) {
                            System.out.println("umbbell Bench Press");
                        } else if (pos.equals("Incline Barbell Bench Press")) {
                            System.out.println("Incline Barbell Bench Press");
                        } else if (pos.equals("Incline Dumbbell Bench Press")) {
                            System.out.println("Incline Dumbbell Bench Press");
                        } else if (pos.equals("Chest Fly")) {
                            System.out.println("Chest Fly");
                        } else if (pos.equals("Pushup")) {
                            System.out.println("Pushup");
                        } else if (pos.equals("Crunch")) {

                          final PopupWindow pw = new PopupWindow(getActivity());
                           TextView tv = new TextView(getActivity());
                         final  VideoView vv = new VideoView(getActivity());

                           LayoutParams linearparams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                           vv.setLayoutParams(linearparams1);
                           //tv.setText("Testing");
                           final MediaController mediaController = new MediaController(getActivity());
                           mediaController.setAnchorView(vv);
                           vv.setMediaController(mediaController);
                           vv.setVideoURI(uri);
                           //vv.setVideoPath(uri.toString());
                           vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                        @Override
                                                        public void onPrepared(MediaPlayer mp) {

                                                            vv.requestFocus();
                                                            vv.start();

                                                        }
                                                    }
                           );

                           pw.setContentView(vv);


                           pw.setWidth(700);
                           pw.setHeight(800);
                            pw.setTouchable(true);
                            pw.setFocusable(true);
                            pw.setOutsideTouchable(true);

                           pw.showAtLocation(lvExercises, Gravity.BOTTOM, 40, 500);
                           pw.update();

                           System.out.println("Crunch");
                       }
                       else if (pos.equals("Reverse Crunch")){
                           System.out.println("Reverse Crunch");
                       }
                       else if (pos.equals("Russian Twist")){
                           System.out.println("Russian Twist");
                       }
                       else if (pos.equals("Hanging Leg Raise")){
                           System.out.println("Hanging Leg Raise");
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

//                lvExercises.setOnLongClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String pos = parent.getItemAtPosition(position).toString();
//
//                        if (lvExercises.isItemChecked(position)) {
//                            exercisesClicked.remove(pos);
//                            Toast.makeText(getContext(), "You have selected " + pos, Toast.LENGTH_SHORT).show();
//                            exercisesClicked.add(pos);
//                            System.out.println("EXERCISES CLICKED: " + exercisesClicked);
//                        } else if (!lvExercises.isItemChecked(position)) {
//                            exercisesClicked.remove(pos);
//                            System.out.println("EXERCISES CLICKED: " + exercisesClicked);
//                        }
//
//
//                        if (pos.compareToIgnoreCase("Chest") == 0) {
//                            imgTicker.setImageResource(R.drawable.chest);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, chest);
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//
//                        } else if (pos.compareToIgnoreCase("Shoulders") == 0) {
//                            imgTicker.setImageResource(R.drawable.arms);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, shoulders);
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//                        } else if (pos.compareToIgnoreCase("Abs") == 0) {
//                            imgTicker.setImageResource(R.drawable.abs);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, abs);
//                            System.out.println("****** getContext: " + getContext());
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//                        } else if (pos.compareToIgnoreCase("Biceps") == 0) {
//                            imgTicker.setImageResource(R.drawable.arms);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, biceps);
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//                        } else if (pos.compareToIgnoreCase("Triceps") == 0) {
//                            imgTicker.setImageResource(R.drawable.arms);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, triceps);
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//                        } else if (pos.compareToIgnoreCase("Legs") == 0) {
//                            imgTicker.setImageResource(R.drawable.legs);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, legs);
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//                        } else if (pos.compareToIgnoreCase("Back") == 0) {
//                            imgTicker.setImageResource(R.drawable.back);
//                            nAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, back);
//                            lvExercises.setAdapter(nAdapter);
//                            nAdapter.notifyDataSetChanged();
//                        }
//
//                    );
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
