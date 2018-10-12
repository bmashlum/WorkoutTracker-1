package com.example.brad.fitaid;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
                final String fname = _txtfname.getText().toString();
                final String lname = _txtlname.getText().toString();
                String pass = _txtpass.getText().toString();
                final String email = _txtemail.getText().toString();
                String pass2 = _txtpass2.getText().toString();


//                String[] recipient = email.split( "," );
//                String subject = "Thank you for signup";
//                String message = "Thank you for signing up to FitAid";

//                if ((fname.equals( "" )) || (lname.equals( "" )) || (pass.equals( "" )) || (email.equals( "" )) || (phone.equals( "" ))){
//                    //Toast.makeText( getApplicationContext(), "register successfully", Toast.LENGTH_LONG ).show();
////                    Intent go3 = new Intent( SignInActivity.this, LoginActivity.class );
////                    startActivity( go3 );
//                    Toast.makeText( getApplicationContext(), "not all fields are full", Toast.LENGTH_LONG ).show();
//                }
                if ((email.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter Your Email", Toast.LENGTH_LONG ).show();
                }
                if ((fname.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG ).show();
                }
                if ((lname.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter last name", Toast.LENGTH_LONG ).show();
                }
                if ((pass.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter password", Toast.LENGTH_LONG ).show();
                }
                if ((pass2.equals( "" ))) {
                    Toast.makeText( getApplicationContext(), "Please enter retype password", Toast.LENGTH_LONG ).show();
                }
                    String b = "@";
                    if (!email.contains( b )){
                        Toast.makeText( getApplicationContext(), "please enter email in correct form!", Toast.LENGTH_LONG ).show();
                }


                else if (pass.equals( pass2 )) {
                    insertdata( email, fname, lname, pass);
                    Toast.makeText( getApplicationContext(), "register successfully", Toast.LENGTH_LONG ).show();
                    Intent go3 = new Intent( SignInActivity.this, LoginActivity.class );
                    startActivity( go3 );

//                    Intent send = new Intent(Intent.ACTION_SEND);
//                    send.setData( Uri.parse("mailto:") );
//                    send.putExtra(Intent.EXTRA_EMAIL, recipient);
//                    send.putExtra(Intent.EXTRA_SUBJECT, subject);
//                    send.putExtra(Intent.EXTRA_TEXT, message);
//                    send.setType( "message/rfc822" );
//                    startActivity( Intent.createChooser( send, "" ) );

//                    try {
//                        GMailSender sender = new GMailSender("gilad543@gmail.com", "Gb0528916199");
//                        sender.sendMail("This is Subject",
//                                "This is Body",
//                                "gilad543@gmail.com",
//                                email);
//                    } catch (Exception e) {
//                        Log.e("FitAid", e.getMessage(), e);
//                    }

                    AsyncTask mailtask = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void[] objects) {
                            // Recipient's email ID needs to be mentioned.
                            String to = email;

                            // Sender's email ID needs to be mentioned
                            String from = "FitAid@gmail.com";
                            final String username = "FitAidApp@gmail.com";//change accordingly
                            final String password = "gilad1234";//change accordingly

                            // Assuming you are sending email through relay.jangosmtp.net
                            String host = "smtp.gmail.com";

                            Properties props = new Properties();
                            props.put( "mail.smtp.auth", "true" );
                            props.put( "mail.smtp.starttls.enable", "true" );
                            props.put( "mail.smtp.host", host );
                            props.put( "mail.smtp.port", "587" );

                            // Get the Session object.
                            Session session = Session.getInstance( props,
                                    new javax.mail.Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication( username, password );
                                        }
                                    } );

                            try {
                                // Create a default MimeMessage object.
                                Message message = new MimeMessage( session );

                                // Set From: header field of the header.
                                message.setFrom( new InternetAddress( from ) );

                                // Set To: header field of the header.
                                message.setRecipients( Message.RecipientType.TO,
                                        InternetAddress.parse( to ) );

                                // Set Subject: header field
                                message.setSubject( "Thank You From FITAID");

                                // Now set the actual message
                                message.setText( "Thank you: " + email + " for registering to FitAid " +
                                        "\nYour details are: \n" +
                                "Email: " + email +
                                "\nFirst Name: " + fname +
                                "\nLast Name: " + lname +
                                "\nPassword: *************" +
                                "\nLET'S GET STARTED!!!");

                                // Send message
                                Transport.send( message );

                                System.out.println( "Sent message successfully...." );

                            } catch (MessagingException e) {
                                throw new RuntimeException( e );
                            }
                            return null;
                        }

                    };
                    Void[] voids = new Void[1];
                        mailtask.execute( voids );

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

