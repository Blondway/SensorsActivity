package com.md.sensorsactivity;

import android.util.Log;

import java.util.Arrays;

public class InsertFFT {

    int samples = 256;

    //double[] rawDataDoubleArray = new double[samples];

    double[] realDoubleArray = new double[samples];
    double[] imagDoubleArray = new double[samples];

    public void fftToTable (double[] rawDataDoubleArray, String labelFFT) {

        realDoubleArray = rawDataDoubleArray; // compute FFT but from the copied Array

        for (int s = 0; s < imagDoubleArray.length; s++) {
            imagDoubleArray[s] = 0;
        }

        //Calculate FFT for recorded data module
        FFT fft = new FFT(rawDataDoubleArray.length);
        fft.fft(realDoubleArray, imagDoubleArray);
        double[] fftMagDoubleArray = new double[realDoubleArray.length];

        Log.d("", "FFT real: " + Arrays.toString(realDoubleArray));
        Log.d("", "FFT imaginary: " + Arrays.toString(imagDoubleArray));

        //Calculate magnitude of FFT data
        for (int i = 0; i < realDoubleArray.length; i++) {
            fftMagDoubleArray[i] = Math.sqrt(Math.pow(realDoubleArray[i], 2) + Math.pow(imagDoubleArray[i], 2));
        }

        Log.d("", "FFT Magnitude: " + Arrays.toString(fftMagDoubleArray));

        MainActivity.getDB().insertFFTData(fftMagDoubleArray, realDoubleArray, imagDoubleArray, labelFFT);
    }
}
