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
//java -cp .;target/jlayer-1.0.1-1.jar;target/jorbis-0.0.17-2.jar;target/vorbisspi-1.0.3-1.jar;target/tritonus-share-0.3.7-2.jar;target/mp3spi-1.9.5-1.jar;target/gson-2.5.jar;target/App-2.0.jar;target/jtransforms-2.4.0.jar;src/lib/TarsosDSP-latest.jar com.sarangi.app.App
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
        {

                FeatureExtractor featureExtractor = new FeatureExtractor();

                featureExtractor.extractFeature(new String("src/resources/song/songFeatures/features.txt"),new String("src/resources/song/training"));
                featureExtractor.extractFeature(new String("src/resources/song/songFeatures/test.txt"),new String("src/resources/song/testing"));

        }
}
