package com.example.brad.fitaid;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUGTAG = "JWP";

    /**
     * Buttons
     **/
    Button btnProfile, btnJournal, btnWorkouts, btnSettings;
    /**
     * ImageViews
     **/
    ImageView imgNavBar, imgNavBarColors;
    /**
     * Spinners
     **/
    Spinner spinWorkout;

    /**
     * Adapters
     **/

    ImageView imgTicker;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.statusBar));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.statusBar));
        }

        /** FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/

        btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setVisibility(View.VISIBLE);
        btnProfile.setBackgroundColor(Color.TRANSPARENT);

        btnJournal = findViewById(R.id.btnJournal);
        btnJournal.setVisibility(View.VISIBLE);
        btnJournal.setBackgroundColor(Color.TRANSPARENT);

        btnWorkouts = findViewById(R.id.btnWorkouts);
        btnWorkouts.setVisibility(View.VISIBLE);
        btnWorkouts.setBackgroundColor(Color.TRANSPARENT);

        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setVisibility(View.VISIBLE);
        btnSettings.setBackgroundColor(Color.TRANSPARENT);

        imgNavBar = findViewById(R.id.img_TopNav);



        imgTicker = findViewById(R.id.img_ticker);

        String[] workoutArray = getResources().getStringArray(R.array.workouts);
        List<String> workoutList = Arrays.asList(workoutArray);
        Collections.sort(workoutList); //Workout array will be sorted

        spinWorkout = findViewById(R.id.sp_workout);
        ArrayAdapter adaptSpinWorkout = new ArrayAdapter(this, R.layout.spinner_item, workoutList);
        adaptSpinWorkout.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinWorkout.setAdapter(adaptSpinWorkout);
        spinWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinWorkout.getSelectedItem().toString().equals("Chest")) {
                    imgTicker.setImageResource(R.drawable.chest);
                } else if (spinWorkout.getSelectedItem().toString().equals("Shoulders")) {
                    imgTicker.setImageResource(R.drawable.shoulders);
                } else if (spinWorkout.getSelectedItem().toString().equals("Abs")) {
                    imgTicker.setImageResource(R.drawable.abs);
                } else if (spinWorkout.getSelectedItem().toString().equals("Biceps")) {
                    imgTicker.setImageResource(R.drawable.shoulders);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    /**
     * Show a string on the screen via Toast.
     *
     * @param msg String
     * @return void
     */

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    } // end toast

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void profileClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu);
    }

    public void journalClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_yellow);
    }

    public void workoutsClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_green);
    }

    public void settingsClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_blue);
    }
}
