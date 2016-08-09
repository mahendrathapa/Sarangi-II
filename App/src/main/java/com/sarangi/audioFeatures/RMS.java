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

public class RMS{

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

