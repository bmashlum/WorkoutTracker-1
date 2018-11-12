package com.example.brad.fitaid;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    //private String currentUser = SigninActivity.userId;

    HashMap<String,Double> statMap = new HashMap<>();
    final String email= SignInActivity.userId.replaceAll("\\.", "");
    final String date_n = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());
    private ArrayList<Double> inputText = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/" + email + "/" + date_n);
    DatabaseReference ref2 = database.getReference("/" + email);

    TextInputEditText etWeight, etBodyFat, etAge, etChest, etLeftArm, etRightArm, etWaist, etLeftLeg, etRightLeg, etLeftCalf, etRightCalf;
    double weight, bodyFat, age, chest, leftArm, rightArm, waist, leftLeg, rightLeg, leftCalf, rightCalf;
    Button btnSave;
    Button btnGraph;
    private  GraphView graphView;
    private LinkedHashMap<String, Double> exerciseMap = new LinkedHashMap<String, Double>();

    private ArrayList<Double> x_value = new ArrayList<>();
    private ArrayList<Double> y_value = new ArrayList<>();
    private double x = 0.0;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSave = v.findViewById(R.id.btn_Save);
        btnGraph = v.findViewById(R.id.btn_graph);

        etWeight = v.findViewById(R.id.inputWeight);
        etBodyFat = v.findViewById(R.id.inputFat);
        etAge = v.findViewById(R.id.inputAge);
        etChest = v.findViewById(R.id.inputChest);
        etLeftArm = v.findViewById(R.id.inputArmL);
        etRightArm = v.findViewById(R.id.inputArmR);
        etWaist = v.findViewById(R.id.inputWaist);
        etLeftLeg = v.findViewById(R.id.inputLegL);
        etRightLeg = v.findViewById(R.id.inputLegR);
        etLeftCalf = v.findViewById(R.id.inputCalfL);
        etRightCalf = v.findViewById(R.id.inputCalfR);








        Query lastQuery = ref2.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    statMap = (HashMap) data.getValue();
                    etWeight.setText(String.valueOf(statMap.get("Weight")));
                    etBodyFat.setText(String.valueOf(statMap.get("Body Fat")));
                    etAge.setText(String.valueOf(statMap.get("Age")));
                    etChest.setText(String.valueOf(statMap.get("Chest")));
                    etLeftArm.setText(String.valueOf(statMap.get("Left Arm")));
                    etRightArm.setText(String.valueOf(statMap.get("Right Arm")));
                    etWaist.setText(String.valueOf(statMap.get("Waist")));
                    etLeftCalf.setText(String.valueOf(statMap.get("Left Calf")));
                    etRightCalf.setText(String.valueOf(statMap.get("Right Calf")));
                    etLeftLeg.setText(String.valueOf(statMap.get("Left Leg")));
                    etRightLeg.setText(String.valueOf(statMap.get("Right Leg")));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.clear();
                try {
                    weight = Double.parseDouble(etWeight.getText().toString());
                    bodyFat = Double.parseDouble(etBodyFat.getText().toString());
                    age = Double.parseDouble(etAge.getText().toString());
                    chest = Double.parseDouble(etChest.getText().toString());
                    leftArm = Double.parseDouble(etLeftArm.getText().toString());
                    rightArm = Double.parseDouble(etRightArm.getText().toString());
                    waist = Double.parseDouble(etWaist.getText().toString());
                    leftLeg = Double.parseDouble(etLeftLeg.getText().toString());
                    rightLeg = Double.parseDouble(etRightLeg.getText().toString());
                    leftCalf = Double.parseDouble(etLeftCalf.getText().toString());
                    rightCalf = Double.parseDouble(etRightCalf.getText().toString());
                } catch (NumberFormatException e) {
                    System.out.println("EMPTY STRING");
                }


                inputText.add(weight);
                inputText.add(bodyFat);
                inputText.add(age);
                inputText.add(chest);
                inputText.add(leftArm);
                inputText.add(rightArm);
                inputText.add(waist);
                inputText.add(leftLeg);
                inputText.add(rightLeg);
                inputText.add(leftCalf);
                inputText.add(rightCalf);









                // LinkedHashMap<String, Double> exerciseMap = new LinkedHashMap<String, Double>();

                exerciseMap.put("Weight", inputText.get(0));
                exerciseMap.put("Body Fat", inputText.get(1));
                exerciseMap.put("Age", inputText.get(2));
                exerciseMap.put("Chest", inputText.get(3));
                exerciseMap.put("Left Arm", inputText.get(4));
                exerciseMap.put("Right Arm", inputText.get(5));
                exerciseMap.put("Waist", inputText.get(6));
                exerciseMap.put("Left Leg", inputText.get(7));
                exerciseMap.put("Right Leg", inputText.get(8));
                exerciseMap.put("Left Calf", inputText.get(9));
                exerciseMap.put("Right Calf", inputText.get(10));

                ref.setValue(exerciseMap);

                System.out.println("TESTINGG " + exerciseMap);

            }
        });

        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , Graph_Activity.class));
            }
        });


        return v;
    }


}