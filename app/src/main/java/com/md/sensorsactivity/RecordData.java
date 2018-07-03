package com.md.sensorsactivity;

import java.util.Calendar;
import java.util.Date;

public class RecordData {

    public void recordDataMethod(long startTime, String dateTime, int samples_total, int samples, double x, double y, double z, double x2, double y2, double z2, double x3, double y3, double z3, double cos3, double accuracy3, String label){

        long durationTime;

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

        for (int i = 1; i <= samples_total; i++) {
            if (i < samples_total) {

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

            } else if (i == samples_total) {
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
                MainActivity.getDB().insertData(dateTime, accDoubleArrayX, accDoubleArrayY, accDoubleArrayZ,
                        gyroDoubleArrayX, gyroDoubleArrayY, gyroDoubleArrayZ,
                        rotationDoubleArrayX, rotationDoubleArrayY, rotationDoubleArrayZ, rotationDoubleArrayCos, rotationDoubleArrayAccuracy,
                        durationTime, label);

                //Stop recording when given number of samples inserted

                samples++;

            } else {
                //Not in use
            }
        }
    }
}
