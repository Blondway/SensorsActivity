package com.md.sensorsactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database name
    private static final String DATABASE_NAME = com.md.sensorsactivity.MainActivity.getNameOfDB();

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

    // Rotation vector
    private static final String COL_ROTATION_X = "rotation_X";
    private static final String COL_ROTATION_Y = "rotation_Y";
    private static final String COL_ROTATION_Z = "rotation_Z";
    private static final String COL_ROTATION_COS = "rotation_cos";
    private static final String COL_ROTATION_ACCURACY = "rotation_accuracy";

    //private static final String COL_ACC_RAW_DATA = "acc_recorded_values";
    //private static final String COL_ACC_REAL_DATA = "acc_FFT_real";
    //private static final String COL_ACC_IMAG_DATA = "acc_FFT_imag";
    //private static final String COL_ACC_FFT_MAG = "acc_FFT_magnitude";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Old query
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_REC_TIME + " TEXT,"
                + COL_DURATION + " TEXT," + COL_ACC_RAW_DATA + " TEXT," + COL_ACC_REAL_DATA + " TEXT,"
                + COL_ACC_IMAG_DATA + " TEXT," + COL_ACC_FFT_MAG + " TEXT," + COL_LABEL + " TEXT" + ")";
        */

        String ACC_DATA = COL_ACC_X + " TEXT," + COL_ACC_Y + " TEXT," + COL_ACC_Z + " TEXT,";
        String GYRO_DATA = COL_GYRO_X + " TEXT," + COL_GYRO_Y + " TEXT," + COL_GYRO_Z + " TEXT,";
        String ROTATION_DATA = COL_ROTATION_X + " TEXT," + COL_ROTATION_Y + " TEXT," + COL_ROTATION_Z + " TEXT," + COL_ROTATION_COS + " TEXT," + COL_ROTATION_ACCURACY + " TEXT,";

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_REC_TIME + " TEXT,"
                + COL_DURATION + " TEXT," + ACC_DATA + GYRO_DATA + ROTATION_DATA + COL_LABEL + " TEXT" + ")";

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
    public boolean insertData(String start_time,
                              double[] acc_x, double[] acc_y, double[] acc_z,
                              double[] gyro_x, double[] gyro_y, double[] gyro_z,
                              double[] rotation_x, double[] rotation_y, double[] rotation_z, double[] rotation_cos, double[] rotation_accuracy,
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
        contentValues.put(COL_ROTATION_X, Arrays.toString(rotation_x));
        contentValues.put(COL_ROTATION_Y, Arrays.toString(rotation_y));
        contentValues.put(COL_ROTATION_Z, Arrays.toString(rotation_z));
        contentValues.put(COL_ROTATION_COS, Arrays.toString(rotation_cos));
        contentValues.put(COL_ROTATION_ACCURACY, Arrays.toString(rotation_accuracy));
        contentValues.put(COL_DURATION, String.valueOf(duration_time));
        contentValues.put(COL_LABEL, label);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    /* Old method
    public boolean insertData(String start_time, double[] raw_data, double[] real_data, double[] imag_data, double[] fftMag_data, long duration_time, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_REC_TIME, start_time);
        contentValues.put(COL_ACC_RAW_DATA, Arrays.toString(raw_data));
        contentValues.put(COL_ACC_REAL_DATA, Arrays.toString(real_data));
        contentValues.put(COL_ACC_IMAG_DATA, Arrays.toString(imag_data));
        contentValues.put(COL_ACC_FFT_MAG, Arrays.toString(fftMag_data));
        contentValues.put(COL_DURATION, String.valueOf(duration_time));
        contentValues.put(COL_LABEL, label);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    */

    //Deleting all data from the table
    public void deleteAllRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}