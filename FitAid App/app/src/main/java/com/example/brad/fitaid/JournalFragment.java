package com.example.brad.fitaid;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment {
    private ListView lvJournalEntries;
    private TextView tvDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ArrayList<String> dummy = new ArrayList<>();
    ArrayAdapter adapter;

    public JournalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_journal, container, false);

        lvJournalEntries = v.findViewById(R.id.lvJournalEntries);


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



        return v;
    }


    protected void displayReceivedData(ArrayList<String> input)
    {
        adapter = new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.list_view, input);

        lvJournalEntries.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
