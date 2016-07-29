/**
 * @(#) DatasetUtil.java 2.0     July 29, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

import com.sarangi.structures.*;
import com.sarangi.audioFeatures.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Class for holding the dataset for training and testing
 *
 *
 * @author Bijay Gurung
 */

public static class DatasetUtil {

        public static enum FeatureType {

                SARANGI_MFCC, 
                SARANGI_PITCH,
                SARANGI_RHYTHM,

        }

        /**
         * Get the LearningDataset object on a song by song basis.
         *
         * @param dataSongs The songs to use.
         * @param labelArray The array of label strings.
         * @param featureType The type of feature to extract into the dataset.
         *
         * @return The LearningDataset object corresponding to the given input.
         *
         */
        public static LearningDataset getSongwiseDataset(List<Song> dataSongs, String[] labelArray, FeatureType featureType) {

                LearningDataset learningDataset = new LearningDataset();

                learningDataset.labels = new int[dataSongs.size()];

                if (featureType == FeatureType.SARANGI_PITCH) {
                    learningDataset.dataset = new double[dataSongs.size()][dataSongs.get(0).getPitch().length];
                    int i = 0;
                    for (Song song: dataSongs) {

                            int[] pitch = song.getPitch();

                            learningDataset.dataset[i] = convertIntArrayToDoubleArray(pitch);
                            learningDataset.labels[i] = getIndexOfLabel(song.getSongName(), labelArray);

                            i++;

                    }

                } else if (featureType == FeatureType.SARANGI_MFCC) {
                    
                    // We average the value of MFCC over all frames to get one set of MFCC for the whole song.

                    learningDataset.dataset = new double[dataSongs.size()][Melfreq.NUM_OF_COEFFICIENTS];

                    int i = 0;
                    for (Song song: dataSongs) {

                            ArrayList<float[]> mfcc = song.getMFCC();

                            double[] avgMFCC = new double[Melfreq.NUM_OF_COEFFICIENTS];

                            for (float[] singleFrameMFCC: mfcc) {
                                    // Sum the MFCC coefficients 
                                    for (int j=0; j<Melfreq.NUM_OF_COEFFICIENTS; j++) {
                                            avgMFCC[j] += singleFrameMFCC[j];
                                    }
                            }

                            int numberOfFrames = mfcc.size();
                            for (int j=0; j<avgMFCC.length; j++) {
                                    avgMFCC[j] /= numberOfFrames;
                            }

                            learningDataset.dataset[i] = avgMFCC;

                            learningDataset.labels[i] = getIndexOfLabel(song.getSongName(), labelArray);

                            i++;

                    }
                }

                return learningDataset;

        }

        /**
         * Get the LearningDataset object on a frame by frame basis.
         *
         * @param dataSongs The songs to use.
         * @param labelArray The array of label strings.
         * @param featureType The type of feature to extract into the dataset.
         *
         * @return The LearningDataset object corresponding to the given input.
         *
         */
        public static LearningDataset getFramewiseDataset(List<Song> dataSongs, String[] labelArray, FeatureType featureType) {

                LearningDataset learningDataset = new LearningDataset();

                if (featureType == FeatureType.SARANGI_MFCC) {

                    // Store the songs.
                    List<double[]> dataset = new ArrayList<double[]>();
                    List<int> labels = new ArrayList<int>();

                    for (Song song: dataSongs) {

                            List<double[]> mfcc = song.getMFCC();

                            dataset.addAll(mfcc);

                    }

                    learningDataset.dataset = new double[dataset.size()][];

                    for (int i = 0; i < dataset.size(); i++) {

                            learningDataset.dataset[i] = convertFloatsToDoubles(dataset.get(i));
                            learningDataset.labels[i] = getIndexOfLabel(song.getSongName(), labelArray);

                    }
                }

                return learningDataset;

        }
        
        /**
         * Get the index of the labelArray member which contains the given string.
         *
         * @param str The string which is supposed to contain a label string.
         * @param labelArray The array of labels.
         *
         * @return The index
         *
         */

        public static int getIndexOfLabel(String str, String[] labelArray) {

                for (int i=0; i<labelArray.length; i++) {
                        if (str.contains(labelArray[i])) {
                                return (i+1);
                        }
                }

                return -1;

        }

        /**
         * Convert the given int array to an array of doubles.
         *
         * @param arr The int array to be converted.
         *
         * @return The double array.
         *
         */

        public static double[] convertIntArrayToDoubleArray(int[] arr) {

                double[] retArray = new double[arr.length];
                for (int i=0; i<arr.length; i++) {
                        retArray[i] = (double) arr[i];
                }

                return retArray;
        }

        /**
         * Return array of doubles using an array of floats
         * 
         * @param input Array of floats
         *
         * @return Array of doubles
         *
         */
        public static double[] convertFloatsToDoubles(float[] input) {
            if (input == null) {
                return null; // Or throw an exception - your choice
            }

            double[] output = new double[input.length];

            for (int i = 0; i < input.length; i++) {
                output[i] = input[i];
            }
            return output;
        }


}
