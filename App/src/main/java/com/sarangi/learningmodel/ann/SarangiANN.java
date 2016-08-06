package com.sarangi.learningmodel.ann; 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.sarangi.structures.*;
import com.sarangi.json.SongHandler;
import com.sarangi.learningmodel.*;

import smile.classification.NeuralNetwork;
import smile.math.Math;
import java.lang.Math.*;

/**
 * Artificial Neural Network Class.
 * For supervised learning of the music features
 *
 * @author Mehang Rai
 * */

public class SarangiANN extends SarangiClassifier {

        /* FIELDS **************************************************/

        /**
         * The NeuralNetwork object.
         *
         */
        public NeuralNetwork ann;


        /**
         * Constructor.
         *
         * @param trainingSongs The songs to be used for training
         * @param labels The string labels
         * @param featureType The type of feature to be used
         *
         */
        public SarangiANN(List<Song>trainingSongs, String[] labels, FeatureType featureType) {

                super(trainingSongs,labels,featureType);

        }
        
        /**
         * Train the model in a Neural Network.
         *
         * @param trainingSongs The songs to be used for training.
         *
         */

        @Override
        public void train(List<Song> trainingSongs) {
                this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels, featureType);

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

        @Override 
        public int predict(Song song) {
                List<Song> oneSong = new ArrayList<Song>();
                oneSong.add(song);

                // TODO Get a better solution than this Hack
                LearningDataset songDataset = DatasetUtil.getSongwiseDataset(oneSong,this.labels,this.featureType);
                return ann.predict(songDataset.dataset[0]);
        }

 }
