package com.md.sensorsactivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView statusText, textSamples, resultText;
    private Sensor accSensor; // Accelerometer
    private Sensor gyroSensor; // Gyroscope
    private SensorManager SM;

    private static String NameOfPackage;
    private static String NameOfDB;
    private static DatabaseHelper dbRef;

    private boolean flag;
    private boolean flagClassify;
    private int samples_acc, samples_gyro;
    private int samples_total = 2048;
    private int classify_samples = 32;
    Date currentTime;
    String dateTime;
    long startTime;
    long durationTime;
    String label;
    private double x, y, z, x2, y2, z2;

    double[] accDoubleArrayX = new double[samples_total];
    double[] accDoubleArrayY = new double[samples_total];
    double[] accDoubleArrayZ = new double[samples_total];

    double[] gyroDoubleArrayX = new double[samples_total];
    double[] gyroDoubleArrayY = new double[samples_total];
    double[] gyroDoubleArrayZ = new double[samples_total];

    private EditText textLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = false; //false = not recording data, true = recording data
        flagClassify = false;

        // Create Sensor Manager
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        accSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Gyroscope Sensor
        gyroSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Register Sensor Listener
        SM.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);

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
                Toast.makeText(MainActivity.this, "Dane usunięte", Toast.LENGTH_LONG).show();
            }
        });

        //Initialize EditText for label
        textLabel = (EditText) findViewById(R.id.editTextLabel);

        //Initialize EditText for samples
        textSamples = (EditText) findViewById(R.id.editTextSamples);

        //Status text view
        statusText = (TextView) findViewById(R.id.statusTextView);
        statusText.setTextColor(Color.parseColor("#757575"));

        //Result text view
        resultText = (TextView) findViewById(R.id.resultTextView);

        //Start recording sensor data on click
        Button startButton = (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get label from EditText
                label = textLabel.getText().toString();

                //Get total number of samples from EditText
                samples_total = Integer.parseInt(textSamples.getText().toString());

                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

                flag = true; //flag set to true = record data

                samples_acc = 0;
                samples_gyro = 0;

                startTime = System.currentTimeMillis();

                statusText.setText("Zapisywanie...");
                statusText.setTextColor(Color.parseColor("#4CAF50"));

            }
        });

        //Start recording sensor data on click
        Button classifyButton = (Button) findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resultText.setText("Klasyfikowanie...");
                resultText.setTextColor(Color.parseColor("#4CAF50"));

                flagClassify = true;
                samples_acc = 0;
                samples_gyro = 0;
            }
        });

        //Check if database exists
        File dbtest = new File("/data/data/" + getNameOfPackage() + "/databases/" + NameOfDB);
        if (dbtest.exists()) {
            Log.d("", "Adding to database is possible!");
            Toast.makeText(this, "Dodawanie aktywności jest możliwe", Toast.LENGTH_LONG).show();
        } else {
            Log.d("", "Database don't exists.");
            Toast.makeText(this, "Dodawanie aktywności NIE jest możliwe", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Getting accelerometer data
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //Getting gyroscope data
            x2 = sensorEvent.values[0];
            y2 = sensorEvent.values[1];
            z2 = sensorEvent.values[2];

        }

        //Insert given number of sensor data samples into database
        if ((samples_acc < samples_total || samples_gyro < samples_total) && flag == true)  {

            //Add x,y,z values to arrays for sensors
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && samples_acc < samples_total) {
                accDoubleArrayX[samples_acc] = x;
                accDoubleArrayY[samples_acc] = y;
                accDoubleArrayZ[samples_acc] = z;

                samples_acc++;
            }

            if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE && samples_gyro < samples_total) {
                gyroDoubleArrayX[samples_gyro] = x2;
                gyroDoubleArrayY[samples_gyro] = y2;
                gyroDoubleArrayZ[samples_gyro] = z2;

                samples_gyro++;
            }

        } else if (samples_acc == samples_total && samples_gyro == samples_total && flag == true){

            durationTime = System.currentTimeMillis() - startTime;

            //Insert x,y,z values for sensors
            dbRef.insertData(dateTime, accDoubleArrayX, accDoubleArrayY, accDoubleArrayZ,
                    gyroDoubleArrayX, gyroDoubleArrayY, gyroDoubleArrayZ, durationTime, label);

            Log.d("", "Data inserted!");
            Toast.makeText(this, "Dane zapisane", Toast.LENGTH_LONG).show();

            //Stop recording when given number of samples inserted
            flag = false;
            samples_acc++;
            samples_gyro++;

            statusText.setText("Zapisywanie wyłączone");
            statusText.setTextColor(Color.parseColor("#757575"));

        }
        else {
            //Not in use
        }

        //------------------------------------------------------------------------------------------

        //Classify
        if ((samples_acc < classify_samples || samples_gyro < classify_samples) && flagClassify == true)  {

            //Add x,y,z values to arrays for sensors
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && samples_acc < classify_samples) {
                accDoubleArrayX[samples_acc] = x;
                accDoubleArrayY[samples_acc] = y;
                accDoubleArrayZ[samples_acc] = z;

                samples_acc++;
            }

            if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE && samples_gyro < classify_samples) {
                gyroDoubleArrayX[samples_gyro] = x2;
                gyroDoubleArrayY[samples_gyro] = y2;
                gyroDoubleArrayZ[samples_gyro] = z2;

                samples_gyro++;
            }

        } else if (samples_acc == classify_samples && samples_gyro == classify_samples && flagClassify == true){

            double[] classifyRawDataDoubleArrayAcc = new double[classify_samples];
            double accModule;
            double[] classifyRawDataDoubleArrayGyro = new double[classify_samples];
            double gyroModule;

            for (int s = 0; s < classify_samples; s++) {

                //Add data to array
                accModule = Math.sqrt(accDoubleArrayX[s] * accDoubleArrayX[s] + accDoubleArrayY[s] * accDoubleArrayY[s] + accDoubleArrayZ[s] * accDoubleArrayZ[s]);
                classifyRawDataDoubleArrayAcc[s] = accModule;

                gyroModule = Math.sqrt(gyroDoubleArrayX[s] * gyroDoubleArrayX[s] + gyroDoubleArrayY[s] * gyroDoubleArrayY[s] + gyroDoubleArrayZ[s] * gyroDoubleArrayZ[s]);
                classifyRawDataDoubleArrayGyro[s] = gyroModule;
            }

            Log.d("", "Recorded data module: " + Arrays.toString(classifyRawDataDoubleArrayAcc));

            //FFT
            double [] classifyRealDoubleArrayAcc = classifyRawDataDoubleArrayAcc;
            double [] classifyImagDoubleArrayAcc = new double[classifyRealDoubleArrayAcc.length];

            double [] classifyRealDoubleArrayGyro = classifyRawDataDoubleArrayGyro;
            double [] classifyImagDoubleArrayGyro = new double[classifyRealDoubleArrayGyro.length];

            for (int c = 0; c < classifyRawDataDoubleArrayAcc.length; c++) {

                classifyImagDoubleArrayAcc[c] = 0;
                classifyImagDoubleArrayGyro[c] = 0;
            }

            //Calculate FFT for recorded data module
            FFT fft = new FFT(classifyRealDoubleArrayAcc.length);

            //-----------ACCELEROMETER ------------
            fft.fft(classifyRealDoubleArrayAcc, classifyImagDoubleArrayAcc);
            double[] classifyFFTMagDoubleArrayAcc = new double[classifyRealDoubleArrayAcc.length];

            Log.d("", "FFT real: " + Arrays.toString(classifyRealDoubleArrayAcc));
            Log.d("", "FFT imaginary: " + Arrays.toString(classifyImagDoubleArrayAcc));

            //Calculate magnitude of FFT data
            for (int i = 0; i < classifyRealDoubleArrayAcc.length; i++) {
                classifyFFTMagDoubleArrayAcc[i] = Math.sqrt(Math.pow(classifyRealDoubleArrayAcc[i], 2) + Math.pow(classifyImagDoubleArrayAcc[i], 2));
            }

            Log.d("", "FFT Magnitude: " + Arrays.toString(classifyFFTMagDoubleArrayAcc));

            //-----------GYROSCOPE ------------
            fft.fft(classifyRealDoubleArrayGyro, classifyImagDoubleArrayGyro);
            double[] classifyFFTMagDoubleArrayGyro = new double[classifyRealDoubleArrayGyro.length];

            Log.d("", "FFT real: " + Arrays.toString(classifyRealDoubleArrayGyro));
            Log.d("", "FFT imaginary: " + Arrays.toString(classifyImagDoubleArrayGyro));

            //Calculate magnitude of FFT data
            for (int i = 0; i < classifyRealDoubleArrayGyro.length; i++) {
                classifyFFTMagDoubleArrayGyro[i] = Math.sqrt(Math.pow(classifyRealDoubleArrayGyro[i], 2) + Math.pow(classifyImagDoubleArrayGyro[i], 2));
            }

            Log.d("", "FFT Magnitude: " + Arrays.toString(classifyFFTMagDoubleArrayGyro));

            double[] classifyAccGyro = new double[classifyFFTMagDoubleArrayAcc.length + classifyFFTMagDoubleArrayGyro.length];// NEW ATTRIBUTE HERE!!!!!

            for (int n = 0; n < classifyAccGyro.length/2; n++){
                classifyAccGyro[n] = classifyFFTMagDoubleArrayAcc[n];
            }

            int c = 0;
            for (int n = classifyAccGyro.length/2; n < classifyAccGyro.length; n++){
                classifyAccGyro[n] = classifyFFTMagDoubleArrayGyro[c];
                c++;
            }
            Log.d("", "FFT Magnitude Acc+Gyro: " + Arrays.toString(classifyAccGyro));

            // Acc - classifyFFTMagDoubleArrayAcc
            // Gyro - classifyFFTMagDoubleArrayGyro
            // Acc+Gyro - classifyAccGyro

            //Initialise classification
            Classification classification = new Classification();
            double classifictaionResult = classification.runClassification(classifyAccGyro);

            Log.d("", "classifictaionResult: " + classifictaionResult);

            if (classifictaionResult == 0.0) resultText.setText("bieganie");
            if (classifictaionResult == 1.0) resultText.setText("chodzenie");
            if (classifictaionResult == 2.0) resultText.setText("rower");
            if (classifictaionResult == 3.0) resultText.setText("stanie");

            resultText.setTextColor(Color.parseColor("#673AB7"));

            //Stop recording when given number of samples inserted
            flagClassify = false;
            samples_acc++;
            samples_gyro++;

        }
        else {
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