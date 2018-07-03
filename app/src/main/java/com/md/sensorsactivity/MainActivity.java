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
    private TextView xText2, yText2, zText2;
    private TextView xText3, yText3, zText3, cosText3, accuracyText3;
    private Sensor accSensor; // Accelerometer
    private Sensor gyroSensor; // Gyroscope
    private Sensor rotationSensor; // Rotation vector
    private SensorManager SM;
    private RecordData recData;

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
    private double x, y, z, x2, y2, z2, x3, y3, z3, cos3, accuracy3;

    /* FFT
    double[] realDoubleArray = new double[samples_total];
    double[] imagDoubleArray = new double[samples_total];
    double[] rawDataDoubleArray = new double[samples_total];
    */

    double[] accDoubleArrayX = new double[samples_total];
    double[] accDoubleArrayY = new double[samples_total];
    double[] accDoubleArrayZ = new double[samples_total];

    double[] gyroDoubleArrayX = new double[samples_total];
    double[] gyroDoubleArrayY = new double[samples_total];
    double[] gyroDoubleArrayZ = new double[samples_total];

    double[] rotationDoubleArrayX = new double[samples_total];
    double[] rotationDoubleArrayY = new double[samples_total];
    double[] rotationDoubleArrayZ = new double[samples_total];
    double[] rotationDoubleArrayCos = new double[samples_total];
    double[] rotationDoubleArrayAccuracy = new double[samples_total];


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
        // Gyroscope Sensor
        gyroSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //Rotation vector Sensor
        rotationSensor = SM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // Register Sensor Listener
        SM.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Accelerometer's views
        xText = (TextView) findViewById(R.id.xText);
        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);

        // Gyroscope's views
        xText2 = (TextView) findViewById(R.id.xText2);
        yText2 = (TextView) findViewById(R.id.yText2);
        zText2 = (TextView) findViewById(R.id.zText2);

        // Rotation vector's views
        xText3 = (TextView) findViewById(R.id.xText3);
        yText3 = (TextView) findViewById(R.id.yText3);
        zText3 = (TextView) findViewById(R.id.zText3);
        cosText3 = (TextView) findViewById(R.id.cosText3);
        accuracyText3 = (TextView) findViewById(R.id.accuracyText3);

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

        //Initialize method for recording data
        recData = new RecordData();

    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Getting and displaying accelerometer data
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
            /*
            xText.setText("X: " + sensorEvent.values[0]);
            yText.setText("Y: " + sensorEvent.values[1]);
            zText.setText("Z: " + sensorEvent.values[2]);
            // 0 - X axis, 1 - Y axis, 2 - Z axis
            */
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //Getting and displaying gyroscope data
            x2 = sensorEvent.values[0];
            y2 = sensorEvent.values[1];
            z2 = sensorEvent.values[2];
            /*
            xText2.setText("X: " + sensorEvent.values[0]);
            yText2.setText("Y: " + sensorEvent.values[1]);
            zText2.setText("Z: " + sensorEvent.values[2]);
            // 0 - X axis, 1 - Y axis, 2 - Z axis
            */
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            //Getting and displaying gyroscope data
            x3 = sensorEvent.values[0]; // x*sin(θ/2)
            y3 = sensorEvent.values[1]; // y*sin(θ/2)
            z3 = sensorEvent.values[2]; // z*sin(θ/2)
            cos3 = sensorEvent.values[3]; // cos(θ/2)
            accuracy3 = sensorEvent.values[4]; // estimated heading Accuracy (in radians) (-1 if unavailable)
            /*
            xText3.setText("X: " + sensorEvent.values[0]);
            yText3.setText("Y: " + sensorEvent.values[1]);
            zText3.setText("Z: " + sensorEvent.values[2]);
            cosText3.setText("Cos: " + sensorEvent.values[3]);
            accuracyText3.setText("Accuracy: " + sensorEvent.values[4]);
            */
        }

        //Calculate module of single sensor data
        //final double module = Math.sqrt(x * x + y * y + z * z);

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
                //for (int i = 0; i < samples_total; i++) imagDoubleArray[i] = 0;

                flag = true; //flag set to true = record data
                samples = 0;
                startTime = System.currentTimeMillis();

                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

            }
        });

        //Record data and insert to database
        if (flag == true) {
            recData.recordDataMethod(startTime, dateTime, samples_total, samples, x, y, z, x2, y2, z2, x3, y3, z3, cos3, accuracy3, label);
            Log.d("", "Data inserted!");
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_LONG).show();
            flag = false;
        }

    /*
        //Insert given number of sensor data samples into database
        if (samples < samples_total) {
            if (flag == true) {

                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

                //Add data to array
                //rawDataDoubleArray[samples] = module;
                //Log.d("", "Inserted:" + rawDoubleArray[samples]);

                //Add x,y,z values to arrays for sensors
                accDoubleArrayX[samples] = x;
                accDoubleArrayY[samples] = y;
                accDoubleArrayZ[samples] = z;

                gyroDoubleArrayX[samples] = x2;
                gyroDoubleArrayY[samples] = y2;
                gyroDoubleArrayZ[samples] = z2;

                rotationDoubleArrayX[samples] = x3;
                rotationDoubleArrayY[samples] = y3;
                rotationDoubleArrayZ[samples] = z3;
                rotationDoubleArrayCos[samples] = cos3;
                rotationDoubleArrayAccuracy[samples] = accuracy3;

                samples++;

            } else {
                //Log.d("", "samples < 10 but Flag is false - Data NOT inserted");
            }
        } else if (samples == samples_total) {
            durationTime = System.currentTimeMillis() - startTime;

            //Log.d("", "Recorded data module: " + Arrays.toString(rawDataDoubleArray));

            /* FFT
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
            */
    /*
            //Insert x,y,z values for sensors
            dbRef.insertData(dateTime, accDoubleArrayX, accDoubleArrayY, accDoubleArrayZ,
                    gyroDoubleArrayX, gyroDoubleArrayY, gyroDoubleArrayZ,
                    rotationDoubleArrayX, rotationDoubleArrayY, rotationDoubleArrayZ, rotationDoubleArrayCos, rotationDoubleArrayAccuracy,
                    durationTime, label);

            //Stop recording when given number of samples inserted
            flag = false;
            samples++;

        } else {
            //Not in use
        }
    */
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