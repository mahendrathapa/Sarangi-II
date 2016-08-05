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

public class RMS extends SarangiFeature{

        private double[][] frameRms;

        private final int frameLevelDimension = 1;

        public RMS(){

                this(2);
        }

        public RMS(int dimension){
                super("Root Mean Square","A mearuse of the power of a signal",dimension);
        }

        public double[] extractFeature(List<double[]> audioFrames, double samplingRate){

                frameRms = new double[audioFrames.size()][frameLevelDimension];

                int count = 0;

                for(double[] frame : audioFrames){

                        double sum = 0.0;
                        int length = frame.length;

                        for(int samp = 0; samp<length; samp++)
                                sum += Math.pow(frame[samp],2);

                        double[] rms = new double[]{Math.sqrt(sum/length)};

                        frameRms = Statistics.assign1Dto2DArray(frameRms, rms, count);
                        ++count;
                }

                feature = Statistics.getAverageAndStandardDeviation(frameRms,dimension);

                return feature;
        }

        public double[][] getFrameLevelFeature(){
                return frameRms;
        }

}

