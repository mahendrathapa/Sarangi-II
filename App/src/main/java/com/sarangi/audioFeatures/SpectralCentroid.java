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

/**
 * A class that extracts the spectral centroid feature of a song.
 * This is a measure of the "centre of mass" of the power spectrum.
 *
 * @author Mahendra Thapa
 */

public class SpectralCentroid{

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

    public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

        double [][] output = PowerSpectrum.extractFeature(audioFrames,samplingRate);

        return extractFeature(output,samplingRate);
    }

    /**
     * Extracts this feature from the given power spectrum at the given sampling
     * rate.
     * <p>
     * In the case of this feature, the sampling rate parameter is ignored.
     * <p>
     * @param  powerSpectrums          The power specturm of the samples to extract the feature from.
     *
     * @param samplingRate             The sampling rate that the samples are encoded with.
     *
     * @return                         The extracted feature values.
     *
     */
  
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


