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

/**
 * A class that extracts the spectral rolloff point. This is a measure
 * of the amount of the right-skewnedness of the power spectrum.
 *
 * @author Mahendra Thapa
 */

public class SpectralRolloffPoint{

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

        return extractFeature(output, samplingRate);
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


