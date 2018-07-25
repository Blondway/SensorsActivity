package com.md.sensorsactivity;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private TextView xText2, yText2, zText2;
    private TextView xText3, yText3, zText3, cosText3, accuracyText3;
    private TextView statusText, textSamples;
    private Sensor accSensor; // Accelerometer
    private Sensor gyroSensor; // Gyroscope
    private Sensor rotationSensor; // Rotation vector
    private SensorManager SM;

    private static String NameOfPackage;
    private static String NameOfDB;
    private static DatabaseHelper dbRef;

    private boolean flag;
    private boolean waitFlag;
    private int samples_acc, samples_gyro, samples_rotation;
    private int samples_total = 2048;
    Date currentTime;
    String dateTime;
    long startTime;
    long durationTime;
    long startWaiting;
    long stopWaiting;
    String label;
    //String samples_total_string;
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

        /*
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
        */

        //Status text view
        statusText = (TextView) findViewById(R.id.statusTextView);

        NameOfPackage = getApplicationContext().getPackageName();
        NameOfDB = "SensorsData.db";

        // Create database
        final DatabaseHelper db = new DatabaseHelper(this);
        dbRef = db;


        // Fill FFT table with data
        InsertFFT insertFFT = new InsertFFT();
        String labelFFT = "chodzenie";

        //double[] rawDataDoubleArray = {14.71520447, 9.065795905, 9.012060748, 10.98198373, 5.088077554, 14.26114393, 11.33780818, 4.4979188, 17.87625582, 7.516173633, 10.064891, 11.13484833, 4.122621803, 13.48672177, 11.0480056, 6.504156539, 14.24147349, 7.920432138, 11.67597949, 11.26563415, 5.889116683, 14.25254151, 10.72931664, 9.31067768, 10.70080638, 5.655417906, 14.02602936, 11.79788587, 4.529037271, 13.45167911, 9.321138442, 9.384795695, 10.96847255, 5.074962988, 12.72066988, 11.00025102, 7.628561095, 15.21797517, 8.194077264, 11.90421045, 12.38228628, 5.246277126, 14.15049009, 11.5616528, 4.710314032, 13.835769, 9.556261067, 11.07855155, 11.36796626, 3.331440614, 15.67381281, 11.2985778, 5.684180149, 13.05560576, 9.958319141, 9.944955939, 13.14601709, 5.844022992, 11.87796037, 11.70779387, 3.550993177, 14.21794287, 11.61323111, 6.492729713, 14.75928743, 6.682586817, 10.47951716, 12.28273883, 5.504375667, 15.64019309, 10.44607479, 4.425306639, 16.19673162, 9.080444346, 9.223646548, 11.26020449, 5.678074129, 12.1906705, 13.13270357, 5.080018367, 13.63786916, 10.568644, 6.809428171, 13.16650096, 8.761499487, 10.21997766, 10.89818861, 4.962808364, 12.54431558, 10.15740664, 5.0806923, 13.74881997, 11.08495676, 5.244104447, 16.15541712, 9.586254324, 10.82693114, 10.62361557, 4.366314391, 11.56118932, 11.00154423, 5.540598902, 13.77057914, 10.53702869, 9.806735532, 12.60840178, 6.455268529, 13.20048267, 10.89133166, 6.600553807, 14.99394436, 8.002139563, 9.397035478, 11.96615112, 5.546821163, 12.14224439, 10.56243182, 5.19678874, 13.75265234, 11.03103394, 6.019502039, 13.73368843, 8.986423871, 9.664924622, 12.11840493, 5.372677413, 13.23339797, 12.59342582};
        //insertFFT.fftToTable(rawDataDoubleArray, labelFFT);

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

        //Initialize EditText for samples
        textSamples = (EditText) findViewById(R.id.editTextSamples);

        //Status text view
        statusText = (TextView) findViewById(R.id.statusTextView);
        statusText.setTextColor(Color.GRAY);

        //Start recording sensor data on click
        Button startButton = (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get label from EditText
                label = textLabel.getText().toString();

                //Get total number of samples from EditText
                //samples_total_string = textSamples.getText().toString();
                //samples_total = Integer.parseInt(samples_total_string);
                samples_total = Integer.parseInt(textSamples.getText().toString());

                //Create empty table (for imaginary FFT input)
                //for (int i = 0; i < samples_total; i++) imagDoubleArray[i] = 0;


                //statusText.setText("Wait");
                //statusText.setTextColor(Color.RED);


                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

                flag = true; //flag set to true = record data

                samples_acc = 0;
                samples_gyro = 0;
                samples_rotation = 0;

                startTime = System.currentTimeMillis();

                statusText.setText("Recording...");
                statusText.setTextColor(Color.GREEN);

            }
        });
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


        //Insert given number of sensor data samples into database
        if (samples_acc < samples_total || samples_gyro < samples_total || samples_rotation < samples_total)  {
            if (flag == true) {

                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

                //Add data to array
                //rawDataDoubleArray[samples] = module;
                //Log.d("", "Inserted:" + rawDoubleArray[samples]);

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

                if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR && samples_rotation < samples_total) {
                    rotationDoubleArrayX[samples_rotation] = x3;
                    rotationDoubleArrayY[samples_rotation] = y3;
                    rotationDoubleArrayZ[samples_rotation] = z3;
                    rotationDoubleArrayCos[samples_rotation] = cos3;
                    rotationDoubleArrayAccuracy[samples_rotation] = accuracy3;

                    samples_rotation++;
                }

            } //end if flag is true
            else {
                //Log.d("", "samples < 10 but Flag is false - Data NOT inserted");
            }
        } else if (samples_acc == samples_total && samples_gyro == samples_total && samples_rotation == samples_total){
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

            //Insert x,y,z values for sensors
            dbRef.insertData(dateTime, accDoubleArrayX, accDoubleArrayY, accDoubleArrayZ,
                    gyroDoubleArrayX, gyroDoubleArrayY, gyroDoubleArrayZ,
                    rotationDoubleArrayX, rotationDoubleArrayY, rotationDoubleArrayZ, rotationDoubleArrayCos, rotationDoubleArrayAccuracy,
                    durationTime, label);

            Log.d("", "Data inserted!");
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_LONG).show();

            //Stop recording when given number of samples inserted
            flag = false;
            samples_acc++;
            samples_gyro++;
            samples_rotation++;

            statusText.setText("Not recording");
            statusText.setTextColor(Color.GRAY);

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