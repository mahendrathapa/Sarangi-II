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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import com.sarangi.json.*;
import com.sarangi.structures.*;
import com.sarangi.learningmodel.ann.*;
import com.sarangi.learningmodel.svm.*;
import smile.math.kernel.GaussianKernel;
import smile.math.kernel.MercerKernel;

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

                try{

                        FileWriter fileWriter = new FileWriter(filename,true);

                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        final TypeToken<SarangiClassifier> requestListTypeToken = new TypeToken<SarangiClassifier>() {};

                        // Adding all different container classes with their flag
                        final RuntimeTypeAdapterFactory<SarangiClassifier> typeFactory = RuntimeTypeAdapterFactory
                        .of(SarangiClassifier.class, "type") 
                        .registerSubtype(SarangiANN.class, "NeuralNetwork")
                        .registerSubtype(SarangiSVM.class, "SVM");
                        
                        // Add the polymorphic specialization
                        final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(MercerKernel.class, new MercerKernelInstanceCreator())
                                                        .registerTypeAdapterFactory(typeFactory);
                        final Gson gson = gsonBuilder.create();

                        String json = gson.toJson(classifier);

                        bufferedWriter.write(json);
                        bufferedWriter.close();

                } catch(IOException ex){

                        System.out.println("Program Terminating");
                        System.exit(0);
                }
        }

        /**
         * Load the classifier from the specified file.
         *
         * @param filename The file from which to load the classifier.
         */
        public static SarangiClassifier loadClassifier(String filename, String classifierType) {

                SarangiClassifier classifier = getClassifier(classifierType);

                try{

                        File file = new File(filename);

                        if(file.length() == 0)
                                return classifier;

                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String jsonResponse = bufferedReader.readLine();

                        final TypeToken<SarangiClassifier> requestListTypeToken = new TypeToken<SarangiClassifier>() {};

                        // Adding all different container classes with their flag
                        final RuntimeTypeAdapterFactory<SarangiClassifier> typeFactory = RuntimeTypeAdapterFactory
                        .of(SarangiClassifier.class, "type") 
                        .registerSubtype(SarangiANN.class, "NeuralNetwork")
                        .registerSubtype(SarangiSVM.class, "SVM");
                        
                        // Add the polymorphic specialization
                        final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(MercerKernel.class, new MercerKernelInstanceCreator())
                                                        .registerTypeAdapterFactory(typeFactory);
                        final Gson gson = gsonBuilder.create();

                        classifier = gson.fromJson(jsonResponse, requestListTypeToken.getType());

                        bufferedReader.close();

                        return classifier;
                } catch(IOException ex){
                        return null;
                }


        }
}
