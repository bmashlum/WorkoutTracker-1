package com.example.brad.fitaid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static android.app.PendingIntent.getActivity;

public class Graph_Activity extends AppCompatActivity {

    final String email= SignInActivity.userId.replaceAll("\\.", "");
    final String date_n = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    // DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child("date");
    DatabaseReference ref = database.getReference("/" + email);

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    final ArrayList<Long> weights = new ArrayList<>();
    private HashMap<Integer, Long> weights_map = new HashMap<>();
    final ArrayList<String> dates_forX_values = new ArrayList<>();
    private int count=0;
    ImageButton backHome;
    ImageButton toBodyFat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);
        backHome =  findViewById(R.id.backToHome);
        toBodyFat = findViewById(R.id.toBodyFat_btn);

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectWeight((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });




        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);



        // graph.getGridLabelRenderer().setNumHorizontalLabels(4);

        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(250);
        viewport.setMinX(20);
        viewport.setMaxX(24);
        viewport.setScrollable(true);




        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        toBodyFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Graph_Activity.this, Graph_BodyFat.class));
            }
        });

    }

    private void collectWeight(Map<String,Object> dates) {

//      final ArrayList<Long> weights = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : dates.entrySet()){

            //Get user map
            Map singleDate = (Map) entry.getValue();
            //Get phone field and append to list
            weights.add((Long) singleDate.get("Weight"));
            weights_map.put(lastX++, (Long) singleDate.get("Weight"));
            dates_forX_values.add(entry.getKey().toString());
        }

        System.out.println("lets see:  "+ weights.toString() + " " + weights.size() + " " + weights_map + " " + dates_forX_values) ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i <= weights.size(); i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    // add random data to graph
    private void addEntry() {

        // here, we choose to display max 10 points on the viewport and we scroll to end
        for (int i=0;i<weights_map.size();i++) {
            series.appendData(new DataPoint(lastX++ , weights_map.get(i)), true, weights_map.size());

        }
        System.out.println(ref);
    }




}