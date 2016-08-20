/*
 * @(#) Config.java   2.0     August 20, 2016
 *
 * Bijay Gurung
 *
 * Institute of Engineering
 */

package com.sarangi.app;

import com.sarangi.structures.FeatureType;


/**
 * A class for storing the configurations of the application. 
 *
 * @author  Bijay Gurung
 */

public class Config {

    /**
     * The features to be used.
     *
     */
    public static FeatureType[] features = { 
        FeatureType.SARANGI_MELFREQ,
        //FeatureType.SARANGI_PITCH,
        //FeatureType.SARANGI_COMPACTNESS,
        //FeatureType.SARANGI_RHYTHM,
        //FeatureType.SARANGI_RMS,
        //FeatureType.SARANGI_SPECTRALCENTROID,
        //FeatureType.SARANGI_SPECTRALFLUX,
        //FeatureType.SARANGI_SPECTRALROLLOFFPOINT,
        //FeatureType.SARANGI_SPECTRALVARIABILITY,
        //FeatureType.SARANGI_ZEROCROSSING,
    };

    /**
     * The size of each dataset.
     * It is the sum of length of each feature used.
     * Default is 60.
     * But should be calculated at the start of application using 
     * calculateDatasetSize()
     * 
     */
    public static int DATASET_SIZE = 60;

    /*NEURAL NETWORK PARAMETERS**********************************/

    public static int ANN_EPOCH = 1000;

    public static double ANN_LEARNING_RATE = 0.1;
    public static double ANN_MOMENTUM = 0.0;
    public static double ANN_WEIGHT_DECAY = 0.0;

    public static int ANN_HIDDEN_NODES = 30;

    /*SVM PARAMETERS**********************************/

    public static int SVM_EPOCH = 10;

    public static double SVM_TOLERANCE = 1E-3;

    /**
     * Calculate the Dataset Size.
     * Adds the length of each feature.
     * Should be called at start-up in application entry point.
     *
     */
    public static void calculateDatasetSize() {

        int datasetSize = 0;

        for (FeatureType feature: features) {
            datasetSize += feature.getLength();
        }

        DATASET_SIZE = datasetSize;
    }


}
