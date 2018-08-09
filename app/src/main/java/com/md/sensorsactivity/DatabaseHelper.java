package com.md.sensorsactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 10;

    // Database name
    private static final String DATABASE_NAME = com.md.sensorsactivity.MainActivity.getNameOfDB();

    // TABLE RECORDS
    // Table name
    private static final String TABLE_NAME = "Records";

    // Table columns names
    private static final String COL_ID = "ID";

    private static final String COL_LABEL = "label";
    private static final String COL_REC_TIME = "rec_time";
    private static final String COL_DURATION = "duration";

    // Accelerometer
    private static final String COL_ACC_X = "acc_X";
    private static final String COL_ACC_Y = "acc_Y";
    private static final String COL_ACC_Z = "acc_Z";

    // Gyroscope
    private static final String COL_GYRO_X = "gyro_X";
    private static final String COL_GYRO_Y = "gyro_Y";
    private static final String COL_GYRO_Z = "gyro_Z";

    // TABLE MOD_FFT
    // Table name
    private static final String TABLE_MOD_NAME = "FFT_modules";

    // Table columns names
    private static final String COL_MOD_ID = "ID";

    private static final String COL_MOD_DATA = "data";
    private static final String COL_MOD_LABEL = "label";
    private static final String COL_MOD_REAL = "real";
    private static final String COL_MOD_IMAG = "imag";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String ACC_DATA = COL_ACC_X + " TEXT," + COL_ACC_Y + " TEXT," + COL_ACC_Z + " TEXT,";
        String GYRO_DATA = COL_GYRO_X + " TEXT," + COL_GYRO_Y + " TEXT," + COL_GYRO_Z + " TEXT,";

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_REC_TIME + " TEXT,"
                + COL_DURATION + " TEXT," + ACC_DATA + GYRO_DATA + COL_LABEL + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);

        String CREATE_MOD_TABLE = "CREATE TABLE " + TABLE_MOD_NAME + "(" + COL_MOD_ID + " INTEGER PRIMARY KEY,"
                + COL_MOD_DATA + " TEXT," + COL_MOD_REAL + " TEXT," + COL_MOD_IMAG + " TEXT," + COL_MOD_LABEL + " TEXT" + ")";

        db.execSQL(CREATE_MOD_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOD_NAME);
        // Create tables again
        onCreate(db);
    }

    //Inserting data
    public boolean insertData(String start_time,
                              double[] acc_x, double[] acc_y, double[] acc_z,
                              double[] gyro_x, double[] gyro_y, double[] gyro_z,
                              long duration_time, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_REC_TIME, start_time);
        contentValues.put(COL_ACC_X, Arrays.toString(acc_x));
        contentValues.put(COL_ACC_Y, Arrays.toString(acc_y));
        contentValues.put(COL_ACC_Z, Arrays.toString(acc_z));
        contentValues.put(COL_GYRO_X, Arrays.toString(gyro_x));
        contentValues.put(COL_GYRO_Y, Arrays.toString(gyro_y));
        contentValues.put(COL_GYRO_Z, Arrays.toString(gyro_z));
        contentValues.put(COL_DURATION, String.valueOf(duration_time));
        contentValues.put(COL_LABEL, label);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Inserting data to FFT module table
    public boolean insertFFTData(double[] modules, double[] real, double[] imag, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MOD_DATA, Arrays.toString(modules));
        contentValues.put(COL_MOD_REAL, Arrays.toString(real));
        contentValues.put(COL_MOD_IMAG, Arrays.toString(imag));
        contentValues.put(COL_MOD_LABEL, label);
        long result = db.insert(TABLE_MOD_NAME, null, contentValues);
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