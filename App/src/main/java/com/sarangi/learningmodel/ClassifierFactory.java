/**
 * @(#) ClassifierFactory.java 2.0     August 04, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel; 

import java.util.*;
import java.io.*;

import com.sarangi.json.*;
import com.sarangi.structures.*;
import com.sarangi.learningmodel.ann.*;
import com.sarangi.learningmodel.svm.*;
import smile.math.kernel.GaussianKernel;

/**
 * Class to produce instance of required classifier.
 *
 * @author Bijay Gurung
 */


public class ClassifierFactory {

        /**
         * Get an empty classifier instance.
         *
         * @param classifierType The type of classifier instance to return.
         *
         * @return A SarangiClassifier instance as requested.
         *
         */
        public static SarangiClassifier getClassifier(String classifierType) {
                if (classifierType == null) {
                        return null;
                }

                if (classifierType.equalsIgnoreCase("NeuralNetwork")){
                        return new SarangiANN();
                }else if (classifierType.equalsIgnoreCase("SVM")){
                        return new SarangiSVM();
                }else if (classifierType.equalsIgnoreCase("FRAMEWISENEURALNETWORK")){
                        return new SarangiFrameANN();
                }else if (classifierType.equalsIgnoreCase("FRAMEWISESVM")){
                        return new SarangiFrameSVM();
                }

                return null;
        }

        /**
         * Get the required classifier instance.
         *
         * @param trainingSongs The songs to be used for training the classifier.
         * @param labels The labels to be used for training the classifier.
         * @param featureType The type of feature to be used for classification.
         * @param classifierType The type of classifier instance to return.
         *
         * @return A SarangiClassifier instance as requested.
         *
         */
        public static SarangiClassifier getClassifier(List<Song> trainingSongs, String[] labels, FeatureType featureType, String classifierType) {
                if (classifierType == null) {
                        return null;
                }

                if (classifierType.equalsIgnoreCase("NeuralNetwork")){
                        return new SarangiANN(trainingSongs,labels,featureType);
                }else if (classifierType.equalsIgnoreCase("SVM")){
                        return new SarangiSVM(trainingSongs,labels,featureType);
                }else if (classifierType.equalsIgnoreCase("FRAMEWISENEURALNETWORK")){
                        return new SarangiFrameANN(trainingSongs,labels,featureType);
                }else if (classifierType.equalsIgnoreCase("FRAMEWISESVM")){
                        return new SarangiFrameSVM(trainingSongs,labels,featureType);
                }

                return null;
        }

        /**
         * Store the classifier in the specified file.
         *
         * @param classifier The SarangiClassifier to be stored.
         * @param filename The file in which to store the classifier.
         * TODO Implement a custom JsonSerializer/JsonDeserializer
         */
        public static void storeClassifier(SarangiClassifier classifier, String filename) {

                classifier.store(filename);
        }

        /**
         * Load the classifier from the specified file.
         *
         * @param filename The file from which to load the classifier.
         */
        public static SarangiClassifier loadClassifier(String filename, String classifierType, String[] labels, FeatureType featureType) {

                SarangiClassifier loadedClassifier = getClassifier(classifierType);

                loadedClassifier.setLabels(labels);
                loadedClassifier.setFeatureType(featureType);

                loadedClassifier.load(filename);

                return loadedClassifier;

        }
}
