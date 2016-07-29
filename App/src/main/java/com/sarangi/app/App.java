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

import com.sarangi.learningmodel.ann.MyANN;
import com.sarangi.learningmodel.svm.MySVM;
import com.sarangi.learningmodel.svm.SVMRunner;

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
                String trainingFilename = "src/resources/song/songFeatures/features.txt";
                String testFilename = "src/resources/song/songFeatures/test.txt";

               FeatureExtractor.extractFeature(new String("src/resources/song/songFeatures/features.txt"),new String("src/resources/song/training"));
               FeatureExtractor.extractFeature(new String("src/resources/song/songFeatures/test.txt"),new String("src/resources/song/testing"));

                SVMRunner svmRunner= new SVMRunner(new String[]{"hvha","hvla","lvla","lvha"});
                svmRunner.run(trainingFilename,testFilename);

        }
}
