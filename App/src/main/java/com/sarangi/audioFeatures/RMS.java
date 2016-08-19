/*
 * @(#) RMS.java 2.0      August 5, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

/**
 * A class that extracts the Root Mean Square (RMS) from a set of samples. This
 * is a good measure of the power of a signal.
 *
 * @author Mahendra Thapa
 *
 */
public class RMS{


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

        int dimension = 1;
        double[][] feature = new double[audioFrames.size()][dimension];

        int count = 0;

        for(double[] frame : audioFrames){

            double sum = 0.0;
            int length = frame.length;

            for(int samp = 0; samp<length; ++samp)
                sum += Math.pow(frame[samp],2);

            double[] rms = new double[]{Math.sqrt(sum/length)};

            feature = Statistics.assign1Dto2DArray(feature, rms, count);
            ++count;
        }

        return feature;

    }
}

