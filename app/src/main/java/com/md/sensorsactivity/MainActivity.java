package com.md.sensorsactivity;

import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private Sensor accSensor; // Accelerometer
    private SensorManager SM;

    private static String NameOfPackage;
    private static String NameOfDB;
    private static DatabaseHelper dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create Sensor Manager
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        accSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register Sensor Listener
        SM.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);

        xText = (TextView) findViewById(R.id.xText);
        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);

        NameOfPackage = getApplicationContext().getPackageName();
        NameOfDB = "SensorsData.db";

        // Create database
        final DatabaseHelper db = new DatabaseHelper(this);
        dbRef = db;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Getting and displaying accelerometer data
        final double x = sensorEvent.values[0];
        final double y = sensorEvent.values[1];
        final double z = sensorEvent.values[2];
        xText.setText("X: " + sensorEvent.values[0]);
        yText.setText("Y: " + sensorEvent.values[1]);
        zText.setText("Z: " + sensorEvent.values[2]);
        // 0 - X axis, 1 - Y axis, 2 - Z axis
        //sensorsDB.insertData(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);

        final double module = Math.sqrt(x*x+y*y+z*z);

        //Inserting data every second if database exist
        File dbtest = new File("/data/data/" + getNameOfPackage() + "/databases/" + NameOfDB);
        if (dbtest.exists()) {
            Log.d("", "Adding to database is possible!");
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    dbRef.insertData(module);
                    Log.d("", "Data inserted!");
                }
            }, 0, 1000);//put here time 1000 milliseconds=1 second
        } else {
            Log.d("", "Database don't exists.");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not in use
    }

    //Return methods
    public static String getNameOfPackage() {
        return NameOfPackage;
    }

    public static String getNameOfDB() {
        return NameOfDB;
    }

    public static DatabaseHelper getDB() {
        return dbRef;
    }
}