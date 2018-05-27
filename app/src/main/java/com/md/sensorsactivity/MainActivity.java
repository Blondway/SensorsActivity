package com.md.sensorsactivity;

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
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private Sensor accSensor; // Accelerometer
    private SensorManager SM;

    private static String NameOfPackage;
    private static String NameOfDB;
    private static DatabaseHelper dbRef;

    private boolean flag;
    private int samples;
    private int samples_total = 8;
    Date currentTime;
    String dateTime;
    long startTime;
    long durationTime;

    double[] recordsDoubleArray = new double[samples_total];
    double[] emptyDoubleArray = new double[samples_total];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = false; //false = not recording data, true = recording data

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

        //Delete all rows from table on click
        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.deleteAllRows();
                Log.d("", "All data deleted.");
            }
        });

        for (int i = 0; i < samples_total; i++) emptyDoubleArray[i] = 0;
        Log.d("", "Pusta tablica wyglada tak: " + Arrays.toString(emptyDoubleArray));
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {

        //Getting and displaying accelerometer data
        final double x = sensorEvent.values[0];
        final double y = sensorEvent.values[1];
        final double z = sensorEvent.values[2];
        xText.setText("X: " + sensorEvent.values[0]);
        yText.setText("Y: " + sensorEvent.values[1]);
        zText.setText("Z: " + sensorEvent.values[2]);
        // 0 - X axis, 1 - Y axis, 2 - Z axis

        //Calculate module of single sensor data
        final double module = Math.sqrt(x * x + y * y + z * z);

        //Check if database exists
        File dbtest = new File("/data/data/" + getNameOfPackage() + "/databases/" + NameOfDB);
        if (dbtest.exists()) {
            //Log.d("", "Adding to database is possible!");
        } else {
            Log.d("", "Database don't exists.");
        }

        //Start recording sensor data on click
        Button startButton = (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true; //flag set to true = record data
                samples = 0;
                startTime = System.currentTimeMillis();
            }
        });

        //Insert given number of sensor data samples into database
        if (samples < samples_total) {
            if (flag == true) {

                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

                //Add data to array
                recordsDoubleArray[samples] = module;
                //Log.d("", "Zobacz co wstawilem:" + recordsDoubleArray[samples]);

                samples++;

            } else {
                //Log.d("", "samples < 10 but Flag is false - Data NOT inserted");
            }
        } else if (samples == samples_total){
            durationTime = System.currentTimeMillis() - startTime;

            Log.d("", "Recorded data module: " + Arrays.toString(recordsDoubleArray));

            //Calculate FFT for recorded data module
            FFT fft = new FFT(recordsDoubleArray.length);
            fft.fft(recordsDoubleArray, emptyDoubleArray);
            double[] fftMag = new double[recordsDoubleArray.length];

            Log.d("", "FFT real: " + Arrays.toString(recordsDoubleArray));
            Log.d("", "FFT imaginary: " + Arrays.toString(emptyDoubleArray));

            //Calculate magnitude of FFT data
            for (int i = 0; i < recordsDoubleArray.length; i++) {
                fftMag[i] = Math.pow(recordsDoubleArray[i], 2) + Math.pow(emptyDoubleArray[i], 2);
            }

            Log.d("", "FFT Magnitude: " + Arrays.toString(fftMag));

            //Insert FFT magnitude data
            dbRef.insertData(dateTime, fftMag, durationTime);
            Log.d("", "Data inserted!");

            /*//Insert module data
            dbRef.insertData(dateTime, recordsDoubleArray, durationTime);
            Log.d("", "Data inserted!");
            */

            //Stop recording when given number of samples inserted
            flag = false;
            samples++;
            //Log.d("", "samples = 10, Flag set to false - Data inserted");
        } else {
            //Log.d("", "samples > 10 and Flag set to false - Data NOT inserted");
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