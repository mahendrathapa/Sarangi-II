/*
 * @(#) SpectralCentroid.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */
package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

public class SpectralCentroid{

    public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

        double [][] output = PowerSpectrum.extractFeature(audioFrames,samplingRate);

        return extractFeature(output,samplingRate);
    }

    public static double[][] extractFeature(double[][] powerSpectrums,double samplingRate){

        int dimension = 1;
        double[][] feature = new double[powerSpectrums.length][dimension];
        int count = 0;

        for(double[] powerSpectrum : powerSpectrums){

            double total = 0.0;
            double weightedTotal = 0.0;
            int length = powerSpectrum.length;

            for(int bin = 0; bin<length; ++bin){
                weightedTotal += bin * powerSpectrum[bin];
                total += powerSpectrum[bin];
            }

            if(total != 0.0)
                feature = Statistics.assign1Dto2DArray(feature, new double[]{weightedTotal/total},count);
            else
                feature = Statistics.assign1Dto2DArray(feature, new double[]{0.0},count);

            ++count;
        }

        return feature;

    }
}


