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
import com.sarangi.json.JSONFormat;

import smile.classification.NeuralNetwork;
import smile.math.Math;
import java.lang.Math.*;

/**
 * Artificial Neural Network Class
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

public class MyANN {

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

        //stores the number of frames each song has which is used in voting system during testing
        List<Integer> songsTotalFrameList; 


        /**Constructor for the class**/
        public MyANN(){
                songsTotalFrameList = new ArrayList<Integer>();
        }


        /**Method to read all song features from Json file and
         * assign the respective features and
         * the genre of each music sample to the dataset array
         */
        public void readAllSongs (String trainingFilename,String testFilename) throws FileNotFoundException, IOException {

                //Extraction of data from the json fileformat

                JSONFormat json = new JSONFormat();
                List<Song> trainingSongs = json.convertJSONtoArray(trainingFilename);
                List<Song> testSongs = json.convertJSONtoArray(testFilename);

                //features extraction to suitable array form for training
                List<float[]> trainingSongsFeaturesList = new ArrayList<float[]>();
                List<float[]> eachSongFeaturesList = new ArrayList<float[]>();
                List<Float> trainingIntensityList = new ArrayList<Float>();
                List<Float> allIntensityList = new ArrayList<Float>();
                List<Integer> trainingGenreList = new ArrayList<Integer>();

                for (Song item : trainingSongs){

                        eachSongFeaturesList = item.getMelcoeff(); 
                        trainingSongsFeaturesList.addAll(eachSongFeaturesList);
                        trainingIntensityList = item.getIntensity();
                        allIntensityList.addAll(trainingIntensityList); 

                        //Assigning genre to each frame
                        for(float[] eachFrameValue : eachSongFeaturesList){

                                if ( item.getSongName().contains("classic"))
                                        trainingGenreList.add(1);
                                else if (item.getSongName().contains("jazz"))
                                        trainingGenreList.add(2);
                                else if (item.getSongName().contains("rock"))
                                        trainingGenreList.add(3);
                                else if (item.getSongName().contains("pop"))
                                        trainingGenreList.add(4);
                                else            //for hiphop  
                                        trainingGenreList.add(5);

                        }

                }

                trainingSongsFeaturesDataset = new double[trainingSongsFeaturesList.size()][31];
                trainingSongsGenre = new int[trainingGenreList.size()];

                for(int frameCount = 0; frameCount < trainingSongsFeaturesList.size(); frameCount++){
                        trainingSongsFeaturesDataset[frameCount][0] = allIntensityList.get(frameCount).doubleValue();
                        for(int mfccCount = 1; mfccCount < 31; mfccCount++){
                        trainingSongsFeaturesDataset[frameCount][mfccCount] = (double)trainingSongsFeaturesList.get(frameCount)[mfccCount-1];
                        }
                        trainingSongsGenre[frameCount] = trainingGenreList.get(frameCount).intValue();
                }

                //features extraction to suitable array form for testing
                List<float[]> testingSongsFeaturesList = new ArrayList<float[]>();
                List<Integer> testingGenreList = new ArrayList<Integer>();
                List<Float> testIntensityList = new ArrayList<Float>();
                List<Float> testAllIntensityList = new ArrayList<Float>();

                for (Song item : testSongs){
                        eachSongFeaturesList =  item.getMelcoeff();
                        testingSongsFeaturesList.addAll(eachSongFeaturesList);
                        testIntensityList = item.getIntensity();
                        testAllIntensityList.addAll(testIntensityList); 

                        songsTotalFrameList.add(testIntensityList.size());

                        //Assigning genre to each frame
                        for(float[] frameValue : eachSongFeaturesList){
                                if ( item.getSongName().contains("classic"))
                                        testingGenreList.add(1);
                                else if (item.getSongName().contains("jazz"))
                                        testingGenreList.add(2);
                                else if (item.getSongName().contains("rock"))
                                        testingGenreList.add(3);
                                else if (item.getSongName().contains("pop"))
                                        testingGenreList.add(4);
                                else       //for hiphop
                                        testingGenreList.add(5);
                        }
                }


                testingSongsFeaturesDataset = new double[testingSongsFeaturesList.size()][31];
                testingSongsGenre = new int[testingGenreList.size()];

                for(int frameCount = 0; frameCount < testingSongsFeaturesList.size(); frameCount++){
                        testingSongsFeaturesDataset[frameCount][0] = testAllIntensityList.get(frameCount).doubleValue();
                        for(int mfccCount = 1; mfccCount < 31; mfccCount++){
                        testingSongsFeaturesDataset[frameCount][mfccCount] = testingSongsFeaturesList.get(frameCount)[mfccCount-1];
                        }
                        testingSongsGenre[frameCount] = testingGenreList.get(frameCount).intValue();
                }

        }

        /**Method implements supervised artificial neural network learning
         * on training dataset
         * and predicts the accuracy through test dataset
         */

        public void runANN() throws IOException,FileNotFoundException{

             
                NeuralNetwork ann = new NeuralNetwork(NeuralNetwork.ErrorFunction.LEAST_MEAN_SQUARES,NeuralNetwork.ActivationFunction.LOGISTIC_SIGMOID,31,25,15);
                int loop = 1;
                for(int i = 0;i < loop; i++)
                        ann.learn(trainingSongsFeaturesDataset,trainingSongsGenre);

                int errorSongsCount = 0;
                int frameCounter = 0;
                int correctFrame = 0; //yes vote for a song
                int errorFrame = 0; //no vote for a song
                int startFrame = 0;
                int endFrame = songsTotalFrameList.get(0);
                for(int testSongsCount = 1; testSongsCount <= songsTotalFrameList.size(); testSongsCount++){
                        errorFrame = 0;
                        correctFrame = 0;
                        for(; frameCounter < endFrame; frameCounter++){

                                if (ann.predict(testingSongsFeaturesDataset[frameCounter]) != testingSongsGenre[frameCounter]){
                                        //ann.learn(testingSongsFeaturesDataset[frameCounter],testingSongsGenre[frameCounter]);
                                        errorFrame++;
                                }
                                else
                                        correctFrame++;
                        }

                          //      System.out.println("'''''''" );
                           //     System.out.println("correct Frame"+correctFrame );
                            //    System.out.println("error Frame"+errorFrame);
                        if(errorFrame > correctFrame)
                                errorSongsCount++;


                        if(testSongsCount <= 49){
                        endFrame = endFrame +songsTotalFrameList.get(testSongsCount);
                        }
                }
                System.out.println(songsTotalFrameList.get(0));
                System.out.println("Number of errors : "+ errorSongsCount);
                System.out.format("Accuracy rate = %.2f%%\n...........",(100-100.0*errorSongsCount/50.0));



        }
   /* 
                 * code to test for efficiency of library
                //done for the testing the efficiency of code
                double train_data[][] = new double[200][2];
                int train_ans[] = new int[200];
                double test_data[][] = new double[20000][2];
                int test_ans[] = new int[20000];
                FileReader trainfile = new FileReader("src/resources/SongFeatures/ann-train.txt");
                Scanner train_scan = new Scanner(trainfile);
                int train_index=0;
                while(train_scan.hasNextLine()){
                String train_s = train_scan.nextLine();
                if(!train_s.isEmpty()){
                Scanner train_linescan = new Scanner(train_s);
                while(train_linescan.hasNextDouble()){
                train_ans[train_index] = (int)train_linescan.nextDouble();
                train_data[train_index][0] = train_linescan.nextDouble();
                train_data[train_index][1] = train_linescan.nextDouble();
                train_index++;
                }
                train_linescan.close();
                }
                else{
                continue;
                }
                }
                train_scan.close();
                trainfile.close();
                FileReader testfile = new FileReader("src/resources/SongFeatures/ann-test.txt");
                Scanner test_scan = new Scanner(testfile);
                int test_index=0;
                while(test_scan.hasNextLine()){
                String test_s = test_scan.nextLine();
                if(!test_s.isEmpty()){
                Scanner test_linescan = new Scanner(test_s);
                while(test_linescan.hasNextDouble()){
                test_ans[test_index] = (int)test_linescan.nextDouble();
                test_data[test_index][0] = test_linescan.nextDouble();
                test_data[test_index][1] = test_linescan.nextDouble();
                test_index++;
                }
                test_linescan.close();
                }
                else{
                continue;
                }
                }
                test_scan.close();
                testfile.close();

*/


}
