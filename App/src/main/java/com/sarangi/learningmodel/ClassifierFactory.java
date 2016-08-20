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

import com.thoughtworks.xstream.XStreamException;

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
    public static SarangiClassifier getClassifier(ClassifierType classifierType) {
        if (classifierType == null) {
            return null;
        }

        if (classifierType == ClassifierType.SARANGI_ANN){
            return new SarangiANN();
        }else if (classifierType == ClassifierType.SARANGI_SVM){
            return new SarangiSVM();
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
    public static SarangiClassifier getClassifier(List<Song> trainingSongs, String[] labels, ClassifierType classifierType) {
        if (classifierType == null) {
            return null;
        }

        if (classifierType == ClassifierType.SARANGI_ANN){
            return new SarangiANN(trainingSongs,labels);
        }else if (classifierType == ClassifierType.SARANGI_SVM){
            return new SarangiSVM(trainingSongs,labels);
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
    public static void storeClassifier(SarangiClassifier classifier, String filename) throws IOException, XStreamException {
        classifier.store(filename);
    }

    /**
     * Load the classifier from the specified file.
     *
     * @param filename The file from which to load the classifier.
     */
    public static SarangiClassifier loadClassifier(String filename, ClassifierType classifierType, String[] labels) 
        throws IOException, XStreamException {

        SarangiClassifier loadedClassifier = getClassifier(classifierType);

        loadedClassifier.setLabels(labels);

        loadedClassifier.load(filename);

        return loadedClassifier;

    }
}
