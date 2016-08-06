/**
 * @(#) Result.java 2.0     July 29, 2016
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
 * Class for holding result of a classification.
 *
 * @author Bijay Gurung
 */

public class Result {

        /* FIELDS **************************************************/

        /**
         * The actual data.
         * A two dimensional array of doubles.
         *
         */
        public double accuracy;

        /**
         * The labels for the dataset.
         *
         */
        public String[] labels;

        /**
         * The individual accuracy for each label.
         *
         */
        public double[] labelAccuracy;

        /**
         * The Confusion Matrix
         *
         */
        public int[][] confusionMatrix;

        /* CONSTRUCTORS *******************************************/

        /**
         * Empty Constructor.
         *
         */
        public Result() {
        }

        /**
         * Initialization in the constructor.
         *
         * @param accuracy The overall accuracy.
         * @param labels The strings representing the labels. 
         * @param labelAccuracy The accuracy for each label.
         *
         */
        public Result(double accuracy, String[] labels, double[] labelAccuracy, int[][] confusionMatrix) {
                this.accuracy = accuracy;
                this.labels = labels;
                this.labelAccuracy = labelAccuracy;
                this.confusionMatrix = confusionMatrix;
        }

        /**
         * Print all data contained by the object.
         *
         */
        public void printData() {
                System.out.format("Overall Accuracy: %.2f%%\n",this.accuracy);

                if (labels.length != labelAccuracy.length) {
                        System.out.println("Error: Number of labels and accuracy count don't match");
                        return;
                }

                for (int i=0; i<labels.length; i++) {
                    System.out.format("Label %s: %.2f%%\n",this.labels[i],this.labelAccuracy[i]);
                }

                for (int i=0; i<confusionMatrix.length; i++ ){
                        for (int j=0; j<confusionMatrix[i].length; j++) {
                                System.out.format("%d ",confusionMatrix[i][j]);
                        }
                        System.out.println();
                }
        }

        /**
         * Get the accuracy.
         *
         *
         */
        public double getAccuracy() {
                return accuracy;
        }

}
