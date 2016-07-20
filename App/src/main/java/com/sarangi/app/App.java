/*
 * @(#) App.java 2.0    June 9, 2016
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.app;

import java.io.*;
import java.util.*;

import com.sarangi.learningmodel.ann.My_ANN;
import com.sarangi.learningmodel.svm.My_SVM;

/**
 * A main class for interfacing all the other sub-classes.
 *
 * <p>Includes the main method which extract the features of the training set and test set.
 *
 *
 *
 * @author  Mahendra Thapa
 *
 */

public class App
{

        /*CONSTRUCTORS **********************************************/

        private App(){

        }

        /**
         * Extract the features from the training and testing sets of data and stores in the
         * training.txt and testing.txt.
         *
         * @param   args    Take an argument from the command line terminal if any.
         *
         */

        public static void main( String[] args )
                throws FileNotFoundException, IOException
        {

                String training_filename = "src/resources/song/songFeatures/features.txt";
                String test_filename = "src/resources/song/songFeatures/test.txt";
                My_ANN ann = new My_ANN();
                ann.readAllSongs(training_filename,test_filename);
                ann.runANN();

               /* 
                My_SVM svm = new My_SVM();
                svm.readAllSongs(training_filename,test_filename);
                svm.runSVM();
*/

              //  FeatureExtractor featureExtractor = new FeatureExtractor();

 //               featureExtractor.extractFeature(new String("src/resources/song/songFeatures/features.txt"),new String("src/resources/song/training"));
              //  featureExtractor.extractFeature(new String("src/resources/song/songFeatures/test.txt"),new String("src/resources/song/testing"));

        }
}
