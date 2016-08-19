/*
 * @(#) RMS.java 2.0      August 8, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

/**
 * A class that extracts the zero crossing from a set of samples.
 *
 * This is a good measure of the pitch as well as the noininess of a signal.
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

public class ZeroCrossings{

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

        int dimension = 1;

        double[][] feature = new double[audioFrames.size()][dimension];

        int loop = 0;

        for(double[] frame : audioFrames){

            long count = 0;
            int length = frame.length;

            for(int samp = 0; samp < length -1 ; ++samp){

                if(frame[samp] > 0.0 && frame[samp + 1] < 0.0)
                    ++count;
                else if(frame[samp] < 0.0 && frame[samp + 1] > 0.0)
                    ++count;
                else if(frame[samp] == 0.0 && frame[samp + 1] != 0.0)
                    ++count;
            }
            feature = Statistics.assign1Dto2DArray(feature,new double[]{count},loop);
            ++loop;
        }

        return feature;
    }
}



