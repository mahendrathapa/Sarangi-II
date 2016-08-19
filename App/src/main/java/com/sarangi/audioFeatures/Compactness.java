/*
 * @(#) Compactness.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */
package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

/**
 * A class that extracts the compactness features of the song. This is a measure
 * of noiseness of a signal.
 *
 * This is calculated by comparing the value of a magnitude specturm bin with 
 * its surrounding values.
 *
 * @author Mahendra Thapa
 */

public class Compactness{

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
     * @param  magnitudeSpectrums      The magnitude specturm of the samples to extract the feature from.
     *
     * @param samplingRate             The sampling rate that the samples are encoded with.
     *
     * @return                         The extracted feature values.
     *
     */
    
    public static double[][] extractFeature(double[][] magnitudeSpectrums,double samplingRate){

        int dimension = 1;
        double[][] feature = new double[magnitudeSpectrums.length][dimension];
        int count = 0;

        for(double[] magnitudeSpectrum : magnitudeSpectrums){

            double compactness = 0.0;
            int length = magnitudeSpectrum.length;

            for(int i = 1; i< length -1; ++i){

                if((magnitudeSpectrum[i-1] > 0.0) && (magnitudeSpectrum[i] > 0.0)
                        && (magnitudeSpectrum[i+1] > 0.0)){

                    compactness += Math.abs(20.0*Math.log(magnitudeSpectrum[i]) -
                            20.0*(Math.log(magnitudeSpectrum[i-1]) +
                                Math.log(magnitudeSpectrum[i]) + 
                                Math.log(magnitudeSpectrum[i+1]))/3.0);

                        }
            }

            feature = Statistics.assign1Dto2DArray(feature,new double[]{compactness},count);
            ++count;

        }

        return feature;

    }
}
