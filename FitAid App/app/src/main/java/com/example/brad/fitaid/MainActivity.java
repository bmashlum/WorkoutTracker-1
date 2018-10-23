package com.example.brad.fitaid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    FirebaseUser user;
    private static final String DEBUGTAG = "JWP";
    private WorkoutFragment fragmentA;
    private JournalFragment fragmentB;
    private ProfileFragment profileFragment;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/workoutsChosen");

    Button btnProfile, btnJournal, btnWorkouts, btnSettings;

    ImageView imgNavBar, imgNavBarColors;

    public MainActivity() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ref.removeValue();
    }

    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // show toast
            Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show();
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity( new Intent( MainActivity.this,SigninActivity2.class ) );
        }
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            calendar.set(Calendar.MINUTE, 20);
            calendar.set(Calendar.SECOND, 1);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        profileFragment = new ProfileFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, profileFragment, profileFragment.getTag())
                .commit();

        fragmentA = new WorkoutFragment();
        fragmentB = new JournalFragment();


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

    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity( new Intent( MainActivity.this, SigninActivity2.class ) );

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

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, profileFragment, profileFragment.getTag())
                .commit();
    }

    public void journalClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_yellow);
        System.out.println("JOURNAL CLICKED");
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, fragmentB, fragmentB.getTag())
                .commit();

    }

    public void workoutsClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_green);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout, fragmentA, fragmentA.getTag())
                .commit();

    }


    public void settingsClicked(View target) {
        imgNavBar.setImageResource(R.drawable.bottom_menu_blue);
    }


    /**@Override
    public void onInputASent(ArrayList<String> input) {
        System.out.println("***** journalFragment: " + journalFragment);
        journalFragment.displayReceivedData(input);
    }
    */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**@Override
    public void onInputASent(ArrayList<String> input) {
        System.out.println("**INPUT " + input);
        System.out.println("ISFRAGNULL" + fragmentB.getContext());
        fragmentB.updateEditText(input);
    }

    @Override
    public void onInputBSent(CharSequence input) {

    }*/




}
