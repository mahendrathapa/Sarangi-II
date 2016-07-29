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
        public Result(double accuracy, String[] labels, double[] labelAccuracy) {
                this.accuracy = accuracy;
                this.labels = labels;
                this.labelAccuracy = labelAccuracy;
        }




}
