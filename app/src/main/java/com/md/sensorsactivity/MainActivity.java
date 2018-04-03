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
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private TextView xText, yText, zText;
    private Sensor accSensor; // Accelerometer
    private SensorManager SM;

    private static DatabaseHelper dbRef;
    DatabaseHelper sensorsDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        accSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register Sensor Listener
        SM.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);

        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);



        // Create Database
        sensorsDB = new DatabaseHelper(this);
        Toast.makeText(this, "Database created",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float x = sensorEvent.values[0];
        final float y = sensorEvent.values[1];
        final float z = sensorEvent.values[2];
        xText.setText("X: " + sensorEvent.values[0]);
        yText.setText("Y: " + sensorEvent.values[1]);
        zText.setText("Z: " + sensorEvent.values[2]);
        // 0 - X axis, 1 - Y axis, 2 - Z axis
        //sensorsDB.insertData(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);

        File dbtest = new File("/data/data/" + "com.md.sensorsactivity" + "/databases/" + "SensorsData.db");
        if (dbtest.exists()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sensorsDB.insertData(x,y,z);
                }
            }, 0, 1000);//put here time 1000 milliseconds=1 second
        }
        else {
            Log.d("", "Database don't exists.");
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not in use
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.DeleteButton:
                dbRef.deleteDB();
                Toast.makeText(this, "Database deleted",Toast.LENGTH_SHORT).show();
                sensorsDB = new DatabaseHelper(this);
                Toast.makeText(this, "Database created",Toast.LENGTH_SHORT).show();
                break;
            case R.id.CreateButton:
                //DO something
                break;
        }
    }
}
