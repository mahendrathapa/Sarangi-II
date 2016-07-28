package com.sarangi.learningmodel.svm; 

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
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.Math;
import java.lang.Math.*;

/**
 * Support Vector Machine Class
 *
 * @author Mehang Rai
 * */


public class MySVM {

        /* FIELDS **************************************************/

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

        /* CONSTRUCTORS *******************************************/

        /**
         * Two argument constructor.
         *
         * @param trainingFilename The file containing the songs for training.
         * @param testFilename The file containing the songs for testing..
         *
         */
        public MySVM(String trainingFilename, String testFilename) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SongHandler testSongHandler = new SongHandler(testFilename);
                List<Song> testSongs = testSongHandler.loadSongs();

                trainingSet = new LearningDataset(trainingSongs, new String[] {"classic","hiphop","jazz","pop","rock"},
                                                  LearningDataset.FeatureType.SARANGI_PITCH);

                testSet = new LearningDataset(testSongs, new String[] {"classic","hiphop","jazz","pop","rock"},
                                                  LearningDataset.FeatureType.SARANGI_PITCH);
        }

        /**
         * Runs SVM on the datasets. 
         *
         */
        public void runSVM(){
                try {

                        SVM svm = new SVM(new GaussianKernel(60.0d), 8.0d, Math.max(trainingSet.getLabels())+1, SVM.Multiclass.ONE_VS_ONE);
                        svm.learn(trainingSet.getDataset(),trainingSet.getLabels());
                        svm.finish();
                        /*
                        int classicalError = 0;
                        int hiphopError = 0;
                        int jazzError = 0;
                        int popError = 0;
                        int rockError = 0;
                        */

                        int error = 0;
                        for (int i = 0; i < testSet.getLength(); i++){
                                double[][] testSetDataset = testSet.getDataset();
                                int[] testLabels = testSet.getLabels();
                                if (svm.predict(testSetDataset[i]) != testLabels[i]){
                                        error++;
                                        /*
                                        if(testingSongsGenre[i]==1)
                                        {
                                                classicalError++;
                                        }
                                        else if(testingSongsGenre[i]==2)
                                        {
                                                hiphopError++;
                                        }
                                        else if(testingSongsGenre[i]==3)
                                        {
                                                jazzError++;
                                        }
                                        else if(testingSongsGenre[i]==4)
                                        {
                                                popError++;
                                        }
                                        else if(testingSongsGenre[i]==4)
                                        {
                                                rockError++;
                                        }
                                        */
                                }
                        }
                        /*
                        System.out.println("Error for classical "+classicalError);
                        System.out.println("Error for hiphop "+hiphopError);
                        System.out.println("Error for jazz "+jazzError);
                        System.out.println("Error for pop "+popError);
                        System.out.println("Error for rock "+rockError);
                        */
                        System.out.format("Accuracy rate = %.2f%%\n...........",(100-100.0*error/testSet.getLength()));


                        /*
                        //rest of code done for svm more epoch to increase efficiency
                        int epoch = 0; 
                        for (int i = 0; i < epoch; i++){
                                System.out.println("Epoch........"+i);
                                for (int j = 0; j < trainingSongsFeaturesDataset.length; j++){
                                        int index = Math.randomInt(trainingSongsFeaturesDataset.length);
                                        svm.learn(trainingSongsFeaturesDataset[index],trainingSongsGenre[index]);

                                }
                                svm.finish();
                                error = 0;
                                for (int k = 0; k < testingSongsFeaturesDataset.length; k++){
                                        if (svm.predict(testingSongsFeaturesDataset[k]) != testingSongsGenre[k]){
                                                error++;
                                        }
                                }

                                System.out.format("Error rate = %.2f%%\n",(100-100.0*error/testingSongsFeaturesDataset.length));
                        }
                        */

                }
                catch (Exception ex){
                        System.err.println(ex);
                }
        }


}
