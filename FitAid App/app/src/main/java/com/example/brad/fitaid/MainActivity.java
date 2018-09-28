package com.example.brad.fitaid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.brad.fitaid.R.id.mainLayout;

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


    /**
     * Adapters
     **/


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProfileFragment profileFragment = new ProfileFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, profileFragment, profileFragment.getTag())
                .commit();


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

        ProfileFragment profileFragment = new ProfileFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, profileFragment, profileFragment.getTag())
                .commit();
    }

    public void journalClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_yellow);

        JournalFragment journalFragment = new JournalFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, journalFragment, journalFragment.getTag())
                .commit();
    }

    public void workoutsClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_green);

        WorkoutFragment workoutFragment = new WorkoutFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, workoutFragment, workoutFragment.getTag())
                .commit();

    }


    public void settingsClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_blue);
    }
}
