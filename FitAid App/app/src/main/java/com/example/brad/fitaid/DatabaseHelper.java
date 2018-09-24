package com.example.brad.fitaid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="fitaidregister.db";
    public static final String TABLE_NAME = "register";
    public static final String COL_1 = "ID";
    public static final String COL_2 ="FirstName";
    public static final String COL_3 ="LastName";
    public static final String COL_4 ="Password";
    public static final String COL_5 ="Email";
    public static final String COL_6 ="Phone";

    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( String.format( "CREATE TABLE '%s'( ID INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT, LastName TEXT, Password TEXT, Email TEXT, Phone TEXT)", TABLE_NAME ) );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( String.format( "DROP TABLE IF EXISTS%s", TABLE_NAME ) ); //drop old table if exists
        onCreate( db );
    }
}
