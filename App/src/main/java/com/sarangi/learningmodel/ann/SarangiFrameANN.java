/**
 * @(#) SarangiFrameANN.java 2.0     August 02, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */
package com.sarangi.learningmodel.ann; 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.sarangi.structures.*;
import com.sarangi.learningmodel.*;

import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.Math;
import java.lang.Math.*;
import java.util.*;

/**
 * ANN classifier that works on framewise data for each song.
 *
 * @author Bijay Gurung
 * */


public class SarangiFrameANN extends SarangiClassifier {

        /* FIELDS **************************************************/

        /**
         * The SMILE ANN object
         *
         */

        public NeuralNetwork ann; 


        /* CONSTRUCTORS *******************************************/

        /**
         * Constructor.
         *
         */
        public SarangiFrameANN() {

        }

        /**
         * Constructor.
         *
         * @param trainingSongs The songs to be used for training
         * @param labels The string labels
         * @param featureType The type of feature to be used
         *
         */
        public SarangiFrameANN(List<Song>trainingSongs, String[] labels, FeatureType featureType) {

                super(trainingSongs, labels, featureType, "FrameNeuralNetwork");

        }

        /**
         * Train the model using SVM
         *
         * @param trainingSongs The songs to be used for training.
         *
         */

        @Override
        public void train(List<Song> trainingSongs) {
                this.trainingSet = DatasetUtil.getFramewiseDataset(trainingSongs, labels, featureType);

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

                // TODO Get a better solution than this Hack.
                LearningDataset songDataset = DatasetUtil.getFramewiseDataset(oneSong,this.labels,this.featureType);
                int[] labelCount = new int[labels.length];

                for (int i=0; i < songDataset.dataset.length; i++) {
                        labelCount[ann.predict(songDataset.dataset[i]) - 1]++;
                }
                
                // The label in which most of the frames are predicted to be is the label for the song.

                int songLabel = 1;
                int baseCount = labelCount[0];

                for (int i=1; i < labelCount.length; i++) {
                        if (labelCount[i] > baseCount) {
                                baseCount = labelCount[i];
                                songLabel = i+1;
                        }
                }

                if (baseCount < songDataset.dataset.length) {
                        System.out.println("Doesn't have majority");
                }

                return songLabel;

        }

        public void store(String filename) {
        }
        public void load(String filename) {
        }

}
