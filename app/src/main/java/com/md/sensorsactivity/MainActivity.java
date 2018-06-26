package com.md.sensorsactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.Math;
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
    private int samples_total = 32;
    Date currentTime;
    String dateTime;
    long startTime;
    long durationTime;
    String label;

    double[] realDoubleArray = new double[samples_total];
    double[] imagDoubleArray = new double[samples_total];
    double[] rawDataDoubleArray = new double[samples_total];

    private EditText textLabel;


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

        //Options button - go to OptionsActivity on Click
        Button optionsButton = (Button) findViewById(R.id.buttonOptions);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
            }
        });

        //Initialize EditText for label
        textLabel = (EditText) findViewById(R.id.editTextLabel);

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

                //Get label from EditText
                label = textLabel.getText().toString();

                //Create empty table (for imaginary FFT input)
                for (int i = 0; i < samples_total; i++) imagDoubleArray[i] = 0;

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
                rawDataDoubleArray[samples] = module;
                //Log.d("", "Zobacz co wstawilem:" + rawDoubleArray[samples]);

                samples++;

            } else {
                //Log.d("", "samples < 10 but Flag is false - Data NOT inserted");
            }
        } else if (samples == samples_total) {
            durationTime = System.currentTimeMillis() - startTime;

            Log.d("", "Recorded data module: " + Arrays.toString(rawDataDoubleArray));

            realDoubleArray = rawDataDoubleArray; // compute FFT but from the copied Array

            //Calculate FFT for recorded data module
            FFT fft = new FFT(realDoubleArray.length);
            fft.fft(realDoubleArray, imagDoubleArray);
            double[] fftMagDoubleArray = new double[realDoubleArray.length];

            Log.d("", "FFT real: " + Arrays.toString(realDoubleArray));
            Log.d("", "FFT imaginary: " + Arrays.toString(imagDoubleArray));

            //Calculate magnitude of FFT data
            for (int i = 0; i < realDoubleArray.length; i++) {
                fftMagDoubleArray[i] = Math.sqrt(Math.pow(realDoubleArray[i], 2) + Math.pow(imagDoubleArray[i], 2));
            }

            Log.d("", "FFT Magnitude: " + Arrays.toString(fftMagDoubleArray));

            //Insert FFT magnitude data (with date, duration and label)
            dbRef.insertData(dateTime, rawDataDoubleArray, realDoubleArray, imagDoubleArray, fftMagDoubleArray, durationTime, label);
            Log.d("", "Data inserted!");
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_LONG).show();

            //Stop recording when given number of samples inserted
            flag = false;
            samples++;

        } else {
            //Not in use
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