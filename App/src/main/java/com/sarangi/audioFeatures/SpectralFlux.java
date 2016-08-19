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

/**
 * A class that extract the spectral flux from a window of samples and the 
 * preceeding window. 
 *
 * This a good measure of the amount of spectral changes of a signal.
 *
 * @author Mahendra Thapa
 */

public class SpectralFlux{

    /* PUBLIC METHODS ******************************************************/

    /**
     * Extracts this feature from the given audio frames at the given sampling
     * rate.
     *
     * <p> 
     * In the case of this feature, the sampling rate parameter is ignored.
     * <p> 
     *  
     * @param   audioFrames     The audio frame to extract the feature from.
     *
     * @param   samplingRate    The sampling rate that the samples are encoded.
     *
     * @return                  The extracted feature values.
     */

    public static double[][] extractFeature(List<double[]> audioFrames,double samplingRate){

        double[][] output = MagnitudeSpectrum.extractFeature(audioFrames,samplingRate);

        return extractFeature(output, samplingRate);
    }

    /**
     * Extracts this feature from the given power spectrum at the given sampling
     * rate.
     * <p>
     * In the case of this feature, the sampling rate parameter is ignored.
     * <p>
     * @param magnitudeSpectrums       The magnitude specturm of the samples to extract the feature from.
     *
     * @param samplingRate             The sampling rate that the samples are encoded with.
     *
     * @return                         The extracted feature values.
     *
     */
  
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


