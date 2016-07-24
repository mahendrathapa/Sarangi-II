package com.sarangi.learningmodel.svm; 

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.List; import java.util.ArrayList;

import java.lang.reflect.Type;

import com.sarangi.structures.*;
import com.sarangi.json.*;

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.kernel.LinearKernel;
import smile.math.Math;
import java.lang.Math.*;

/**
 * Support Vector Machine Class
 * for supervised learning of the music features
 *
 * <p>Includes constructor for assigning the training and test dataset and its answer 
 *
 * <p>Includes method for reading JSON file and merging the features into an array
 *
 * <p>Includes method for machine learning and accuracy caculation
 *
 * @author Mehang Rai
 * */


public class MySVM {

        /**
         * For training
         * Dataset containing the features of each music sample in double array
         * Genre containing the genre of music to which each music sample belong
         */
        private double trainingSongsFeaturesDataset[][];
        private int trainingSongsGenre[];

        /**
         * For training
         * Dataset containing the features of each music sample in double array
         * Genre containing the genre of music to which each music sample belong
         */
        private double testingSongsFeaturesDataset[][];
        private int testingSongsGenre[];


        /**Construtor for the class**/
        public MySVM(){
                trainingSongsFeaturesDataset = new double[450][30]; 
                trainingSongsGenre = new int[450];
                testingSongsFeaturesDataset = new double[50][30];
                testingSongsGenre = new int[50];
        }


        /**Method to read all song features from Json file and
         * assign the respective features and
         * the genre of each music sample to the dataset array
         */
        public void readAllSongs (String trainingFilename,String testFilename) throws FileNotFoundException, IOException {

                JSONFormat jsonFormat = new JSONFormat();
                List<Song> trainingSongs = jsonFormat.convertJSONtoArray(trainingFilename);
                List<Song> testSongs = jsonFormat.convertJSONtoArray(testFilename);

                //features extraction to suitable array form for training
                int i = 0;
                for (Song item : trainingSongs){
                        int[] temp = item.getPitch();
                        for(int j=0;j<30;j++){
                       trainingSongsFeaturesDataset[i][j] = (double)temp[j]; 
                        }

                        //Assigning genre to each frame

                                if ( item.getSongName().contains("classic"))
                                        trainingSongsGenre[i] = 1;
                                else if (item.getSongName().contains("jazz"))
                                        trainingSongsGenre[i] = 2;
                                else if (item.getSongName().contains("rock"))
                                        trainingSongsGenre[i] = 3;
                                else if (item.getSongName().contains("pop"))
                                        trainingSongsGenre[i] = 4;
                                else            //for hiphop  
                                        trainingSongsGenre[i] = 5;

                                i++;
                }
                i = 0;
                for (Song item : testSongs){
                         int[] temp = item.getPitch();
                        for(int j=0;j<30;j++){
                       testingSongsFeaturesDataset[i][j] = (double)temp[j]; 
                        }
                        //Assigning genre to each frame
                                if ( item.getSongName().contains("classic"))
                                        testingSongsGenre[i] = 1;
                                else if (item.getSongName().contains("jazz"))
                                        testingSongsGenre[i] = 2;
                                else if (item.getSongName().contains("rock"))
                                        testingSongsGenre[i] = 3;
                                else if (item.getSongName().contains("pop"))
                                        testingSongsGenre[i] = 4;
                                else       //for hiphop
                                        testingSongsGenre[i] = 5;
                                i++;
                        }

        }

        /**Method implements support vector machine
         * on training dataset
         * and predicts the accuracy through test dataset
         */
        public void runSVM(){
                try {

                        SVM svm = new SVM(new GaussianKernel(60.0d), 8.0d, Math.max(trainingSongsGenre)+1, SVM.Multiclass.ONE_VS_ONE);
                        svm.learn(trainingSongsFeaturesDataset,trainingSongsGenre);
                        svm.finish();

                        int error = 0;
                        for (int i = 0; i < testingSongsFeaturesDataset.length; i++){
                                if (svm.predict(testingSongsFeaturesDataset[i]) != testingSongsGenre[i]){
                                        error++;
                                }
                        }
                        System.out.format("Accuracy rate = %.2f%%\n...........",(100-100.0*error/testingSongsFeaturesDataset.length));


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

                }
                catch (Exception ex){
                        System.err.println(ex);
                }
        }

}
