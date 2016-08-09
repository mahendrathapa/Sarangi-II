/*
 * @(#) Rhythm.java 2.0     June 30,2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 *
 */


package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * A class for calculating the rhythm features of the given audio samples.
 *
 * <p> Include constructor in which the different methods are integrated to calcuate
 * the rhythmic features of the audio input signal.
 *
 * <p> Include method for accessing rhythmic features of the given audio sample 
 *
 * @author  Mahendra Thapa
 *
 */

public class Rhythm{


        public static double[][] extractFeature(List<double[]> audioFrames, double samplingRate){


                double[][] rmsFrame = RMS.extractFeature(audioFrames,samplingRate);

                return extractFeature(rmsFrame,samplingRate);


        }
        public static double[][] extractFeature(double[][] rmsValues,double samplingRate){

                int binSize = 1024;

                int length = rmsValues.length - binSize;

                double[] rms = new double[binSize];

                double effectiveSamplingRate = samplingRate / (double)rms.length;

                int minLag = (int)(0.286 * effectiveSamplingRate);
                int maxLag = (int)(3.0 * effectiveSamplingRate);

                double[][] feature = new double[length + 1][2];

                for(int loop=0; loop<=length; loop+= 1){


                        for(int k=0, j = loop; k < binSize; ++j,++k){

                                rms[k] = rmsValues[j][0];
                        }

                        double[] beatHistogram = getAutoCorrelation(rms,minLag,maxLag);

                        double[] beatLabels = getAutoCorrelationLabels(effectiveSamplingRate,minLag,maxLag);

                        for(int i=0; i<beatLabels.length; ++i)
                                beatLabels[i]  *= 60.0;

                        double beatPerMinute = getStrongestBeat(beatHistogram,beatLabels);

                        double strongest =  getStrengthofStrongestBeat(beatHistogram);

                        feature = Statistics.assign1Dto2DArray(feature,new double[]{beatPerMinute,strongest},loop);

                }

                return feature;
       }
        
        /**
         * Return the dominating beat of the audio signal based on the energy of that signal at that given BPM.
         *
         * @param   beatHistogram   The beat histogram of the audio signal.
         *
         * @param   labels          The distribution of BPM.
         *
         * @return                  Beats per minutes of the given audio signal.
         */
        private static double getStrongestBeat(double[] beatHistogram, double[] labels){
                int highestBin = getIndexOfLargest(beatHistogram);
                return labels[highestBin];
        }

        /**
         * Return the strength of the strongest beat of the given audio signal.
         *
         * @param   beatHistogram   The beat histogram of the audio signal.
         *
         * @return                  The strength of the strongest beat of the given audio signal.
         */
        private static double getStrengthofStrongestBeat(double[] beatHistogram){
                double beatSum = getBeatSum(beatHistogram);
                int highestBin = getIndexOfLargest(beatHistogram);
                double highestStrength = beatHistogram[highestBin];
                return highestStrength/beatSum;
        }

        /**
         * Return the strength of the beat of the given audio signal.
         *
         * @param   beatHistogram   The beat histogram of the audio signal.
         *
         * @return                  The strength of the beat of the given audio signal.
         */

        private static double[] getStrengthOfBeat(double[] beatHistogram){
                double beatSum = getBeatSum(beatHistogram);
                int length = beatHistogram.length;
                for(int i=0; i<length; ++i)
                        beatHistogram[i] /= beatSum;
                return beatHistogram;
        }

        /**
         * Calculate the Beat Sum of a signal based on the beat histogram of the signal.
         *
         * @param   beatHistogram       A reference to the beat histogram of the signal.
         *
         * @return                      Beat sum of the signal.
         */
        private static double getBeatSum(double[] beatHistogram){
                double sum = (double)0.0;
                for(double temp:beatHistogram)
                        sum += temp;
                return sum;
        }

        /**
         * Returns the index of the entry of an array of double with the largest value.
         *
         * @param   values      A reference to the array.
         *
         * @return              Index of the highest value of that array.
         *
         */
        private static int getIndexOfLargest (double[] values){
                int maxIndex = 0;
                for(int i=0; i<values.length; ++i)
                        if(values[i]>values[maxIndex])
                                maxIndex = i;
                return maxIndex;
        }

        /**
         * Returns the bin labels for each bin of an auto-correlation calculation that involved
         * the given parameters.
         *
         * @param   samplingRate        The sampling rate that was used to encode.
         *                              the signal that was auto-correlated
         * @param   minLag              The minimum lag in samples that was used in the auto-correlation.
         * @param   maxLag              The maximum lag in samples that was used in the auto-correlation.
         *
         * @return                      The labels, in Hz, for the corresponding
         *                              bins produced by the getAutoCorrelation method.
         */

        private static double[] getAutoCorrelationLabels(double samplingRate, int minLag, int maxLag){
                double[] labels  = new double[maxLag-minLag + 1];
                for(int i=0; i<labels.length; ++i)
                        labels[i] = samplingRate / ((double)(i+minLag));
                return labels;
        }

        /**
         * Calculates the auto-correlation of the given signal. The auto-correlation
         * is only calculated between the given lags.
         *
         * <p>The getAutoCorrelationsLabels method can be called to find the labels in
         * Hz for each of the returned bins.
         *
         * @param   signal  The digital signal to auto-correlate.
         * @param   minLag  The minimum lag in samples to look for in the auto-correlation.
         * @parm    maxLag  The maximum lag in samples to look for in the auto-correlation.
         * @return          The auto-correlation for each lag from minLag to maxLag.
         *                  Entry 0 corresponds to minLag, and the last entry corresponds
         *                  to maxLag.
         *
         */

        private static double[] getAutoCorrelation(double[] signal, int minLag, int maxLag){
                double[] autoCorrelation = new double[maxLag-minLag+1];
                for(int lag = minLag; lag<=maxLag; ++lag){
                        int autoIndice= lag-minLag;
                        autoCorrelation[autoIndice] = (double)0.0;
                        for(int samp = 0; samp<signal.length-lag;++samp)
                                autoCorrelation[autoIndice] +=signal[samp]*signal[samp + lag];
                }
                return autoCorrelation;
        }

}
