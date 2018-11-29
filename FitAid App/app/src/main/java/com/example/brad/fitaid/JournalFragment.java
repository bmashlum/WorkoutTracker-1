package com.example.brad.fitaid;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment {

    static String pickedDate = "";
    static String pos;
    static String exercises;
    private ListView lvJournalEntries;
    private TextView tvDisplayDate;
    private TextView rep1, rep2, rep3, rep4, weight1, weight2, weight3, weight4, sets, reps, weight, sets1, sets2, sets3, sets4;
    ArrayList<String> pickedDateExercises;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    HashMap<String, Long> setsMap = new HashMap<>();
    ArrayList<String> exercisesRetrieved = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    final String email = SignInActivity.userId.replaceAll("\\.", "");
    final String dateOpened = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference refdb3 = database.getReference("/" + email + "/" + "Journal" + "/" + dateOpened.replaceAll("\\/", ","));


    private JournalViewModel viewModel;

    public JournalFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        sets = v.findViewById(R.id.tvSets);
        sets1 = v.findViewById(R.id.tvSets1);
        sets2 = v.findViewById(R.id.tvSets2);
        sets3 = v.findViewById(R.id.tvSets3);
        sets4 = v.findViewById(R.id.tvSets4);

        reps = v.findViewById(R.id.tvReps);
        weight = v.findViewById(R.id.tvWeight);

        rep1 = v.findViewById(R.id.tvRep1);
        rep2 = v.findViewById(R.id.tvRep2);
        rep3 = v.findViewById(R.id.tvRep3);
        rep4 = v.findViewById(R.id.tvRep4);
        weight1 = v.findViewById(R.id.tvWeight1);
        weight2 = v.findViewById(R.id.tvWeight2);
        weight3 = v.findViewById(R.id.tvWeight3);
        weight4 = v.findViewById(R.id.tvWeight4);

        /**hideTextViews();
        new ShowcaseView.Builder(getActivity())
                .setTarget(new ActionViewTarget(getActivity(), ActionViewTarget.Type.SPINNER))
                .setContentTitle("ShowcaseView")
                .setContentText("This is highlighting the Home button")
                .hideOnTouchOutside()
                .build();*/

        lvJournalEntries = v.findViewById(R.id.lvJournalEntries);

        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_view_journal, exercisesRetrieved);

        lvJournalEntries.setAdapter(arrayAdapter);

        lvJournalEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = parent.getItemAtPosition(position).toString();
                System.out.println("Item selected: " + pos);
                final DatabaseReference refdbTvDisplayDate = database.getReference("/" + email + "/" + "Journal" + "/" + tvDisplayDate.getText().toString().replaceAll("\\/", ","));

                Query lastQuery = refdbTvDisplayDate.orderByKey().equalTo(pos);
                lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            setsMap = (HashMap) data.getValue();

                            //for(DataSnapshot exerciseSnapshot : .child(""))
                            System.out.println("DATA VALUE " + setsMap);
                            rep1.setText(String.valueOf(setsMap.get("Set1 Reps")));
                            weight1.setText(String.valueOf(setsMap.get("Set1 Weight")));
                            rep2.setText(String.valueOf(setsMap.get("Set2 Reps")));
                            weight2.setText(String.valueOf(setsMap.get("Set2 Weight")));
                            rep3.setText(String.valueOf(setsMap.get("Set3 Reps")));
                            weight3.setText(String.valueOf(setsMap.get("Set3 Weight")));
                            rep4.setText(String.valueOf(setsMap.get("Set4 Reps")));
                            weight4.setText(String.valueOf(setsMap.get("Set4 Weight")));

                            showTextViews();

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("DB ERROR");
                    }


                });


            }
        });


        lvJournalEntries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = adapterView.getItemAtPosition(i).toString();
                System.out.println("Item selected: " + pos);
                openDialog();
                return true;
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
                pickedDate = date;
                tvDisplayDate.setText(date);
                arrayAdapter.clear();
                if (exercisesRetrieved.isEmpty()) {
                    hideTextViews();
                }
                final DatabaseReference refdb2 = database.getReference("/" + email + "/" + "Journal" + "/" + pickedDate.replaceAll("\\/", ","));

                refdb2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            pickedDateExercises = new ArrayList<>();
                            pickedDateExercises.add(data.getKey());
                            arrayAdapter.addAll(pickedDateExercises);
                            arrayAdapter.notifyDataSetChanged();
                            System.out.println("PICKED DATES EXERCISES" + pickedDateExercises);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };

        return v;
    }

    // ViewModel LiveData Observer Implementation for exercises in lists
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideTextViews();

        refdb3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    pickedDateExercises = new ArrayList<>();
                    pickedDateExercises.add(data.getKey());
                    arrayAdapter.addAll(pickedDateExercises);
                    arrayAdapter.notifyDataSetChanged();

                    System.out.println("PICKED DATES EXERCISES" + pickedDateExercises);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("ONACTIVITY CREATED");

        viewModel = ViewModelProviders.of(getActivity()).get(JournalViewModel.class);
        viewModel.getExercises().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                System.out.println("ONCHANGE CREATED");

                exercisesRetrieved.addAll(strings);

            }
        });

    }

    public void openDialog() {
        SetsRepsWeightDialog dialog = new SetsRepsWeightDialog();
        dialog.show(getChildFragmentManager(), "setsrepsweights dialog");
    }

    public void showTextViews() {
        sets.setVisibility(View.VISIBLE);
        sets1.setVisibility(View.VISIBLE);
        sets2.setVisibility(View.VISIBLE);
        sets3.setVisibility(View.VISIBLE);
        sets4.setVisibility(View.VISIBLE);
        reps.setVisibility(View.VISIBLE);
        weight.setVisibility(View.VISIBLE);

        rep1.setVisibility(View.VISIBLE);
        rep2.setVisibility(View.VISIBLE);
        rep3.setVisibility(View.VISIBLE);
        rep4.setVisibility(View.VISIBLE);
        weight1.setVisibility(View.VISIBLE);
        weight2.setVisibility(View.VISIBLE);
        weight3.setVisibility(View.VISIBLE);
        weight4.setVisibility(View.VISIBLE);
    }

    public void hideTextViews() {
        sets.setVisibility(View.INVISIBLE);
        sets1.setVisibility(View.INVISIBLE);
        sets2.setVisibility(View.INVISIBLE);
        sets3.setVisibility(View.INVISIBLE);
        sets4.setVisibility(View.INVISIBLE);

        reps.setVisibility(View.INVISIBLE);
        weight.setVisibility(View.INVISIBLE);

        rep1.setVisibility(View.INVISIBLE);
        rep2.setVisibility(View.INVISIBLE);
        rep3.setVisibility(View.INVISIBLE);
        rep4.setVisibility(View.INVISIBLE);
        weight1.setVisibility(View.INVISIBLE);
        weight2.setVisibility(View.INVISIBLE);
        weight3.setVisibility(View.INVISIBLE);
        weight4.setVisibility(View.INVISIBLE);
    }
}
