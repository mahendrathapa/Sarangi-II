package com.sarangi.learningmodel.ann; 

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.Type;

import com.sarangi.structures.*;
import com.sarangi.json.SongHandler;
import com.sarangi.learningmodel.*;

import smile.classification.NeuralNetwork;
import smile.math.Math;
import java.lang.Math.*;

/**
 * Artificial Neural Network Class
 * for supervised learning of the music features
 *
 * @author Mehang Rai
 * */

public class SarangiANN implements SarangiClassifier {

        /* FIELDS **************************************************/

        /**
         * The NeuralNetwork object.
         *
         */
        public NeuralNetwork ann;

        /**
         * The labels.
         *
         */
        public String[] labels;

        /**
         * The Feature Type.
         *
         */
        public DatasetUtil.FeatureType featureType;

        /**
         * The training dataset.
         *
         */

        public LearningDataset trainingSet; 

        /**
         * The testing dataset.
         *
         */
        public LearningDataset testSet; 


        /**
         * Three argument constructor.
         *
         * @param trainingSongs The songs to be used for training
         * @param labels The string labels
         * @param featureType The type of feature to be used
         *
         */
        public SarangiANN(List<Song>trainingSongs, String[] labels, DatasetUtil.FeatureType featureType) {

                this.labels = labels;
                this.featureType = featureType;

                this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels, featureType);

                // Train ANN

                ann = new NeuralNetwork(NeuralNetwork.ErrorFunction.LEAST_MEAN_SQUARES,NeuralNetwork.ActivationFunction.LOGISTIC_SIGMOID,30,15,15);
                ann.learn(trainingSet.dataset,trainingSet.labelIndices);

        }

        /**
         * Predict the label for the given song.
         *
         * @param song The Song object whose label is to be predicted.
         *
         * @return The label index.
         */
        public int predict(Song song) {
                List<Song> oneSong = new ArrayList<Song>();
                oneSong.add(song);

                // TODO Get a better solution than this Hacky one.
                LearningDataset songDataset = DatasetUtil.getSongwiseDataset(oneSong,this.labels,this.featureType);
                return ann.predict(songDataset.dataset[0]);
        }

        /**
         * Tests the model on the given songs.
         * TODO This could be put into an abstract class 
         *
         * @param testSongs The test Songs.
         * @return The result of the test.
         */
        public Result test(List<Song> testSongs) {
                    int correct = 0;
                    double[] labelAccuracy = new double[this.labels.length];
                    double accuracy = 0.0;

                try{

                    for (Song song: testSongs) {

                            int labelIndex = DatasetUtil.getIndexOfLabel(song.getSongName(),this.labels);

                            if (this.predict(song) == labelIndex){
                                    labelAccuracy[labelIndex-1]++;
                                    correct++;
                            }
                    }

                    int numOfSongs = testSongs.size();

                    accuracy = (100.0*correct/numOfSongs);

                    for (int i=0; i<labelAccuracy.length; i++) {
                            labelAccuracy[i] = (100.0*labelAccuracy[i]/numOfSongs);
                    }


                }catch (Exception ex){
                        ex.printStackTrace();
                }
                    return new Result(accuracy,this.labels,labelAccuracy);
        }

}
