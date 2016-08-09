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

public class Compactness{

        public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

                double[][] output = MagnitudeSpectrum.extractFeature(audioFrames,samplingRate);

                return extractFeature(output,samplingRate);

        }

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
