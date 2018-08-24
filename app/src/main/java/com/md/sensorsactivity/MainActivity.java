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

    private TextView xText, yText, zText;
    private TextView xText2, yText2, zText2;
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
    private int classify_samples = 128;
    Date currentTime;
    String dateTime;
    long startTime;
    long durationTime;
    String label;
    //String samples_total_string;
    private double x, y, z, x2, y2, z2;

    // FFT

    //double[] classifyImagDoubleArrayAcc = new double[classify_samples];



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

        /*
        // Accelerometer's views
        xText = (TextView) findViewById(R.id.xText);
        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);

        // Gyroscope's views
        xText2 = (TextView) findViewById(R.id.xText2);
        yText2 = (TextView) findViewById(R.id.yText2);
        zText2 = (TextView) findViewById(R.id.zText2);
        */

        NameOfPackage = getApplicationContext().getPackageName();
        NameOfDB = "SensorsData.db";

        // Create database
        final DatabaseHelper db = new DatabaseHelper(this);
        dbRef = db;


        // Fill FFT table with data
        InsertFFT insertFFT = new InsertFFT();
        String labelFFT = "chodzenie gyro";

        //double[] rawDataDoubleArray = {14.71520447, 9.065795905, 9.012060748, 10.98198373, 5.088077554, 14.26114393, 11.33780818, 4.4979188, 17.87625582, 7.516173633, 10.064891, 11.13484833, 4.122621803, 13.48672177, 11.0480056, 6.504156539, 14.24147349, 7.920432138, 11.67597949, 11.26563415, 5.889116683, 14.25254151, 10.72931664, 9.31067768, 10.70080638, 5.655417906, 14.02602936, 11.79788587, 4.529037271, 13.45167911, 9.321138442, 9.384795695, 10.96847255, 5.074962988, 12.72066988, 11.00025102, 7.628561095, 15.21797517, 8.194077264, 11.90421045, 12.38228628, 5.246277126, 14.15049009, 11.5616528, 4.710314032, 13.835769, 9.556261067, 11.07855155, 11.36796626, 3.331440614, 15.67381281, 11.2985778, 5.684180149, 13.05560576, 9.958319141, 9.944955939, 13.14601709, 5.844022992, 11.87796037, 11.70779387, 3.550993177, 14.21794287, 11.61323111, 6.492729713, 14.75928743, 6.682586817, 10.47951716, 12.28273883, 5.504375667, 15.64019309, 10.44607479, 4.425306639, 16.19673162, 9.080444346, 9.223646548, 11.26020449, 5.678074129, 12.1906705, 13.13270357, 5.080018367, 13.63786916, 10.568644, 6.809428171, 13.16650096, 8.761499487, 10.21997766, 10.89818861, 4.962808364, 12.54431558, 10.15740664, 5.0806923, 13.74881997, 11.08495676, 5.244104447, 16.15541712, 9.586254324, 10.82693114, 10.62361557, 4.366314391, 11.56118932, 11.00154423, 5.540598902, 13.77057914, 10.53702869, 9.806735532, 12.60840178, 6.455268529, 13.20048267, 10.89133166, 6.600553807, 14.99394436, 8.002139563, 9.397035478, 11.96615112, 5.546821163, 12.14224439, 10.56243182, 5.19678874, 13.75265234, 11.03103394, 6.019502039, 13.73368843, 8.986423871, 9.664924622, 12.11840493, 5.372677413, 13.23339797, 12.59342582};
        //insertFFT.fftToTable(rawDataDoubleArray, labelFFT);


        //---------TEST CLASSIFICATION--------
        /*
        double [] dataToClassify = {1307.250836098, 271.13054693911135, 389.00925152373713, 232.02573539046972, 6.683931898550163, 243.5489140518336, 275.3578999487653, 371.6130349849456, 130.5835673467289, 41.920720207395966, 52.92473796990004, 163.44116644006746, 359.51780954198256, 281.89974176772574, 131.19892206949226, 285.05785679484893, 719.2200718635489, 811.6151321726753, 124.10102393910815, 84.06803207720108, 330.2305271252538, 496.70745647237806, 403.090106497379, 25.370017348136237, 452.43498902630006, 706.8525336995606, 293.58467547976466, 145.85851135766896, 516.3468938625489, 441.241801458378, 51.534096446627956, 322.2106865045468, 477.2067672528414, 466.7785150558131, 176.08591215495144, 225.2625103426343, 459.5408883769343, 487.05747202215247, 109.4678868081686, 391.5890907762998, 380.1258695025525, 14.219747871231988, 269.81108284962846, 722.7157725569227, 565.8291106737055, 222.75170139335756, 464.55122853767875, 381.565153421005, 312.9763879634412, 124.71208757160844, 260.30195039978474, 563.9210847437711, 640.2271499806048, 34.55602095135572, 533.0592701330755, 670.4373705001874, 127.5920486573125, 470.34646353883966, 594.8874380044946, 284.76892137818436, 56.67866419052553, 176.09393234720125, 614.6764682305298, 694.4176474351793, 29.420617356006503, 690.4650303124508, 623.6434086358547, 148.37953137211136, 46.611334690588066, 343.2697299629756, 593.042230182713, 495.55320160495296, 141.55904348001957, 642.1088403480295, 527.2214927682288, 20.401702027793963, 608.8207040562468, 589.9160616227438, 184.63949910457538, 95.61312166677203, 225.8695768143393, 552.4873419473652, 724.168204780697, 89.09361085015404, 616.6733303637587, 681.9209121721088, 268.74825900933064, 31.316011064570397, 384.8730029817455, 435.3084594324374, 126.13665473348041, 441.3201448474473, 336.4447796076291, 185.4299443998813, 171.15447589048316, 406.21063684982846, 523.4918528972161, 307.8139303474284, 67.76457705369069, 434.3870027433146, 509.50712770667604, 156.09358944455911, 293.78788374425335, 711.7742368613966, 449.7956359584618, 30.040889228212826, 390.43829302782444, 507.7950655667716, 302.8199342817794, 89.01918310288258, 128.61800421180703, 809.6286396604214, 727.2597950457308, 281.04503165385364, 117.17278184473099, 282.4604916666121, 363.51815809697337, 157.49409280372396, 60.92365417847305, 36.21397407290429, 134.68535525204678, 375.0133650408735, 268.99336636349454, 255.92451561184592, 6.1518001737522585, 214.116187310762, 402.6390521984887, 259.08593488329217};
        //Initialise classification
        Classification classification = new Classification();
        double classifictaionResult = classification.runClassification(dataToClassify);

        Log.d("", "classifictaionResult: " + classifictaionResult);
        */


        //Delete all rows from table on click
        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.deleteAllRows();
                Log.d("", "All data deleted.");
            }
        });

        //Initialize EditText for label
        textLabel = (EditText) findViewById(R.id.editTextLabel);

        //Initialize EditText for samples
        textSamples = (EditText) findViewById(R.id.editTextSamples);

        //Status text view
        statusText = (TextView) findViewById(R.id.statusTextView);
        statusText.setTextColor(Color.GRAY);

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

                statusText.setText("Recording...");
                statusText.setTextColor(Color.GREEN);

            }
        });

        //Start recording sensor data on click
        Button classifyButton = (Button) findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resultText.setText("Classifying...");
                resultText.setTextColor(Color.GRAY);

                flagClassify = true;
                samples_acc = 0;
                samples_gyro = 0;
            }
        });

        //Check if database exists
        File dbtest = new File("/data/data/" + getNameOfPackage() + "/databases/" + NameOfDB);
        if (dbtest.exists()) {
            Log.d("", "Adding to database is possible!");
        } else {
            Log.d("", "Database don't exists.");
        }
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
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_LONG).show();

            //Stop recording when given number of samples inserted
            flag = false;
            samples_acc++;
            samples_gyro++;

            statusText.setText("Not recording");
            statusText.setTextColor(Color.GRAY);

        }
        else {
            //Not in use
        }


        //------------------------------------------------------------------------------------------------------------------------------------------

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
            double [] classifyRealDoubleArrayAcc = classifyRawDataDoubleArrayAcc; // compute FFT but from the copied Array
            double [] classifyImagDoubleArrayAcc = new double[classifyRealDoubleArrayAcc.length];

            double [] classifyRealDoubleArrayGyro = classifyRawDataDoubleArrayGyro; // compute FFT but from the copied Array
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
            double classifictaionResult = classification.runClassification(classifyFFTMagDoubleArrayAcc);

            Log.d("", "classifictaionResult: " + classifictaionResult);

            if (classifictaionResult == 1.0) resultText.setText("stanie");
            if (classifictaionResult == 0.0) resultText.setText("chodzenie");
            resultText.setTextColor(Color.BLUE);

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