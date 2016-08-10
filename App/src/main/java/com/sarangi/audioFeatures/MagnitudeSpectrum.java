/*
 * @(#) MagnitudeSpectrum.java 2.0      August 6, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;

public class MagnitudeSpectrum{

        public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){

                int dimension = audioFrames.get(0).length;

                double[][] feature = new double[audioFrames.size()][dimension];

                int count = 0;


                for(double[] frame : audioFrames){

                        FFT fft = new FFT(frame);
                        feature = Statistics.assign1Dto2DArray(feature,fft.getMagnitudeSpectrum(),count);
                        ++count;
                }

                return feature;
        }
}

