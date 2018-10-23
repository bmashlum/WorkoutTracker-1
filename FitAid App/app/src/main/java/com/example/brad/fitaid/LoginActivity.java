package com.example.brad.fitaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    DatabaseHelper myDb;
    private EditText email;
    private EditText password;
    private Button enter;
    private Button login;
  //  int counter = 5;
    private TextView info;



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

//  public void init1(){
//      login = findViewById( R.id.login );
//      login.setOnClickListener( new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
////              validate( name.getText().toString(), password.getText().toString() );
//              Intent go2 = new Intent (LoginActivity.this, MainActivity.class);
//              startActivity(go2);
//          }
//      } );
//  }

//    public void validate(String userName, String password){
//        info = findViewById(R.id.info);
//     if ((userName == "Admin") && (password == "1234")){
//          Intent intent = new Intent( LoginActivity.this, MainActivity.class );
//          startActivity(intent);
//
//      }
//      else{
//
//       counter--;
//
//       info.setText("No of attempts remaining: " + String.valueOf( counter ));
//
//       if (counter ==0){
//           login.setEnabled( false );
//       }
//     }
//     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        myDb = new DatabaseHelper( this );

        email = findViewById( R.id.email_login );
        password = findViewById( R.id.password );
        enter = findViewById( R.id.enter );
        login = findViewById( R.id.login );
        info = findViewById(R.id.info);


       signIn();

      //  info.setText( "No of attempts remaining: 5" );
    init();
    //init1();
    }



    public void signIn(){
      login.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              boolean recordExists = myDb.hasObject( email.getText().toString() );
              boolean passExists = myDb.hasObject2( password.getText().toString() );
              if ((recordExists==true) && (passExists==true)){

                  Intent intentsignin = new Intent(getApplicationContext(), MainActivity.class);
                  Toast.makeText( getApplicationContext(), "Login successful, redirection to main page", Toast.LENGTH_LONG).show();
                  
                  startActivity( intentsignin );
              } else{
                  Toast.makeText(getApplicationContext(), "Invalid email or password, please try again", Toast.LENGTH_LONG).show();
              }
          }
      } );
    }

}
