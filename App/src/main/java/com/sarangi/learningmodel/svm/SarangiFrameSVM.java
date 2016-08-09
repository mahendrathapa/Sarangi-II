/**
 * @(#) SarangiFrameSVM.java 2.0     August 01, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */
package com.sarangi.learningmodel.svm; 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.Math;
import java.lang.Math.*;
import java.util.*;

/**
 * SVM that uses framewise data for each song.
 *
 * @author Bijay Gurung
 * */


public class SarangiFrameSVM extends SarangiClassifier {

        /* FIELDS **************************************************/

        /**
         * The SMILE SVM object
         *
         */

        public SVM svm; 


        /* CONSTRUCTORS *******************************************/

        /**
         * Constructor.
         *
         */
        public SarangiFrameSVM() {

        }

        /**
         * Constructor.
         *
         * @param trainingSongs The songs to be used for training
         * @param labels The string labels
         * @param featureType The type of feature to be used
         *
         */
        public SarangiFrameSVM(List<Song>trainingSongs, String[] labels, FeatureType featureType) {

                super(trainingSongs, labels, featureType);

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

                svm = new SVM(new GaussianKernel(60.0d), 2.0d, Math.max(trainingSet.labelIndices)+1, SVM.Multiclass.ONE_VS_ONE);
                svm.learn(trainingSet.dataset,trainingSet.labelIndices);
                svm.finish();
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

                // TODO Get a better solution than this Hacky one.
                LearningDataset songDataset = DatasetUtil.getFramewiseDataset(oneSong,this.labels,this.featureType);
                int[] labelCount = new int[labels.length];

                for (int i=0; i < songDataset.dataset.length; i++) {
                        labelCount[svm.predict(songDataset.dataset[i]) - 1]++;
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
