/**
 * @(#) LearningDataset.java 2.0     July 27, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

import com.sarangi.structures.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Class for holding the dataset for training and testing
 *
 *
 * @author Bijay Gurung
 */

public class LearningDataset {

        /* FIELDS **************************************************/


        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("LearningDataset");

        /**
         * The actual data.
         * A two dimensional array of doubles.
         *
         */
        private double dataset[][];

        /**
         * The labels for the dataset.
         *
         */
        private int labels[];

        public enum FeatureType {

                SARANGI_MFCC, 
                SARANGI_PITCH,
                SARANGI_RHYTHM,
        }

        private FeatureType featureType = FeatureType.SARANGI_MFCC;
        

        /* CONSTRUCTORS *******************************************/

        /**
         * Initialize the fileName.
         * Also, set the level of the log according to the status in which the program is used.
         * The log levels are SEVERE, WARNING and INFO mainly.
         *
         */
        public LearningDataset(List<Song> dataSongs, String[] labelArray, FeatureType featureType) {

                logger.setLevel(Level.SEVERE);
                this.featureType = featureType;
                dataset = new double[dataSongs.size()][30];
                labels = new int[dataSongs.size()];

                if (this.featureType == FeatureType.SARANGI_PITCH || true ) {
                    int i = 0;
                    for (Song song: dataSongs) {

                            int[] pitch = song.getPitch();

                            dataset[i] = convertIntArrayToDoubleArray(pitch);

                            labels[i] = getIndexOfLabel(song.getSongName(), labelArray);

                            i++;

                    }
                }

        }

        /**
         * Get the dataset. 
         *
         * @return The dataset
         *
         */

        public double[][] getDataset() {
                return dataset;
        }

        /**
         * Get the label array.. 
         *
         * @return The label array.
         *
         */

        public int[] getLabels() {
                return labels;
        }

        public int getLength() {
                return dataset.length;
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


}
