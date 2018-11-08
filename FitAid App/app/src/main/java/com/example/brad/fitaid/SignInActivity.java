package com.example.brad.fitaid;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {


    static FirebaseAuth mAuth;
    EditText email, ab;
    public static String userEmail;
    private String mail;
    public static String userId= "";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference( "/");
    private TextView forgot_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById( R.id.etEmailSign);
        ab = findViewById( R.id.etPassSign );
        forgot_pass = (TextView) findViewById(R.id.tv_forgot_pass);


    }

    public void signinUser(View view) {
         mail = email.getText().toString();

        String password = ab.getText().toString();
        if (mail.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signin", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                userEmail = email.getText().toString();
                                startActivity( new Intent( SignInActivity.this, MainActivity.class ) );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signin", "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
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
        startActivity( new Intent (SignInActivity.this, CreateAccountActivity.class));
    }
    public void goToReset(View view){
        startActivity( new Intent (SignInActivity.this, ForgotPasswordActivity.class));
    }

}


