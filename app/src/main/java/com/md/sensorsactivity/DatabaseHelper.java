package com.md.sensorsactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SensorsData.db";
    public static final String TABLE_NAME = "Record_1";
    public static final String COL_ID = "ID";
    public static final String COL_TIME = "TIME";
    public static final String COL_X_ACC = "X_ACC";
    public static final String COL_Y_ACC = "Y_ACC";
    public static final String COL_Z_ACC = "Z_ACC";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME INTEGER, X_ACC FLOAT, Y_ACC FLOAT, Z_ACC FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // deleting table if exists
        onCreate(db);
    }
}
