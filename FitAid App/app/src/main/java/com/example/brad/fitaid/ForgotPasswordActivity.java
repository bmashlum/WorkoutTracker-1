package com.example.brad.fitaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class ForgotPasswordActivity extends Activity {
    private TextView message_forgot;
    private EditText email_to_reset;
    private Button reset_password;
    private FirebaseAuth mauth;


@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);
    mauth = FirebaseAuth.getInstance();

    email_to_reset = (EditText) findViewById(R.id.et_email_reset_pass);
    reset_password = (Button) findViewById(R.id.btn_reset_pass);


    reset_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String user_Email = email_to_reset.getText().toString();

            if (TextUtils.isEmpty(user_Email)) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else {
                mauth.sendPasswordResetEmail(user_Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPasswordActivity.this, SignInActivity.class));
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(ForgotPasswordActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    });
}
}
