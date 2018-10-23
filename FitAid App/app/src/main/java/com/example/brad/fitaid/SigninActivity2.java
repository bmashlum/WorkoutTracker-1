package com.example.brad.fitaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_in2 );
        mAuth = FirebaseAuth.getInstance();
        email = findViewById( R.id. etEmailSign);
        pw = findViewById( R.id.etPassSign );
    }

    public void signinUser(View view) {
        String mail = email.getText().toString();
        String password = pw.getText().toString();
        if (mail.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signin", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity( new Intent( SigninActivity2.this, MainActivity.class ) );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signin", "signInWithEmail:failure", task.getException());
                                Toast.makeText(SigninActivity2.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
        else {
            Toast.makeText( this, "Enter email and password", Toast.LENGTH_SHORT ).show();
        }
    }

    public void gotoCreate(View view) {
        startActivity( new Intent (SigninActivity2.this, CreateAccountActivity.class));
    }
}


