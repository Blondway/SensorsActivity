package com.md.sensorsactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database name
    private static final String DATABASE_NAME = com.md.sensorsactivity.MainActivity.getNameOfDB();

    // Table name
    private static final String TABLE_NAME = "Records";

    // Table columns names
    private static final String COL_ID = "id";
    private static final String COL_VALUES = "all_values";
    private static final String COL_LABEL = "label";
    private static final String COL_REC_TIME = "rec_time";
    private static final String COL_DURATION = "duration";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_REC_TIME + " TEXT,"
                + COL_DURATION + " TEXT," + COL_VALUES + " TEXT," + COL_LABEL + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    //Inserting data
    public boolean insertData(String  start_time, ArrayList single_data, long duration_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_REC_TIME, start_time);
        contentValues.put(COL_VALUES, String.valueOf(single_data));
        contentValues.put(COL_DURATION, String.valueOf(duration_time));
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Deleting all data from the table
    public void deleteAllRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}