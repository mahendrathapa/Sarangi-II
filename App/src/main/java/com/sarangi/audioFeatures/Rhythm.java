/*
 * @(#) Rhythm.java 2.0     June 30,2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 *
 */


package com.sarangi.audioFeatures;

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

        /*FIELDS **************************************************************/

        /**
         * A reference to the number of window
         */
        private final int binSize = 1024;

        /**
         * Reference to the range of the beats per minute.
         */
        private final int range = 30;

        /**
         * Reference to the gap of the beats per minute.
         */
        private final int gap = 10;

        /**
         * A reference to the array for storing the rhythmic features as a graph between the strength and beats per minute
         */
        int[] beatGraph = new int[range];

        /**
         * A reference to the beats per minutes of each window
         */
        List<Float> bpm = new ArrayList<Float>();

        /**
         * Sampling frequency of the audio sample
         */
        private  float samplingRate;


        /*CONSTRUCTORS ******************************************************************/

        /**
         * Extract the rhythmic features from the given audio samples.
         * Also set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         *
         * @param   samples             A reference to the audio sample of the song.
         *
         * @param   audioFormat         A reference to the audio format which is associated with given audio samples.
         *
         */

        public Rhythm(List<float[]> audioFrame, AudioFormat audioFormat){

                samplingRate = (float)audioFormat.getSampleRate();

                int length = audioFrame.size() - binSize;

                float[] rms = new float[binSize];
                
                float effectiveSamplingRate = samplingRate / (float)rms.length;

                int minLag = (int)(0.286 * effectiveSamplingRate);
                int maxLag = (int)(3.0 * effectiveSamplingRate);

                for(int loop=0; loop<=length; loop+= 1){

                        
                        for(int k=0, j = loop; k < binSize; ++j,++k){

                                rms[k] = (float)getRms(audioFrame.get(j));
                        }

                        float[] beatHistogram = getAutoCorrelation(rms,minLag,maxLag);

                        float[] beatLabels = getAutoCorrelationLabels(effectiveSamplingRate,minLag,maxLag);

                        for(int i=0; i<beatLabels.length; ++i)
                                beatLabels[i]  *= 60.0;

                        bpm.add(getStrongestBeat(beatHistogram,beatLabels));

                }

                for(float singleRms : bpm){
                        int index = (int)singleRms/gap;

                        if(index<range)
                                ++beatGraph[index];
                }

        }

        /**
         * Return the rhythmic graph of the given audio signal.
         *
         * @return      Rhythmic graph of the audio.
         */
        public int[] getRhythmGraph(){
                return beatGraph;
        }

        /**
         * Return the frame wise beats per minute of the given audio signal.
         *
         * @return      Beats per minute of the each frame.
         *
         */
        public List<Float> getBPMFrameWise(){
                return bpm;
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
        private float getStrongestBeat(float[] beatHistogram, float[] labels){
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
        private float getStrengthofStrongestBeat(float[] beatHistogram){
                float beatSum = getBeatSum(beatHistogram);
                int highestBin = getIndexOfLargest(beatHistogram);
                float highestStrength = beatHistogram[highestBin];
                return highestStrength/beatSum;
        }

        /**
         * Return the strength of the beat of the given audio signal.
         *
         * @param   beatHistogram   The beat histogram of the audio signal.
         *
         * @return                  The strength of the beat of the given audio signal.
         */

        private float[] getStrengthOfBeat(float[] beatHistogram){
                float beatSum = getBeatSum(beatHistogram);
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
        private float getBeatSum(float[] beatHistogram){
                float sum = (float)0.0;
                for(float temp:beatHistogram)
                        sum += temp;
                return sum;
        }

        /**
         * Returns the index of the entry of an array of float with the largest value.
         *
         * @param   values      A reference to the array.
         *
         * @return              Index of the highest value of that array.
         *
         */
        private int getIndexOfLargest (float[] values){
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

        private float[] getAutoCorrelationLabels(float samplingRate, int minLag, int maxLag){
                float[] labels  = new float[maxLag-minLag + 1];
                for(int i=0; i<labels.length; ++i)
                        labels[i] = samplingRate / ((float)(i+minLag));
                return labels;
        }


        /**
         * Returns the Root Mean Square (RMS) from a set of samples. 
         *
         * @param   sample          The digital signal to calculate the RMS value
         *
         * @return                  RMS value of the signal.
         */
        private double getRms(float[] sample){
                double sum = 0.0;
                for(int i=0; i<sample.length; ++i)
                        sum += Math.pow(sample[i],2);
                return Math.sqrt(sum/sample.length);
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

        private float[] getAutoCorrelation(float[] signal, int minLag, int maxLag){
                float[] autoCorrelation = new float[maxLag-minLag+1];
                for(int lag = minLag; lag<=maxLag; ++lag){
                        int autoIndice= lag-minLag;
                        autoCorrelation[autoIndice] = (float)0.0;
                        for(int samp = 0; samp<signal.length-lag;++samp)
                                autoCorrelation[autoIndice] +=signal[samp]*signal[samp + lag];
                }
                return autoCorrelation;
        }


}


