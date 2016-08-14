/*
 * @(#) SpectralFlux.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */
package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

public class SpectralFlux{

    public static double[][] extractFeature(List<double[]> audioFrames,double samplingRate){

        double[][] output = MagnitudeSpectrum.extractFeature(audioFrames,samplingRate);

        return extractFeature(output, samplingRate);
    }

    public static double[][] extractFeature(double[][] magnitudeSpectrums,double samplingRate){

        int dimension = 1;
        int length = magnitudeSpectrums.length;
        double[][] feature = new double[length][dimension];

        for(int i = 1; i<length; ++i){

            double sum = 0.0;
            double[] thisMagnitudeSpectrum = magnitudeSpectrums[i];
            double[] previousMagnitudeSpectrum = magnitudeSpectrums[i-1];

            int loop = thisMagnitudeSpectrum.length;

            for(int bin = 0; bin < loop; ++bin){

                double difference = thisMagnitudeSpectrum[bin] - previousMagnitudeSpectrum[bin];
                double differenceSquared = difference * difference;

                sum += differenceSquared;
            }

            feature = Statistics.assign1Dto2DArray(feature,new double[]{sum},i-1);
        }

        return feature;

    }

}


