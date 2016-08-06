/**
 * @(#) ClassifierFactory.java 2.0     August 04, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel; 

import java.util.*;

import com.sarangi.structures.*;
import com.sarangi.learningmodel.ann.*;
import com.sarangi.learningmodel.svm.*;

/**
 * Class to produce instance of required classifier.
 *
 * @author Bijay Gurung
 */


public class ClassifierFactory {

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
        public SarangiClassifier getClassifier(List<Song> trainingSongs, String[] labels, FeatureType featureType, String classifierType) {
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

}
