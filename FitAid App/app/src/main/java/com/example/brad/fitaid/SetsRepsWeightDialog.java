package com.example.brad.fitaid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import static com.example.brad.fitaid.JournalFragment.pickedDate;
import static com.example.brad.fitaid.JournalFragment.pos;

public class SetsRepsWeightDialog extends AppCompatDialogFragment {
    View view;
    private ArrayList<Double> inputText = new ArrayList<>();
    private LinkedHashMap<String, Double> exerciseMap = new LinkedHashMap<String, Double>();
    private TextView set2, set3, set4;
    private TextInputLayout rep1, weight1, rep2, weight2, rep3, weight3, rep4, weight4;
    private TextInputEditText etRep1, etWeight1, etRep2, etWeight2, etRep3, etWeight3, etRep4, etWeight4;
    private int counter = 1;
    private double r1, w1, r2, w2, r3, w3, r4, w4;

    final String email = SignInActivity.userId.replaceAll("\\.", "");
    final String date_n = new SimpleDateFormat("M,dd,yyyy", Locale.getDefault()).format(new Date());
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/" + email + "/" + "Journal" + "/" + date_n + "/" + pos);


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Button addSet = new Button(getContext());
        //addSet.findViewById(R.id.btnAddSet);

        String popUpTitle = pos;

        TextView textView = new TextView(getContext());
        textView.setText(popUpTitle);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setTextColor(Color.WHITE);


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);

        etRep1 = view.findViewById(R.id.input_rep1);
        etWeight1 = view.findViewById(R.id.input_weight1);
        etRep2 = view.findViewById(R.id.input_rep2);
        etWeight2 = view.findViewById(R.id.input_weight2);
        etRep3 = view.findViewById(R.id.input_rep3);
        etWeight3 = view.findViewById(R.id.input_weight3);
        etRep4 = view.findViewById(R.id.input_rep4);
        etWeight4 = view.findViewById(R.id.input_weight4);


        set2 = view.findViewById(R.id.tvSets2);
        set3 = view.findViewById(R.id.tvSets3);
        set4 = view.findViewById(R.id.tvSets4);

        set2.setVisibility(View.INVISIBLE);
        set3.setVisibility(View.INVISIBLE);
        set4.setVisibility(View.INVISIBLE);

        rep1 = view.findViewById(R.id.rep1_input_layout);
        rep2 = view.findViewById(R.id.rep2_input_layout);
        rep3 = view.findViewById(R.id.rep3_input_layout);
        rep4 = view.findViewById(R.id.rep4_input_layout);
        weight1 = view.findViewById(R.id.weight1_input_layout);
        weight2 = view.findViewById(R.id.weight2_input_layout);
        weight3 = view.findViewById(R.id.weight3_input_layout);
        weight4 = view.findViewById(R.id.weight4_input_layout);
        rep2.setVisibility(View.INVISIBLE);
        weight2.setVisibility(View.INVISIBLE);
        rep3.setVisibility(View.INVISIBLE);
        weight3.setVisibility(View.INVISIBLE);
        rep4.setVisibility(View.INVISIBLE);
        weight4.setVisibility(View.INVISIBLE);


        builder.setView(view)
                .setCustomTitle(textView)
                .setNeutralButton("Add Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputText.clear();
                        try {
                            r1 = Double.parseDouble(etRep1.getText().toString());
                            w1 = Double.parseDouble(etWeight1.getText().toString());
                            r2 = Double.parseDouble(etRep2.getText().toString());
                            w2 = Double.parseDouble(etWeight2.getText().toString());
                            r3 = Double.parseDouble(etRep3.getText().toString());
                            w3 = Double.parseDouble(etWeight3.getText().toString());
                            r4 = Double.parseDouble(etRep4.getText().toString());
                            w4 = Double.parseDouble(etWeight4.getText().toString());
                        } catch (NumberFormatException e) {
                            System.out.println("EMPTY STRING");
                        }

                        inputText.add(r1);
                        inputText.add(w1);
                        inputText.add(r2);
                        inputText.add(w2);
                        inputText.add(r3);
                        inputText.add(w3);
                        inputText.add(r4);
                        inputText.add(w4);

                        exerciseMap.put("Set1 Reps", inputText.get(0));
                        exerciseMap.put("Set1 Weight", inputText.get(1));
                        exerciseMap.put("Set2 Reps", inputText.get(2));
                        exerciseMap.put("Set2 Weight", inputText.get(3));
                        exerciseMap.put("Set3 Reps", inputText.get(4));
                        exerciseMap.put("Set3 Weight", inputText.get(5));
                        exerciseMap.put("Set4 Reps", inputText.get(6));
                        exerciseMap.put("Set4 Weight", inputText.get(7));

                        ref.setValue(exerciseMap);
                    }
                });

        return builder.create();


    }

    @Override
    public void onStart() {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog d = (AlertDialog) getDialog();

        if (d != null) {
            Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter = counter + 1;
                    switch (counter) {
                        case 2:
                            set2.setVisibility(View.VISIBLE);
                            rep2.setVisibility(View.VISIBLE);
                            weight2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            set3.setVisibility(View.VISIBLE);
                            rep3.setVisibility(View.VISIBLE);
                            weight3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            set4.setVisibility(View.VISIBLE);
                            rep4.setVisibility(View.VISIBLE);
                            weight4.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }

                    //Boolean wantToCloseDialog = false;
                    System.out.println("NEUTRAL BUTTON WORKS");

                    //if(wantToCloseDialog)
                    //dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }

}
