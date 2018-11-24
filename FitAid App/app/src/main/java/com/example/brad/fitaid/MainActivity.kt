package com.example.brad.fitaid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation

import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.ui.NavigationUI

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

import java.util.Calendar

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    internal var user: FirebaseUser? = null
    private var fragmentA: WorkoutFragment? = null
    private var fragmentB: JournalFragment? = null
    internal val database = FirebaseDatabase.getInstance()
    internal var ref = database.getReference("server/workoutsChosen")

    internal var btnAddSet: Button? = null
    internal var tvSet2: TextView? = null
    internal var imgNavBar: ImageView? = null
    internal var imgNavBarColors: ImageView? = null

    override fun onResume() {
        super.onResume()
        ref.removeValue()
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = Navigation.findNavController(this, R.id.my_nav_host_frag)
        setupBottomNavMenu(navController)

        user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            startActivity(Intent(this@MainActivity, SignInActivity::class.java))
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefs.getBoolean("firstTime", false)) {
            val alarmIntent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)
            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, 11)
            calendar.set(Calendar.MINUTE, 20)
            calendar.set(Calendar.SECOND, 1)
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

            val editor = prefs.edit()
            editor.putBoolean("firstTime", true)
            editor.apply()
        }


        fragmentA = WorkoutFragment()
        fragmentB = JournalFragment()


        val window = window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.statusBar)
        } else {
            window.statusBarColor = resources.getColor(R.color.statusBar)
        }

        /** FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         * fab.setOnClickListener(new View.OnClickListener() {
         * @Override public void onClick(View view) {
         * Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         * .setAction("Action", null).show();
         * }
         * }); */


    }

    inner class AlarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // show toast
            Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        navigationBottom?.let {
            NavigationUI.setupWithNavController(it, navController)
        }


    }


//    fun signout(view: View) {
//        FirebaseAuth.getInstance().signOut()
//        finish()
//        startActivity(Intent(this@MainActivity, SignInActivity::class.java))
//
//    }


    /**
     * Show a string on the screen via Toast.
     *
     * @param msg String
     * @return void
     */

    fun toast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    } // end toast

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }


    fun settingsClicked(target: View) {
        imgNavBar!!.setImageResource(R.drawable.bottom_menu_blue)
    }



    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }

    companion object {
        private val DEBUGTAG = "JWP"
    }


}
