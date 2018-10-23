package com.example.brad.fitaid;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


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

   /** public interface FragmentBListener {
        void onInputBSent(CharSequence input);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        btnRetrieve = v.findViewById(R.id.btn_Retrieve);

        lvJournalEntries = v.findViewById(R.id.lvJournalEntries);

        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view, exercisesRetrieved);

        lvJournalEntries.setAdapter(arrayAdapter);
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        ref.addChildEventListener(new ChildEventListener() {
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

                System.out.println("SET LIST" + set );*/
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
        });


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

    /**@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("**ATTACH CALL ");
        if (context instanceof FragmentBListener) {
            listener = (FragmentBListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentBListener");
        }
    }

    @Override
    public void onDetach() {
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
