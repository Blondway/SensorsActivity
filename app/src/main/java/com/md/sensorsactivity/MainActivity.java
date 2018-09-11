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
    private int classify_samples = 32;
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
        String labelFFT = "stanie gyro";

        //double[] rawDataDoubleArray = {14.71520447, 9.065795905, 9.012060748, 10.98198373, 5.088077554, 14.26114393, 11.33780818, 4.4979188, 17.87625582, 7.516173633, 10.064891, 11.13484833, 4.122621803, 13.48672177, 11.0480056, 6.504156539, 14.24147349, 7.920432138, 11.67597949, 11.26563415, 5.889116683, 14.25254151, 10.72931664, 9.31067768, 10.70080638, 5.655417906, 14.02602936, 11.79788587, 4.529037271, 13.45167911, 9.321138442, 9.384795695, 10.96847255, 5.074962988, 12.72066988, 11.00025102, 7.628561095, 15.21797517, 8.194077264, 11.90421045, 12.38228628, 5.246277126, 14.15049009, 11.5616528, 4.710314032, 13.835769, 9.556261067, 11.07855155, 11.36796626, 3.331440614, 15.67381281, 11.2985778, 5.684180149, 13.05560576, 9.958319141, 9.944955939, 13.14601709, 5.844022992, 11.87796037, 11.70779387, 3.550993177, 14.21794287, 11.61323111, 6.492729713, 14.75928743, 6.682586817, 10.47951716, 12.28273883, 5.504375667, 15.64019309, 10.44607479, 4.425306639, 16.19673162, 9.080444346, 9.223646548, 11.26020449, 5.678074129, 12.1906705, 13.13270357, 5.080018367, 13.63786916, 10.568644, 6.809428171, 13.16650096, 8.761499487, 10.21997766, 10.89818861, 4.962808364, 12.54431558, 10.15740664, 5.0806923, 13.74881997, 11.08495676, 5.244104447, 16.15541712, 9.586254324, 10.82693114, 10.62361557, 4.366314391, 11.56118932, 11.00154423, 5.540598902, 13.77057914, 10.53702869, 9.806735532, 12.60840178, 6.455268529, 13.20048267, 10.89133166, 6.600553807, 14.99394436, 8.002139563, 9.397035478, 11.96615112, 5.546821163, 12.14224439, 10.56243182, 5.19678874, 13.75265234, 11.03103394, 6.019502039, 13.73368843, 8.986423871, 9.664924622, 12.11840493, 5.372677413, 13.23339797, 12.59342582};
        //insertFFT.fftToTable(rawDataDoubleArray, labelFFT);


        //---------TEST CLASSIFICATION--------
/*
        double [] dataToClassify = {1282.774740806, 0.37121406164102927, 0.18327536978704131, 0.556491230570702, 0.8137316982116327, 0.31785906992025553, 0.7432950547522484, 0.36264641245272516, 0.4859254613245925, 0.07671695934119643, 0.0673174058770426, 0.1971369715991798, 0.2287626041850965, 0.16031746833409577, 0.3947967180458017, 0.11371071832785343, 0.2577743921324542, 0.10296008992541204, 0.1788395663509208, 0.3592839700475738, 0.1346681107151507, 0.24373972970035157, 0.3932703710568306, 0.13280091542372732, 0.2941671397433654, 0.1769388849072104, 0.4075733434038994, 0.2024467388039463, 0.18423462204415894, 0.2558943773019464, 0.19550050853438852, 0.4599848286769485, 0.5118310474244876, 0.2535703640653277, 0.2994541559415125, 0.059195533709843734, 0.30947414254195826, 0.305011165043807, 0.35864947288874083, 0.3930538397788575, 0.2942553819199088, 0.15203410068861567, 0.10184516619992745, 0.13090359509585406, 0.13458288554315948, 0.4483855205400273, 0.26946220120126885, 0.15054300692504777, 0.168450320839401, 0.3460571700001958, 0.07277318350412265, 0.2560744760319688, 0.5069862344352242, 0.30754531985961187, 0.3682587648499303, 0.2184466665669631, 0.13468706764190916, 0.04292456314976041, 0.19289013287113238, 0.2766972986514106, 0.5959028217748755, 0.3904133010951912, 0.5693006061985324, 0.24856329059982252, 1.0416639479999503, 0.24856329059982243, 0.5693006061985323, 0.39041330109519123, 0.5959028217748755, 0.2766972986514105, 0.1928901328711324, 0.04292456314976038, 0.13468706764190913, 0.21844666656696313, 0.36825876484993025, 0.3075453198596118, 0.5069862344352243, 0.25607447603196887, 0.07277318350412267, 0.3460571700001958, 0.16845032083940104, 0.1505430069250478, 0.2694622012012688, 0.4483855205400272, 0.13458288554315948, 0.13090359509585414, 0.1018451661999274, 0.15203410068861561, 0.2942553819199088, 0.39305383977885744, 0.3586494728887409, 0.30501116504380693, 0.3094741425419583, 0.05919553370984375, 0.2994541559415124, 0.2535703640653277, 0.5118310474244876, 0.45998482867694845, 0.19550050853438847, 0.2558943773019464, 0.18423462204415886, 0.20244673880394637, 0.4075733434038994, 0.1769388849072104, 0.2941671397433654, 0.13280091542372727, 0.3932703710568306, 0.24373972970035168, 0.13466811071515067, 0.35928397004757373, 0.17883956635092088, 0.10296008992541218, 0.2577743921324542, 0.11371071832785351, 0.3947967180458016, 0.16031746833409574, 0.2287626041850965, 0.1971369715991798, 0.06731740587704259, 0.07671695934119643, 0.4859254613245926, 0.3626464124527252, 0.7432950547522484, 0.3178590699202556, 0.8137316982116329, 0.556491230570702, 0.1832753697870414, 0.37121406164102927};

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