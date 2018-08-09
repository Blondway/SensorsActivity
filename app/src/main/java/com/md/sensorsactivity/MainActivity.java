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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private TextView xText2, yText2, zText2;
    private TextView xText3, yText3, zText3, cosText3, accuracyText3;
    private TextView statusText, textSamples, resultText;
    private Sensor accSensor; // Accelerometer
    private Sensor gyroSensor; // Gyroscope
    private Sensor rotationSensor; // Rotation vector
    private SensorManager SM;

    private static String NameOfPackage;
    private static String NameOfDB;
    private static DatabaseHelper dbRef;

    private boolean flag;
    private boolean flagClassify;
    private boolean waitFlag;
    private int samples_acc, samples_gyro, samples_rotation;
    private int samples_total = 2048;
    private int classify_samples = 128;
    Date currentTime;
    String dateTime;
    long startTime;
    long durationTime;
    long startWaiting;
    long stopWaiting;
    String label;
    //String samples_total_string;
    private double x, y, z, x2, y2, z2, x3, y3, z3, cos3, accuracy3;

    // FFT
    double[] classifyRealDoubleArray = new double[classify_samples];
    double[] classifyImagDoubleArray = new double[classify_samples];
    double[] classifyRawDataDoubleArray = new double[classify_samples];
    private double accModule;


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
        flagClassify = false;

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

        NameOfPackage = getApplicationContext().getPackageName();
        NameOfDB = "SensorsData.db";

        // Create database
        final DatabaseHelper db = new DatabaseHelper(this);
        dbRef = db;



        /*
        // Fill FFT table with data
        InsertFFT insertFFT = new InsertFFT();
        String labelFFT = "chodzenie";

        //double[] rawDataDoubleArray = {14.71520447, 9.065795905, 9.012060748, 10.98198373, 5.088077554, 14.26114393, 11.33780818, 4.4979188, 17.87625582, 7.516173633, 10.064891, 11.13484833, 4.122621803, 13.48672177, 11.0480056, 6.504156539, 14.24147349, 7.920432138, 11.67597949, 11.26563415, 5.889116683, 14.25254151, 10.72931664, 9.31067768, 10.70080638, 5.655417906, 14.02602936, 11.79788587, 4.529037271, 13.45167911, 9.321138442, 9.384795695, 10.96847255, 5.074962988, 12.72066988, 11.00025102, 7.628561095, 15.21797517, 8.194077264, 11.90421045, 12.38228628, 5.246277126, 14.15049009, 11.5616528, 4.710314032, 13.835769, 9.556261067, 11.07855155, 11.36796626, 3.331440614, 15.67381281, 11.2985778, 5.684180149, 13.05560576, 9.958319141, 9.944955939, 13.14601709, 5.844022992, 11.87796037, 11.70779387, 3.550993177, 14.21794287, 11.61323111, 6.492729713, 14.75928743, 6.682586817, 10.47951716, 12.28273883, 5.504375667, 15.64019309, 10.44607479, 4.425306639, 16.19673162, 9.080444346, 9.223646548, 11.26020449, 5.678074129, 12.1906705, 13.13270357, 5.080018367, 13.63786916, 10.568644, 6.809428171, 13.16650096, 8.761499487, 10.21997766, 10.89818861, 4.962808364, 12.54431558, 10.15740664, 5.0806923, 13.74881997, 11.08495676, 5.244104447, 16.15541712, 9.586254324, 10.82693114, 10.62361557, 4.366314391, 11.56118932, 11.00154423, 5.540598902, 13.77057914, 10.53702869, 9.806735532, 12.60840178, 6.455268529, 13.20048267, 10.89133166, 6.600553807, 14.99394436, 8.002139563, 9.397035478, 11.96615112, 5.546821163, 12.14224439, 10.56243182, 5.19678874, 13.75265234, 11.03103394, 6.019502039, 13.73368843, 8.986423871, 9.664924622, 12.11840493, 5.372677413, 13.23339797, 12.59342582};
        //insertFFT.fftToTable(rawDataDoubleArray, labelFFT);

        */

        /*
        ---------------------------------BEGINNING OF CLASSIFICATION------------------------------
        Code to test classification (hardcoded example)
         */
        /*
        double result = -1;
        Instance inst_co;

        try {

            //Create list of attributes
            ArrayList<Attribute> attributeList = new ArrayList<Attribute>(128);

            //Set attributes
            Attribute data1 = new Attribute("data1");
            Attribute data2 = new Attribute("data2");
            Attribute data3 = new Attribute("data3");
            Attribute data4 = new Attribute("data4");
            Attribute data5 = new Attribute("data5");
            Attribute data6 = new Attribute("data6");
            Attribute data7 = new Attribute("data7");
            Attribute data8 = new Attribute("data8");
            Attribute data9 = new Attribute("data9");
            Attribute data10 = new Attribute("data10");
            Attribute data11 = new Attribute("data11");
            Attribute data12 = new Attribute("data12");
            Attribute data13 = new Attribute("data13");
            Attribute data14 = new Attribute("data14");
            Attribute data15 = new Attribute("data15");
            Attribute data16 = new Attribute("data16");
            Attribute data17 = new Attribute("data17");
            Attribute data18 = new Attribute("data18");
            Attribute data19 = new Attribute("data19");
            Attribute data20 = new Attribute("data20");
            Attribute data21 = new Attribute("data21");
            Attribute data22 = new Attribute("data22");
            Attribute data23 = new Attribute("data23");
            Attribute data24 = new Attribute("data24");
            Attribute data25 = new Attribute("data25");
            Attribute data26 = new Attribute("data26");
            Attribute data27 = new Attribute("data27");
            Attribute data28 = new Attribute("data28");
            Attribute data29 = new Attribute("data29");
            Attribute data30 = new Attribute("data30");
            Attribute data31 = new Attribute("data31");
            Attribute data32 = new Attribute("data32");
            Attribute data33 = new Attribute("data33");
            Attribute data34 = new Attribute("data34");
            Attribute data35 = new Attribute("data35");
            Attribute data36 = new Attribute("data36");
            Attribute data37 = new Attribute("data37");
            Attribute data38 = new Attribute("data38");
            Attribute data39 = new Attribute("data39");
            Attribute data40 = new Attribute("data40");
            Attribute data41 = new Attribute("data41");
            Attribute data42 = new Attribute("data42");
            Attribute data43 = new Attribute("data43");
            Attribute data44 = new Attribute("data44");
            Attribute data45 = new Attribute("data45");
            Attribute data46 = new Attribute("data46");
            Attribute data47 = new Attribute("data47");
            Attribute data48 = new Attribute("data48");
            Attribute data49 = new Attribute("data49");
            Attribute data50 = new Attribute("data50");
            Attribute data51 = new Attribute("data51");
            Attribute data52 = new Attribute("data52");
            Attribute data53 = new Attribute("data53");
            Attribute data54 = new Attribute("data54");
            Attribute data55 = new Attribute("data55");
            Attribute data56 = new Attribute("data56");
            Attribute data57 = new Attribute("data57");
            Attribute data58 = new Attribute("data58");
            Attribute data59 = new Attribute("data59");
            Attribute data60 = new Attribute("data60");
            Attribute data61 = new Attribute("data61");
            Attribute data62 = new Attribute("data62");
            Attribute data63 = new Attribute("data63");
            Attribute data64 = new Attribute("data64");
            Attribute data65 = new Attribute("data65");
            Attribute data66 = new Attribute("data66");
            Attribute data67 = new Attribute("data67");
            Attribute data68 = new Attribute("data68");
            Attribute data69 = new Attribute("data69");
            Attribute data70 = new Attribute("data70");
            Attribute data71 = new Attribute("data71");
            Attribute data72 = new Attribute("data72");
            Attribute data73 = new Attribute("data73");
            Attribute data74 = new Attribute("data74");
            Attribute data75 = new Attribute("data75");
            Attribute data76 = new Attribute("data76");
            Attribute data77 = new Attribute("data77");
            Attribute data78 = new Attribute("data78");
            Attribute data79 = new Attribute("data79");
            Attribute data80 = new Attribute("data80");
            Attribute data81 = new Attribute("data81");
            Attribute data82 = new Attribute("data82");
            Attribute data83 = new Attribute("data83");
            Attribute data84 = new Attribute("data84");
            Attribute data85 = new Attribute("data85");
            Attribute data86 = new Attribute("data86");
            Attribute data87 = new Attribute("data87");
            Attribute data88 = new Attribute("data88");
            Attribute data89 = new Attribute("data89");
            Attribute data90 = new Attribute("data90");
            Attribute data91 = new Attribute("data91");
            Attribute data92 = new Attribute("data92");
            Attribute data93 = new Attribute("data93");
            Attribute data94 = new Attribute("data94");
            Attribute data95 = new Attribute("data95");
            Attribute data96 = new Attribute("data96");
            Attribute data97 = new Attribute("data97");
            Attribute data98 = new Attribute("data98");
            Attribute data99 = new Attribute("data99");
            Attribute data100 = new Attribute("data100");
            Attribute data101 = new Attribute("data101");
            Attribute data102 = new Attribute("data102");
            Attribute data103 = new Attribute("data103");
            Attribute data104 = new Attribute("data104");
            Attribute data105 = new Attribute("data105");
            Attribute data106 = new Attribute("data106");
            Attribute data107 = new Attribute("data107");
            Attribute data108 = new Attribute("data108");
            Attribute data109 = new Attribute("data109");
            Attribute data110 = new Attribute("data110");
            Attribute data111 = new Attribute("data111");
            Attribute data112 = new Attribute("data112");
            Attribute data113 = new Attribute("data113");
            Attribute data114 = new Attribute("data114");
            Attribute data115 = new Attribute("data115");
            Attribute data116 = new Attribute("data116");
            Attribute data117 = new Attribute("data117");
            Attribute data118 = new Attribute("data118");
            Attribute data119 = new Attribute("data119");
            Attribute data120 = new Attribute("data120");
            Attribute data121 = new Attribute("data121");
            Attribute data122 = new Attribute("data122");
            Attribute data123 = new Attribute("data123");
            Attribute data124 = new Attribute("data124");
            Attribute data125 = new Attribute("data125");
            Attribute data126 = new Attribute("data126");
            Attribute data127 = new Attribute("data127");
            Attribute data128 = new Attribute("data128");

            //Class attribute
            ArrayList<String> classVal = new ArrayList<String>();
            classVal.add("chodzenie");
            classVal.add("stanie");

            //Add attributes to list
            attributeList.add(data1);
            attributeList.add(data2);
            attributeList.add(data3);
            attributeList.add(data4);
            attributeList.add(data5);
            attributeList.add(data6);
            attributeList.add(data7);
            attributeList.add(data8);
            attributeList.add(data9);
            attributeList.add(data10);
            attributeList.add(data11);
            attributeList.add(data12);
            attributeList.add(data13);
            attributeList.add(data14);
            attributeList.add(data15);
            attributeList.add(data16);
            attributeList.add(data17);
            attributeList.add(data18);
            attributeList.add(data19);
            attributeList.add(data20);
            attributeList.add(data21);
            attributeList.add(data22);
            attributeList.add(data23);
            attributeList.add(data24);
            attributeList.add(data25);
            attributeList.add(data26);
            attributeList.add(data27);
            attributeList.add(data28);
            attributeList.add(data29);
            attributeList.add(data30);
            attributeList.add(data31);
            attributeList.add(data32);
            attributeList.add(data33);
            attributeList.add(data34);
            attributeList.add(data35);
            attributeList.add(data36);
            attributeList.add(data37);
            attributeList.add(data38);
            attributeList.add(data39);
            attributeList.add(data40);
            attributeList.add(data41);
            attributeList.add(data42);
            attributeList.add(data43);
            attributeList.add(data44);
            attributeList.add(data45);
            attributeList.add(data46);
            attributeList.add(data47);
            attributeList.add(data48);
            attributeList.add(data49);
            attributeList.add(data50);
            attributeList.add(data51);
            attributeList.add(data52);
            attributeList.add(data53);
            attributeList.add(data54);
            attributeList.add(data55);
            attributeList.add(data56);
            attributeList.add(data57);
            attributeList.add(data58);
            attributeList.add(data59);
            attributeList.add(data60);
            attributeList.add(data61);
            attributeList.add(data62);
            attributeList.add(data63);
            attributeList.add(data64);
            attributeList.add(data65);
            attributeList.add(data66);
            attributeList.add(data67);
            attributeList.add(data68);
            attributeList.add(data69);
            attributeList.add(data70);
            attributeList.add(data71);
            attributeList.add(data72);
            attributeList.add(data73);
            attributeList.add(data74);
            attributeList.add(data75);
            attributeList.add(data76);
            attributeList.add(data77);
            attributeList.add(data78);
            attributeList.add(data79);
            attributeList.add(data80);
            attributeList.add(data81);
            attributeList.add(data82);
            attributeList.add(data83);
            attributeList.add(data84);
            attributeList.add(data85);
            attributeList.add(data86);
            attributeList.add(data87);
            attributeList.add(data88);
            attributeList.add(data89);
            attributeList.add(data90);
            attributeList.add(data91);
            attributeList.add(data92);
            attributeList.add(data93);
            attributeList.add(data94);
            attributeList.add(data95);
            attributeList.add(data96);
            attributeList.add(data97);
            attributeList.add(data98);
            attributeList.add(data99);
            attributeList.add(data100);
            attributeList.add(data101);
            attributeList.add(data102);
            attributeList.add(data103);
            attributeList.add(data104);
            attributeList.add(data105);
            attributeList.add(data106);
            attributeList.add(data107);
            attributeList.add(data108);
            attributeList.add(data109);
            attributeList.add(data110);
            attributeList.add(data111);
            attributeList.add(data112);
            attributeList.add(data113);
            attributeList.add(data114);
            attributeList.add(data115);
            attributeList.add(data116);
            attributeList.add(data117);
            attributeList.add(data118);
            attributeList.add(data119);
            attributeList.add(data120);
            attributeList.add(data121);
            attributeList.add(data122);
            attributeList.add(data123);
            attributeList.add(data124);
            attributeList.add(data125);
            attributeList.add(data126);
            attributeList.add(data127);
            attributeList.add(data128);

            attributeList.add(new Attribute("@@class@@",classVal));

            // Create new dataset
            Instances data = new Instances("TestInstances",attributeList,0);

            // Create new instance
            inst_co = new DenseInstance(data.numAttributes());

            // Set instance's values for attributes
            inst_co.setValue(data1, 1307.250836098);
            inst_co.setValue(data2, 271.13054693911135);
            inst_co.setValue(data3, 389.00925152373713);
            inst_co.setValue(data4, 232.02573539046972);
            inst_co.setValue(data5, 6.683931898550163);
            inst_co.setValue(data6, 243.5489140518336);
            inst_co.setValue(data7, 275.3578999487653);
            inst_co.setValue(data8, 371.6130349849456);
            inst_co.setValue(data9, 130.5835673467289);
            inst_co.setValue(data10, 41.920720207395966);
            inst_co.setValue(data11, 52.92473796990004);
            inst_co.setValue(data12, 163.44116644006746);
            inst_co.setValue(data13, 359.51780954198256);
            inst_co.setValue(data14, 281.89974176772574);
            inst_co.setValue(data15, 131.19892206949226);
            inst_co.setValue(data16, 285.05785679484893);
            inst_co.setValue(data17, 719.2200718635489);
            inst_co.setValue(data18, 811.6151321726753);
            inst_co.setValue(data19, 124.10102393910815);
            inst_co.setValue(data20, 84.06803207720108);
            inst_co.setValue(data21, 330.2305271252538);
            inst_co.setValue(data22, 496.70745647237806);
            inst_co.setValue(data23, 403.090106497379);
            inst_co.setValue(data24, 25.370017348136237);
            inst_co.setValue(data25, 452.43498902630006);
            inst_co.setValue(data26, 706.8525336995606);
            inst_co.setValue(data27, 293.58467547976466);
            inst_co.setValue(data28, 145.85851135766896);
            inst_co.setValue(data29, 516.3468938625489);
            inst_co.setValue(data30, 441.241801458378);
            inst_co.setValue(data31, 51.534096446627956);
            inst_co.setValue(data32, 322.2106865045468);
            inst_co.setValue(data33, 477.2067672528414);
            inst_co.setValue(data34, 466.7785150558131);
            inst_co.setValue(data35, 176.08591215495144);
            inst_co.setValue(data36, 225.2625103426343);
            inst_co.setValue(data37, 459.5408883769343);
            inst_co.setValue(data38, 487.05747202215247);
            inst_co.setValue(data39, 109.4678868081686);
            inst_co.setValue(data40, 391.5890907762998);
            inst_co.setValue(data41, 380.1258695025525);
            inst_co.setValue(data42, 14.219747871231988);
            inst_co.setValue(data43, 269.81108284962846);
            inst_co.setValue(data44, 722.7157725569227);
            inst_co.setValue(data45, 565.8291106737055);
            inst_co.setValue(data46, 222.75170139335756);
            inst_co.setValue(data47, 464.55122853767875);
            inst_co.setValue(data48, 381.565153421005);
            inst_co.setValue(data49, 312.9763879634412);
            inst_co.setValue(data50, 124.71208757160844);
            inst_co.setValue(data51, 260.30195039978474);
            inst_co.setValue(data52, 563.9210847437711);
            inst_co.setValue(data53, 640.2271499806048);
            inst_co.setValue(data54, 34.55602095135572);
            inst_co.setValue(data55, 533.0592701330755);
            inst_co.setValue(data56, 670.4373705001874);
            inst_co.setValue(data57, 127.5920486573125);
            inst_co.setValue(data58, 470.34646353883966);
            inst_co.setValue(data59, 594.8874380044946);
            inst_co.setValue(data60, 284.76892137818436);
            inst_co.setValue(data61, 56.67866419052553);
            inst_co.setValue(data62, 176.09393234720125);
            inst_co.setValue(data63, 614.6764682305298);
            inst_co.setValue(data64, 694.4176474351793);
            inst_co.setValue(data65, 29.420617356006503);
            inst_co.setValue(data66, 690.4650303124508);
            inst_co.setValue(data67, 623.6434086358547);
            inst_co.setValue(data68, 148.37953137211136);
            inst_co.setValue(data69, 46.611334690588066);
            inst_co.setValue(data70, 343.2697299629756);
            inst_co.setValue(data71, 593.042230182713);
            inst_co.setValue(data72, 495.55320160495296);
            inst_co.setValue(data73, 141.55904348001957);
            inst_co.setValue(data74, 642.1088403480295);
            inst_co.setValue(data75, 527.2214927682288);
            inst_co.setValue(data76, 20.401702027793963);
            inst_co.setValue(data77, 608.8207040562468);
            inst_co.setValue(data78, 589.9160616227438);
            inst_co.setValue(data79, 184.63949910457538);
            inst_co.setValue(data80, 95.61312166677203);
            inst_co.setValue(data81, 225.8695768143393);
            inst_co.setValue(data82, 552.4873419473652);
            inst_co.setValue(data83, 724.168204780697);
            inst_co.setValue(data84, 89.09361085015404);
            inst_co.setValue(data85, 616.6733303637587);
            inst_co.setValue(data86, 681.9209121721088);
            inst_co.setValue(data87, 268.74825900933064);
            inst_co.setValue(data88, 31.316011064570397);
            inst_co.setValue(data89, 384.8730029817455);
            inst_co.setValue(data90, 435.3084594324374);
            inst_co.setValue(data91, 126.13665473348041);
            inst_co.setValue(data92, 441.3201448474473);
            inst_co.setValue(data93, 336.4447796076291);
            inst_co.setValue(data94, 185.4299443998813);
            inst_co.setValue(data95, 171.15447589048316);
            inst_co.setValue(data96, 406.21063684982846);
            inst_co.setValue(data97, 523.4918528972161);
            inst_co.setValue(data98, 307.8139303474284);
            inst_co.setValue(data99, 67.76457705369069);
            inst_co.setValue(data100, 434.3870027433146);
            inst_co.setValue(data101, 509.50712770667604);
            inst_co.setValue(data102, 156.09358944455911);
            inst_co.setValue(data103, 293.78788374425335);
            inst_co.setValue(data104, 711.7742368613966);
            inst_co.setValue(data105, 449.7956359584618);
            inst_co.setValue(data106, 30.040889228212826);
            inst_co.setValue(data107, 390.43829302782444);
            inst_co.setValue(data108, 507.7950655667716);
            inst_co.setValue(data109, 302.8199342817794);
            inst_co.setValue(data110, 89.01918310288258);
            inst_co.setValue(data111, 128.61800421180703);
            inst_co.setValue(data112, 809.6286396604214);
            inst_co.setValue(data113, 727.2597950457308);
            inst_co.setValue(data114, 281.04503165385364);
            inst_co.setValue(data115, 117.17278184473099);
            inst_co.setValue(data116, 282.4604916666121);
            inst_co.setValue(data117, 363.51815809697337);
            inst_co.setValue(data118, 157.49409280372396);
            inst_co.setValue(data119, 60.92365417847305);
            inst_co.setValue(data120, 36.21397407290429);
            inst_co.setValue(data121, 134.68535525204678);
            inst_co.setValue(data122, 375.0133650408735);
            inst_co.setValue(data123, 268.99336636349454);
            inst_co.setValue(data124, 255.92451561184592);
            inst_co.setValue(data125, 6.1518001737522585);
            inst_co.setValue(data126, 214.116187310762);
            inst_co.setValue(data127, 402.6390521984887);
            inst_co.setValue(data128, 259.08593488329217);

            // Add instance to dataset
            data.add(inst_co);
            data.setClassIndex(data.numAttributes() - 1);

            // Intialize and run classifier
            WekaWrapper clasifyData = new WekaWrapper();
            result = clasifyData.classifyInstance(data.firstInstance());

        } catch (Exception e) {

            e.printStackTrace();
        }

        Log.d("", "Classification result = " + result); // 0.0 - chodzenie, 1.0 - stanie
        */
        /*
        ---------------------------------END OF CLASSIFICATION------------------------------
        Code to test classification (hardcoded example)
         */

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

        //Start recording sensor data on click
        Button classifyButton = (Button) findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagClassify = true;
                samples_acc = 0;
                samples_gyro = 0;
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


        //------------------------------------------------------------------------------------------------------------------------------------------

        //Classify
        if (samples_acc < classify_samples || samples_gyro < classify_samples)  {
            if (flagClassify == true) {

                //Get current date and time
                currentTime = Calendar.getInstance().getTime();
                dateTime = currentTime.toString();

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


            } //end if flag is true
            else {
                //Log.d("", "samples < 10 but Flag is false - Data NOT inserted");
            }
        } else if (samples_acc == classify_samples && samples_gyro == classify_samples){
            durationTime = System.currentTimeMillis() - startTime;

            for (int s = 0; s < classify_samples; s++) {

                //Add data to array
                accModule = Math.sqrt(accDoubleArrayX[s] * accDoubleArrayX[s] + accDoubleArrayY[s] * accDoubleArrayY[s] + accDoubleArrayZ[s] * accDoubleArrayZ[s]);
                classifyRawDataDoubleArray[s] = accModule;
            }

            Log.d("", "Recorded data module: " + Arrays.toString(classifyRawDataDoubleArray));

            //FFT
            classifyRealDoubleArray = classifyRawDataDoubleArray; // compute FFT but from the copied Array

            //Calculate FFT for recorded data module
            FFT fft = new FFT(classifyRealDoubleArray.length);
            fft.fft(classifyRealDoubleArray, classifyImagDoubleArray);
            double[] classifyFFTMagDoubleArray = new double[classifyRealDoubleArray.length];

            Log.d("", "FFT real: " + Arrays.toString(classifyRealDoubleArray));
            Log.d("", "FFT imaginary: " + Arrays.toString(classifyImagDoubleArray));

            //Calculate magnitude of FFT data
            for (int i = 0; i < classifyRealDoubleArray.length; i++) {
                classifyFFTMagDoubleArray[i] = Math.sqrt(Math.pow(classifyRealDoubleArray[i], 2) + Math.pow(classifyImagDoubleArray[i], 2));
            }

            Log.d("", "FFT Magnitude: " + Arrays.toString(classifyFFTMagDoubleArray));

            //Initialise classification
            Classification classification = new Classification();
            double classifictaionResult = classification.runClassification(classifyFFTMagDoubleArray);

            Log.d("", "classifictaionResult: " + classifictaionResult);

            if (classifictaionResult == 1.0) resultText.setText("stanie");
            if (classifictaionResult == 0.0) resultText.setText("chodzenie");

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