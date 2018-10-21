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

public class CreateAccountActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText mail, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_account );
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById( R.id.etEmail );
        password = findViewById( R.id.etPass );

    }

    public void createAccount(View view) {
        String email = mail.getText().toString();
        String pw = password.getText().toString();
        if (email.length() > 0 && pw.length() > 0) {
            mAuth.createUserWithEmailAndPassword( email, pw )
                    .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e( "Create", "createUserWithEmail:success" );
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity( new Intent( CreateAccountActivity.this, MainActivity.class ) );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e( "Create", "createUserWithEmail:failure", task.getException() );
                                Toast.makeText( CreateAccountActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT ).show();

                            }

                            // ...
                        }
                    } );
        }
        else {
            Toast.makeText( this, "enter email amd password", Toast.LENGTH_SHORT ).show();
        }
    }

}
