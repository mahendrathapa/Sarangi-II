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

public class SpectralVariability{

        public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

                double[][] output = MagnitudeSpectrum.extractFeature(audioFrames,samplingRate);

                return extractFeature(output,samplingRate);

        }

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


