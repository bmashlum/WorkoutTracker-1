package com.example.brad.fitaid;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment {

    //private FragmentBListener listener;
    private ListView lvJournalEntries;
    private TextView tvDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ArrayList<String> exercisesRetrieved = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button btnRetrieve;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/workoutsChosen");

    public JournalFragment() {

    }


    /**
     * public interface FragmentBListener {
     * void onInputBSent(CharSequence input);
     * }
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        lvJournalEntries = v.findViewById(R.id.lvJournalEntries);

        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view_journal, exercisesRetrieved);

        lvJournalEntries.setAdapter(arrayAdapter);

        lvJournalEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = parent.getItemAtPosition(position).toString();

                if (lvJournalEntries.isItemChecked(position)) {
                    System.out.println("JOURNAL LIST POSITION" + pos);
                }
            }
        });

        tvDisplayDate = v.findViewById(R.id.tvDate);
        String date_n = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault()).format(new Date());
        tvDisplayDate.setText(date_n);
        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        requireActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                tvDisplayDate.setText(date);
            }
        };

        if (getArguments() == null || getArguments().get("exercisesClicked") == null) {
            Toast.makeText(getContext(), "No entries added for today yet!", Toast.LENGTH_SHORT).show();

        } else {
            JournalFragmentArgs args = JournalFragmentArgs.fromBundle(getArguments());
            String exercises = args.getExercisesClicked();

            ArrayList<String> myList = new ArrayList(Arrays.asList(exercises.substring(1, exercises.length() - 1).replaceAll("\\s", "").split(",")));
            System.out.println("TEST***" + myList);
            arrayAdapter.addAll(myList);
            arrayAdapter.notifyDataSetChanged();
        }



        /** ref.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                 String key = dataSnapshot.getKey();
                 exercisesRetrieved.add(key);

                 Set<String> listWithoutDuplicates = new LinkedHashSet<>(exercisesRetrieved);
                 exercisesRetrieved.clear();

                 exercisesRetrieved.addAll(listWithoutDuplicates);
                 arrayAdapter.notifyDataSetChanged();
                 /**Set<String> set = new HashSet<>(exercisesRetrieved);
                  exercisesRetrieved.clear();
                  exercisesRetrieved.addAll(set);
                  arrayAdapter.addAll(exercisesRetrieved);
                  arrayAdapter.notifyDataSetChanged();
                  dataSnapshot.getRef().removeValue();

                  System.out.println("SET LIST" + set );
             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {


             }
         });*/


        return v;
    }



    /**public void updateEditText(ArrayList<String> newList) {
     //editText.setText(newText);
     System.out.println("**GET NewList " + newList);
     System.out.println("JOURNAL LISTENER IS : " + listener);
     adapter = new ArrayAdapter((Context)listener, R.layout.list_view, newList);
     lvJournalEntries.setAdapter(adapter);
     adapter.notifyDataSetChanged();
     }*/

    /**@Override public void onAttach(Context context) {
    super.onAttach(context);
    System.out.println("**ATTACH CALL ");
    if (context instanceof FragmentBListener) {
    listener = (FragmentBListener) context;
    } else {
    throw new RuntimeException(context.toString()
    + " must implement FragmentBListener");
    }
    }

     @Override public void onDetach() {
     super.onDetach();
     System.out.println("ONDETACH JOURNAL");
     //listener = null;
     }*/

    /**protected void displayReceivedData(ArrayList<String> input)
     {
     adapter = new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.list_view, input);
     lvJournalEntries.setAdapter(adapter);
     adapter.notifyDataSetChanged();
     }*/
}
