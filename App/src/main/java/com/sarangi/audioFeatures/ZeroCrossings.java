/*
 * @(#) RMS.java 2.0      August 8, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

public class ZeroCrossings{

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



