/*
 * @(#) App.java 2.0    June 9, 2016
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.app;

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

        public static void main( String[] args )
        {
       
              FeatureExtractor featureExtractor = new FeatureExtractor();
              
              featureExtractor.extractFeature(new String("src/resources/song/songFeatures/features.txt"),new String("src/resources/song/training"));
              featureExtractor.extractFeature(new String("src/resources/song/songFeatures/test.txt"),new String("src/resources/song/testing"));

        }
}


