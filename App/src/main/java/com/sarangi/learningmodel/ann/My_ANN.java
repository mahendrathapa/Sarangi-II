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
import smile.classification.SVM; import smile.classification.NeuralNetwork; import smile.math.kernel.GaussianKernel; import smile.math.Math; import java.lang.Math.*;

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

public class My_ANN {

        /**
         * For training
         * Dataset containing the features of each music sample in double array
         * Genre containing the genre of music to which each music sample belong
         */
        private double training_songs_features_dataset[][];
        private int training_songs_genre[];

         /**
         * For training
         * Dataset containing the features of each music sample in double array
         * Genre containing the genre of music to which each music sample belong
         */
        private double testing_songs_features_dataset[][];
        private int testing_songs_genre[];


        /**Constructor for the class**/
        public My_ANN(){
                /*
                   training_dataset = new double[450][1];
                   training_genre = new int[450];
                   test_dataset = new double[50][1];
                   testing_genre = new int[50];
                   */
        }


        /**Method to read all song features from Json file and
         * assign the respective features and
         * the genre of each music sample to the dataset array
         */
        public void readAllSongs (String training_filename,String test_filename) throws FileNotFoundException, IOException {

                //Extraction of data from the json fileformat
                Gson gson = new Gson();
                JsonReader training_json = new JsonReader(new FileReader(training_filename));
                JsonReader test_json = new JsonReader(new FileReader(test_filename));

                Type listType = new TypeToken<List<Song>>(){}.getType();
                List<Song> training_songs = gson.fromJson(training_json,listType);
                List<Song> test_songs = gson.fromJson(test_json,listType);

                //features extraction to suitable array form for training
                List<float[]> training_songs_features_list = new ArrayList<float[]>();
                List<float[]> each_song_features_list = new ArrayList<float[]>();
                List<Float> intensity_list = new ArrayList<Float>();
                List<Float> all_intensity_list = new ArrayList<Float>();
                List<Float> all_pitch_list = new ArrayList<Float>();
                List<Float> pitch_list = new ArrayList<Float>();
                List<Integer> training_genre_list = new ArrayList<Integer>();

                for (Song item : training_songs){


                        each_song_features_list = item.getMelcoeff(); 
                        training_songs_features_list.addAll(each_song_features_list);
                        intensity_list = item.getIntensity();
                        all_intensity_list.addAll(intensity_list); 
                        pitch_list = item.getPitch();
                        all_pitch_list.addAll(pitch_list);

                        //Assigning genre to each frame
                        for(float[] each_frame_value : each_song_features_list){

                                if ( item.getSongName().contains("classic"))
                                        training_genre_list.add(1);
                                else if (item.getSongName().contains("jazz"))
                                        training_genre_list.add(2);
                                else if (item.getSongName().contains("rock"))
                                        training_genre_list.add(3);
                                else if (item.getSongName().contains("pop"))
                                        training_genre_list.add(4);
                                else            //for hiphop  
                                        training_genre_list.add(5);

                        }

                }
                System.out.println(training_genre_list.size());
                System.out.println(training_songs_features_list.size());

                training_songs_features_dataset = new double[training_songs_features_list.size()][32];
                training_songs_genre = new int[training_genre_list.size()];

                for(int frame_count = 0; frame_count < training_songs_features_list.size(); frame_count++){
                        training_songs_features_dataset[frame_count][0] = all_intensity_list.get(frame_count).doubleValue();
                        for(int mfcc_count = 1; mfcc_count < 31; mfcc_count++){
                        training_songs_features_dataset[frame_count][mfcc_count] = (double)training_songs_features_list.get(frame_count)[mfcc_count-1];
                        }
                        training_songs_features_dataset[frame_count][31] = all_pitch_list.get(frame_count).doubleValue();
                        training_songs_genre[frame_count] = training_genre_list.get(frame_count).intValue();
                }

                //features extraction to suitable array form for testing
                List<float[]> testing_songs_features_list = new ArrayList<float[]>();
                List<Integer> testing_genre_list = new ArrayList<Integer>();
                List<Float> test_intensity_list = new ArrayList<Float>();
                List<Float> test_all_intensity_list = new ArrayList<Float>();
                List<Float> test_pitch_list = new ArrayList<Float>();
                List<Float> test_all_pitch_list = new ArrayList<Float>();

                for (Song item : test_songs){
                        each_song_features_list =  item.getMelcoeff();
                        testing_songs_features_list.addAll(each_song_features_list);
                        test_intensity_list = item.getIntensity();
                        test_all_intensity_list.addAll(intensity_list); 
                        test_pitch_list = item.getPitch();
                        test_all_pitch_list.addAll(pitch_list);



                        //Assigning genre to each frame
                        for(float[] frame_value : each_song_features_list){
                                if ( item.getSongName().contains("classic"))
                                        testing_genre_list.add(1);
                                else if (item.getSongName().contains("jazz"))
                                        testing_genre_list.add(2);
                                else if (item.getSongName().contains("rock"))
                                        testing_genre_list.add(3);
                                else if (item.getSongName().contains("pop"))
                                        testing_genre_list.add(4);
                                else       //for hiphop
                                        testing_genre_list.add(5);
                        }
                }

                System.out.println(testing_genre_list.size());
                System.out.println(testing_songs_features_list.size());

                testing_songs_features_dataset = new double[testing_songs_features_list.size()][32];
                testing_songs_genre = new int[testing_genre_list.size()];

                for(int frame_count = 0; frame_count < testing_songs_features_list.size(); frame_count++){
                        testing_songs_features_dataset[frame_count][0] = test_all_intensity_list.get(frame_count).doubleValue();
                        for(int mfcc_count = 1; mfcc_count < 31; mfcc_count++){
                        testing_songs_features_dataset[frame_count][mfcc_count] = testing_songs_features_list.get(frame_count)[mfcc_count-1];
                        }
                        testing_songs_features_dataset[frame_count][0] = test_all_pitch_list.get(frame_count).doubleValue();
                        testing_songs_genre[frame_count] = testing_genre_list.get(frame_count).intValue();
                }

        }

        /**Method implements supervised artificial neural network learning
         * on training dataset
         * and predicts the accuracy through test dataset
         */

        public void runANN() throws IOException,FileNotFoundException{

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

                NeuralNetwork ann = new NeuralNetwork(NeuralNetwork.ErrorFunction.CROSS_ENTROPY,NeuralNetwork.ActivationFunction.SOFTMAX,32,10);
                int count = 0;
                for (int i=0;i<5;i++)
                        ann.learn(training_songs_features_dataset,training_songs_genre);
                int error = 0;
                for (int i = 0; i < testing_songs_features_dataset.length; i++){

                        if (ann.predict(testing_songs_features_dataset[i]) != testing_songs_genre[i]){
                                System.out.println("count"+count);
                                count = 0;
                                  ann.learn(testing_songs_features_dataset[i],testing_songs_genre[i]);
                                error++;
                        }
                        count++;

                }
                System.out.println("Number of errors : "+ error);
                System.out.format("Accuracy rate = %.2f%%\n...........",(100-100.0*error/testing_songs_features_dataset.length));



        }

}
