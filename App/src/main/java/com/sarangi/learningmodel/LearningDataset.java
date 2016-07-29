/**
 * @(#) LearningDataset.java 2.0     July 27, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

/**
 * Structure Class for holding the dataset for training 
 *
 *
 * @author Bijay Gurung
 */

public class LearningDataset {

        /* FIELDS **************************************************/


        /**
         * The actual data.
         * A two dimensional array of doubles.
         *
         */
        public double dataset[][];

        /**
         * The labels for the dataset.
         *
         */
        public int labels[];

        /* CONSTRUCTORS *******************************************/

        /**
         * Empty Constructor.
         *
         */
        public LearningDataset() {
        }


}
