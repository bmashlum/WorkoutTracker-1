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

import androidx.appcompat.app.AppCompatDialogFragment;

public class SetsRepsWeightDialog extends AppCompatDialogFragment {
    View view;
   // Button addSet;
    // TextView set2,set3,set4;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       /* addSet.findViewById(R.id.btnAddSet);
        set2.findViewById(R.id.tvSets2);
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set2.setVisibility(View.VISIBLE);
            }
        });*/

        String popUpTitle = JournalFragment.pos;

        TextView textView = new TextView(getContext());
        textView.setText(popUpTitle);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setTextColor(Color.WHITE);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setCustomTitle(textView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

}
