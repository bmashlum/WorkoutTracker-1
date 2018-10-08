package com.example.brad.fitaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="fitaidregister2.db";
    public static final String TABLE_NAME = "register2";
    public static final String COL_1 = "ID";
    public static final String COL_2 ="Email";
    public static final String COL_3 ="FirstName";
    public static final String COL_4 ="LastName";
    public static final String COL_5 ="Password";
//    public static final String COL_6 ="Pass2";

    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( String.format( "CREATE TABLE '%s'( ID INTEGER PRIMARY KEY AUTOINCREMENT, Email TEXT, FirstName TEXT, LastName TEXT, Password TEXT)", TABLE_NAME ) );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( String.format( "DROP TABLE IF EXISTS%s", TABLE_NAME ) ); //drop old table if exists
        onCreate( db );
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //creating a method below to insert data
    public boolean insertData(String email, String fname, String lname, String pass){
        //we will use this SQLDatabase instance to insert our data
        SQLiteDatabase db = this.getWritableDatabase();
        //now we need to create an instance of the class ContentValue
        ContentValues contentValues = new ContentValues();
        //we will now take this contentValues instance and insert it into the data columns
        //the first arg is the column name itself, the second is the data itself.
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, fname);
        contentValues.put(COL_4, lname);
        contentValues.put(COL_5, pass);

        //we then insert our data using the db instance created above
        //this takes three arguments. The first is the table name, The second is null and the third
        //is the contentValues which we have created.
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;
        } else {
            return true;
        }
    }

    //creating a method that will show all data that has been entered into the database, using
//elements from the Cursor class. The Cursor interface allows read-write access to the result
    public Cursor getAllData(){
        //creating an instance of the database class firstly to allow us to get all the data
        SQLiteDatabase db = this.getWritableDatabase();
        //now we will create an instance of the Cursor class called result and use the
        //rawQuery method. Basically creates a SQL query.
        Cursor result = db.rawQuery("select * from "+ TABLE_NAME,null);
        //we will now return the instance of this cursor, which is "result"
        return result;

    }
    //creating a method that will update all data in our database, using 4 args all of which are
//string, they are id, name, surname and marks
    public boolean updateData(String email, String fname, String lname, String pass){
        //creating an instance of the database class firstly to allow us to get all the data
        SQLiteDatabase db = this.getWritableDatabase();
        //now we need to create an instance of the class ContentValue
        ContentValues contentValues = new ContentValues();
        //we will now take this contentValues instance and insert it into the data columns
        //the first arg is the column name itself, the second is the data itself.
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, fname);
        contentValues.put(COL_4, lname);
        contentValues.put(COL_5, pass);

        //the below update method will update any args you pass through here
        //the first argument is the table name itself, the second is the contentValues, the third
        //is the condition you want to impose, such as "ID = ?" where the ? is the ID provided. The
        //fourth arg is the String[] array
        db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[] {email});
        //we will return true to see if the data is really updated or not
        return true;
    }

    public Integer deleteData(String email){
        //creating an instance of the database class firstly to allow us to get all the data
        SQLiteDatabase db = this.getWritableDatabase();
        //calling the delete function on our db instance. It takes 3 args, 1st is the name of the
        //table, the second is the ID number represented by "ID = ?" and the third arg is the String
        //Array[] of the argument type id. The return below returns the integer of the ID
        return db.delete(TABLE_NAME, "EMAIL = ?", new String[] {email});

    }

    public boolean hasObject(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + "= ?";

        Cursor cursor = db.rawQuery(selectString,new String[]{email});
        boolean exist;
        if(cursor.getCount()>0){
            exist=true;
        } else {
            exist=false;
        }
        db.close();
        cursor.close();

        return exist;
    }

    public boolean hasObject2(String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_5 + "= ?";
        Cursor cursor = db.rawQuery(selectString,new String[]{pass});
        boolean exist;
        if(cursor.getCount()>0){
            exist=true;
        } else {
            exist=false;
        }
        db.close();
        cursor.close();

        return exist;
    }

//    public String getObjectKey(String email){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selectString = "SELECT ID FROM " + TABLE_NAME + "WHERE " + COL_2 + "= ?";
//
//
//    }

}
