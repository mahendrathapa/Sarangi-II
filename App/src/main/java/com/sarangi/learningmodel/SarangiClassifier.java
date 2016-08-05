/**
 * @(#) SarangiClassifier.java 2.0     July 31, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

import com.sarangi.structures.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Interface for all Sarangi Classifiers.
 *
 *
 * @author Bijay Gurung
 */

public abstract class SarangiClassifier implements Classifier {

        /**
         * The labels.
         *
         */
        public String[] labels;

        /**
         * The Feature Type.
         *
         */
        public FeatureType featureType;

        /**
         * The training dataset.
         *
         */

        public LearningDataset trainingSet; 

        /**
         * Three argument constructor.
         *
         * @param trainingSongs The songs to be used for training
         * @param labels The string labels
         * @param featureType The type of feature to be used
         *
         */
        public SarangiClassifier(List<Song> trainingSongs, String[] labels, FeatureType featureType) {

                this.labels = labels;
                this.featureType = featureType;

                this.train(trainingSongs);

        }

        /**
         * Train the model using the given songs.
         * The specific model to be used is determined by the child class.
         *
         * @param trainingSongs The songs to be used for training.
         *
         */

        public abstract void train(List<Song> trainingSongs);

        /**
         * Predict the label for the given song.
         * How the prediction is done is dependent on the specific model being used.
         *
         * @param song The Song object whose label is to be predicted.
         *
         * @return The label index.
         */

        public abstract int predict(Song song);

        /**
         * Tests the model on the given songs.
         *
         * @param testSongs The test Songs.
         * @return The result of the test.
         */
        public Result test(List<Song> testSongs) {
                   int correct = 0;
                    double[] labelAccuracy = new double[this.labels.length];
                    double[] labelCount = new double[this.labels.length];
                    int[][] confusionMatrix = new int[this.labels.length][this.labels.length];
                    double accuracy = 0.0;

                try{

                    for (Song song: testSongs) {

                            int labelIndex = DatasetUtil.getIndexOfLabel(song.getSongName(),this.labels);

                            labelCount[labelIndex-1]++;
                            int predictedLabel = this.predict(song);

                            confusionMatrix[labelIndex-1][predictedLabel-1]++;

                            if (predictedLabel == labelIndex){
                                    labelAccuracy[labelIndex-1]++;
                                    correct++;
                            }
                    }

                    int numOfSongs = testSongs.size();

                    accuracy = (100.0*correct/numOfSongs);

                    for (int i=0; i<labelAccuracy.length; i++) {
                            labelAccuracy[i] = (100.0*labelAccuracy[i]/labelCount[i]);
                    }


                }catch (Exception ex){
                        ex.printStackTrace();
                }
                    return new Result(accuracy,this.labels,labelAccuracy,confusionMatrix);
  
        }

}
