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
 * Support Vector Machine Class
 *
 * @author Mehang Rai
 * */


public class SarangiSVM extends SarangiClassifier {

        /* FIELDS **************************************************/

        /**
         * The SMILE SVM object
         *
         */

        public SVM svm; 

        public static double SIGMA = 60.0d;


        /* CONSTRUCTORS *******************************************/

        /**
         * Constructor.
         *
         */
        public SarangiSVM() {

        }
        
        /**
         * Constructor.
         *
         * @param trainingSongs The songs to be used for training
         * @param labels The string labels
         * @param featureType The type of feature to be used
         *
         */
        public SarangiSVM(List<Song>trainingSongs, String[] labels, FeatureType featureType) {

                super(trainingSongs, labels, featureType,"SVM");

        }

        /**
         * Train the model using SVM
         *
         * @param trainingSongs The songs to be used for training.
         *
         */

        @Override
        public void train(List<Song> trainingSongs) {
                this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels, featureType);

                svm = new SVM(new GaussianKernel(SarangiSVM.SIGMA), 2.0d, Math.max(trainingSet.labelIndices)+1, SVM.Multiclass.ONE_VS_ONE);
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

                // TODO Get a better solution than this Hack
                LearningDataset songDataset = DatasetUtil.getSongwiseDataset(oneSong,this.labels,this.featureType);
                System.out.println(Arrays.toString(songDataset.dataset[0]));
                return svm.predict(songDataset.dataset[0]);
        }


}
