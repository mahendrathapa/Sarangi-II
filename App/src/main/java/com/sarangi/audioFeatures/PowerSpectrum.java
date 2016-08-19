/*
 * @(#) PowerSpectrum.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

/**
 * A class that extracts the FFT power spectrum from a set of samples.
 * This is a good measure of the power of different frequency components 
 * within a window.
 *
 * The dimensions of this feature depend on the number of FFT bins, which
 * depend on the number of input samples. 
 *
 * @author Mahendra Thapa
 *
 */

public class PowerSpectrum{

    /* PUBLIC METHODS ********************************************************/
    
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

        int dimension = audioFrames.get(0).length;

        double[][] feature = new double[audioFrames.size()][dimension];

        int count = 0;


        for(double[] frame : audioFrames){

            FFT fft = new FFT(frame);
            feature = Statistics.assign1Dto2DArray(feature,fft.getPowerSpectrum(),count);
            ++count;
        }

        return feature;
    }
}


