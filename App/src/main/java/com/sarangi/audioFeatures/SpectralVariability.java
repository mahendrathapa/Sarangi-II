/*
 * @(#) SpectralVariability.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */
package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

/**
 * A class that extracts the spectral variance.  This is a measure of the 
 * standard deviation of a signal's magnitude spectrum.
 *
 * @author Mahendra Thapa
 */
public class SpectralVariability{

    /* PUBLIC METHODS ********************************************************/

    /**
     * Extracts this feature from the given audio frames at the given sampling
     * rate.
     * <p>
     * In the case of this feature, the sampling rate parameter is ignored.
     * <p>
     * @param audioFrames       The audio frame to extract the feature from.
     *
     * @param samplingRate      The sampling rate that the samples are encoded with.
     *
     * @return                  The extracted feature values.
     *
     */
    
    public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

        double[][] output = MagnitudeSpectrum.extractFeature(audioFrames,samplingRate);

        return extractFeature(output,samplingRate);

    }

    /**
     * Extracts this feature from the given magnitude spectrum at the given sampling
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

        for(int i=0; i<length; ++i){

            double[] magnitudeSpectrum  = magnitudeSpectrums[i];

            double variance = Statistics.getStandardDeviation(magnitudeSpectrum);

            feature = Statistics.assign1Dto2DArray(feature,new double[]{variance},i);
        }

        return feature;
    }
}
