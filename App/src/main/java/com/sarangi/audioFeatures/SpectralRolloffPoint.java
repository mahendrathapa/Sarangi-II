/*
 * @(#) SpectralRolloffPoint.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */
package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

public class SpectralRolloffPoint{

    public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

        double [][] output = PowerSpectrum.extractFeature(audioFrames,samplingRate);

        return extractFeature(output, samplingRate);
    }

    public static double[][] extractFeature(double[][] powerSpectrums, double samplingRate){

        final double cutoff = 0.85;
        int dimension = 1;
        int length = powerSpectrums.length;

        double[][] feature = new double[length][dimension];

        for(int i=0; i<length; ++i){

            double[] powerSpectrum = powerSpectrums[i];

            double total = 0.0;
            int loop = powerSpectrum.length;

            for(int bin = 0; bin<loop; ++bin)
                total += powerSpectrum[bin];

            double threshold = total * cutoff;

            total = 0.0;
            int point = 0;

            for(int bin = 0; bin < loop; ++bin){
                total += powerSpectrum[bin];

                if(total >= threshold){
                    point = bin;
                    bin = loop;
                }
            }

            feature = Statistics.assign1Dto2DArray(feature,new double[]{((double)point)/((double)loop)},i);
        }

        return feature;
    }
}


