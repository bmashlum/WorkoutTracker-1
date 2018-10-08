package com.example.brad.fitaid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _btnreg;
    EditText _txtfname, _txtlname, _txtpass, _txtemail, _txtpass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signin );
        openHelper= new DatabaseHelper( this );
        _btnreg = (Button) findViewById( R.id.btnreg );
        _txtfname = (EditText) findViewById( R.id.txtfname );
        _txtlname = (EditText) findViewById( R.id.txtlname );
        _txtpass = (EditText) findViewById( R.id.txtpass );
        _txtemail = (EditText) findViewById( R.id.txtemail );
        _txtpass2 = (EditText) findViewById( R.id.txtpass2 );


        _btnreg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fname = _txtfname.getText().toString();
                String lname = _txtlname.getText().toString();
                String pass = _txtpass.getText().toString();
                String email = _txtemail.getText().toString();
                String pass2 = _txtpass2.getText().toString();


                String[] recipient = email.split( "," );
                String subject = "Thank you for signup";
                String message = "Thank you for signing up to FitAid";

//                if ((fname.equals( "" )) || (lname.equals( "" )) || (pass.equals( "" )) || (email.equals( "" )) || (phone.equals( "" ))){
//                    //Toast.makeText( getApplicationContext(), "register successfully", Toast.LENGTH_LONG ).show();
////                    Intent go3 = new Intent( SignInActivity.this, LoginActivity.class );
////                    startActivity( go3 );
//                    Toast.makeText( getApplicationContext(), "not all fields are full", Toast.LENGTH_LONG ).show();
//                }
                if ((fname.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG ).show();
                }
                if ((lname.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter last name", Toast.LENGTH_LONG ).show();
                }
                if ((pass.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter password", Toast.LENGTH_LONG ).show();
                }
                if ((email.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter Your Email", Toast.LENGTH_LONG ).show();
                }
                if ((pass2.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter retype password", Toast.LENGTH_LONG ).show();
                }


                else if (pass.equals( pass2 )) {
                    insertdata( email, fname, lname, pass);
                    Toast.makeText( getApplicationContext(), "register successfully", Toast.LENGTH_LONG ).show();
                    Intent go3 = new Intent( SignInActivity.this, LoginActivity.class );
                    startActivity( go3 );

                    Intent send = new Intent(Intent.ACTION_SEND);
                    send.setData( Uri.parse("mailto:") );
                    send.putExtra(Intent.EXTRA_EMAIL, recipient);
                    send.putExtra(Intent.EXTRA_SUBJECT, subject);
                    send.putExtra(Intent.EXTRA_TEXT, message);
                    send.setType( "message/rfc822" );
                    startActivity( Intent.createChooser( send, "" ) );
                }
                else{
                    Toast.makeText( getApplicationContext(), "retype does not match password", Toast.LENGTH_LONG ).show();
                }


            }
        } );
    }

    public void insertdata(String email, String fname, String lname, String pass){
        ContentValues contentValues = new ContentValues(  );
        contentValues.put(DatabaseHelper.COL_2, email);

        contentValues.put(DatabaseHelper.COL_3, fname);

        contentValues.put(DatabaseHelper.COL_4, lname);

        contentValues.put(DatabaseHelper.COL_5, pass);

       // contentValues.put(DatabaseHelper.COL_6, pass2);

        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }


    }

