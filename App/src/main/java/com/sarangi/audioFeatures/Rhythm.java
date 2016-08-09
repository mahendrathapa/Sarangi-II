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
         * Sampling frequency of the audio sample
         */

        int binSize = 1024;

        private  double samplingRate;

        int howLong = 12;

        private double[] strongestBeat = new double[howLong];


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

        public Rhythm(List<double[]> audioFrame, AudioFormat audioFormat){


                samplingRate = (double)audioFormat.getSampleRate();

                int length = audioFrame.size() - binSize;

                double[] rms = new double[binSize];
                
                double effectiveSamplingRate = samplingRate / (double)rms.length;

                int minLag = (int)(0.286 * effectiveSamplingRate);
                int maxLag = (int)(3.0 * effectiveSamplingRate);

                Map<Double,Integer> bmpCount = new HashMap<Double,Integer>();
                Map<Double,Double> bmpEnergy = new HashMap<Double,Double>();

                for(int loop=0; loop<=length; loop+= 1){

                        
                        for(int k=0, j = loop; k < binSize; ++j,++k){

                                rms[k] = (double)getRms(audioFrame.get(j));
                        }

                        double[] beatHistogram = getAutoCorrelation(rms,minLag,maxLag);

                        double[] beatLabels = getAutoCorrelationLabels(effectiveSamplingRate,minLag,maxLag);

                        for(int i=0; i<beatLabels.length; ++i)
                                beatLabels[i]  *= 60.0;

                        double beatPerMinute = getStrongestBeat(beatHistogram,beatLabels);

                        double strongest =  getStrengthofStrongestBeat(beatHistogram);

                        if(bmpCount.containsKey(beatPerMinute)){
                                int value = bmpCount.get(beatPerMinute);
                                bmpCount.put(beatPerMinute, ++ value);

                                double energy = bmpEnergy.get(beatPerMinute);
                                bmpEnergy.put(beatPerMinute, energy + strongest);
                        }else{

                                bmpCount.put(beatPerMinute,1);
                                bmpEnergy.put(beatPerMinute,strongest);
                        }

                }

                Map<Double,Integer> sortedMap = sortByComparator(bmpCount);

                ArrayList<Double> keys = new ArrayList<Double>(sortedMap.keySet());

                for(int count = 0,i=keys.size()-1;i>=0 && count<howLong; --i,++count){

                        strongestBeat[count] = keys.get(i);
                        strongestBeat[++count] = sortedMap.get(keys.get(i));
                        strongestBeat[++count] = bmpEnergy.get(keys.get(i))/sortedMap.get(keys.get(i));
                }

        }

        private Map<Double, Integer> sortByComparator(Map<Double, Integer> unsortMap) {

                // Convert Map to List
                List<Map.Entry<Double, Integer>> list = 
                        new LinkedList<Map.Entry<Double, Integer>>(unsortMap.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<Double, Integer>>() {
                        public int compare(Map.Entry<Double, Integer> o1,
                                        Map.Entry<Double, Integer> o2) {
                                return (o1.getValue()).compareTo(o2.getValue());
                        }
                });

                // Convert sorted map back to a Map
                Map<Double, Integer> sortedMap = new LinkedHashMap<Double, Integer>();
                for (Iterator<Map.Entry<Double, Integer>> it = list.iterator(); it.hasNext();) {
                        Map.Entry<Double, Integer> entry = it.next();
                        sortedMap.put(entry.getKey(), entry.getValue());
                }
                return sortedMap;
        }

        private void printMap(Map<Double,Integer> map){
               
                ArrayList<Double> keys = new ArrayList<Double>(map.keySet());

                for(int i=keys.size()-1;i>=0; --i)
                        System.out.println(keys.get(i) + " " + map.get(keys.get(i)));

                /*for(Map.Entry<Double,Integer> entry : map.entrySet()){
                        System.out.println(entry.getKey() + "  " +entry.getValue());
                }*/
        }

        public double[] getStrongestBeat(){
                return strongestBeat;
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
        private double getStrongestBeat(double[] beatHistogram, double[] labels){
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
        private double getStrengthofStrongestBeat(double[] beatHistogram){
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

        private double[] getStrengthOfBeat(double[] beatHistogram){
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
        private double getBeatSum(double[] beatHistogram){
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
        private int getIndexOfLargest (double[] values){
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

        private double[] getAutoCorrelationLabels(double samplingRate, int minLag, int maxLag){
                double[] labels  = new double[maxLag-minLag + 1];
                for(int i=0; i<labels.length; ++i)
                        labels[i] = samplingRate / ((double)(i+minLag));
                return labels;
        }


        /**
         * Returns the Root Mean Square (RMS) from a set of samples. 
         *
         * @param   sample          The digital signal to calculate the RMS value
         *
         * @return                  RMS value of the signal.
         */
        private double getRms(double[] sample){
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

        private double[] getAutoCorrelation(double[] signal, int minLag, int maxLag){
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


