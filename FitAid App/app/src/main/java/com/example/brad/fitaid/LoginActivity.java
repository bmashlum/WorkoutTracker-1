package com.example.brad.fitaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    private EditText name;
    private EditText password;
    private Button enter;
    private Button login;


    //public void validate(String userName, String password){
       // if ((username == "Admin") && (password == "1234")){
      //      Intent intent = new Intent( LoginActivity.this, MainActivity.class );
      //  }
   // }
  public void init(){

      enter = findViewById( R.id.enter );
      enter.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent go1 = new Intent(LoginActivity.this, SignInActivity.class);
              startActivity(go1);
          }
      } );
  }

  public void init1(){
      login = findViewById( R.id.login );
      login.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent go2 = new Intent (LoginActivity.this, MainActivity.class);
              startActivity(go2);
          }
      } );
  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        name = findViewById( R.id.name );
        password = findViewById( R.id.password );
        enter = findViewById( R.id.enter );
        login = findViewById( R.id.login );

    init();
    init1();
    }

}
