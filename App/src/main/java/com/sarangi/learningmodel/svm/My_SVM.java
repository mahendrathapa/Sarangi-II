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

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
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


public class My_SVM {

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


        /**Construtor for the class**/
        public My_SVM(){
                /*
                   training_dataset = new double[450][1];
                   training_genre = new int[450];
                   testing_dataset = new double[50][1];
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
                List<Float> training_songs_features_list = new ArrayList<Float>();
                List<Float> each_song_features_list = new ArrayList<Float>();
                List<Integer> training_genre_list = new ArrayList<Integer>();

                for (Song item : training_songs){

                       each_song_features_list = item.getIntensity(); 
                        training_songs_features_list.addAll(each_song_features_list);

                        //Assigning genre to each frame
                        for(Float frame_value : each_song_features_list){

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

                training_songs_features_dataset = new double[training_songs_features_list.size()][1];
                training_songs_genre = new int[training_genre_list.size()];

                for(int frame_count = 0; frame_count < training_songs_features_list.size(); frame_count++){
                        training_songs_features_dataset[frame_count][0] = training_songs_features_list.get(frame_count).doubleValue();
                        training_songs_genre[frame_count] = training_genre_list.get(frame_count).intValue();
                }

                //features extraction to suitable array form for testing
                List<Float> testing_songs_features_list = new ArrayList<Float>();
                List<Integer> testing_genre_list = new ArrayList<Integer>();

                for (Song item : test_songs){
                        each_song_features_list =  item.getIntensity();
                        testing_songs_features_list.addAll(each_song_features_list);

                        //Assigning genre to each frame
                        for(Float frame_value : each_song_features_list){
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

                testing_songs_features_dataset = new double[testing_songs_features_list.size()][1];
                testing_songs_genre = new int[testing_genre_list.size()];


                for(int frame_count = 0; frame_count < testing_songs_features_list.size(); frame_count++){

                        testing_songs_features_dataset[frame_count][0] = testing_songs_features_list.get(frame_count).doubleValue();
                        testing_songs_genre[frame_count] = testing_genre_list.get(frame_count).intValue();
                }

        }

        /**Method implements support vector machine
         * on training dataset
         * and predicts the accuracy through test dataset
         */
        public void runSVM(){
                try {

                        SVM svm = new SVM(new GaussianKernel(8.0), 28.0, Math.max(training_songs_genre)+1, SVM.Multiclass.ONE_VS_ONE);
                        svm.learn(training_songs_features_dataset,training_songs_genre);
                        svm.finish();

                        int error = 0;
                        for (int i = 0; i < testing_songs_features_dataset.length; i++){
                                if (svm.predict(testing_songs_features_dataset[i]) != testing_songs_genre[i]){
                                        error++;
                                }
                        }
                        System.out.format("Accuracy rate = %.2f%%\n...........",(100-100.0*error/testing_songs_features_dataset.length));


                        //rest of code done for svm more epoch to increase efficiency
                        int epoch = 0; 
                        for (int i = 0; i < epoch; i++){
                                System.out.println("Epoch........"+i);
                                for (int j = 0; j < training_songs_features_dataset.length; j++){
                                        int index = Math.randomInt(training_songs_features_dataset.length);
                                        svm.learn(training_songs_features_dataset[index],training_songs_genre[index]);

                                }
                                svm.finish();
                                error = 0;
                                for (int k = 0; k < testing_songs_features_dataset.length; k++){
                                        if (svm.predict(testing_songs_features_dataset[k]) != testing_songs_genre[k]){
                                                error++;
                                        }
                                }

                                System.out.format("Error rate = %.2f%%\n",(100-100.0*error/testing_songs_features_dataset.length));
                        }

                }
                catch (Exception ex){
                        System.err.println(ex);
                }
        }

}
