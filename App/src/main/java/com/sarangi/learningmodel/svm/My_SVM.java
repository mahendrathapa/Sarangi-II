package com.sarangi.learningmodel.svm; 

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;

import java.lang.reflect.Type;

import com.sarangi.structures.*;

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.Math;
import java.lang.Math.*;

public class My_SVM {

        double training_dataset[][];
        int training_genre[];
        double test_dataset[][];
        int test_genre[];

        public My_SVM(){
                training_dataset = new double[450][1];
                training_genre = new int[450];
                test_dataset = new double[50][1];
                test_genre = new int[50];
        }


        public void readAllSongs (String training_filename,String test_filename) throws FileNotFoundException, IOException {
                Gson gson = new Gson();
                JsonReader training_json = new JsonReader(new FileReader(training_filename));
                JsonReader test_json = new JsonReader(new FileReader(test_filename));
 
                Type listType = new TypeToken<List<Song>>(){}.getType();
                List<Song> training_songs = gson.fromJson(training_json,listType);

                List<Song> test_songs = gson.fromJson(test_json,listType);

                int i=0;
                for (Song item: training_songs){
                    //    for(int j = 0; j < 30;j++){
                     //           training_dataset[i][j] = item.getMFCC()[j];
                      //  }
                        training_dataset[i][0] = item.getIntensity();
                        System.out.println("mfcc is being printed");
                        System.out.println(training_dataset[i]);
                       // System.out.println((Math.exp(item.getIntensity())));
                        if ( item.getSongName().contains("classic"))
                               training_genre[i] = 1;
                        else if (item.getSongName().contains("jazz"))
                                training_genre[i] = 2;
                        else if (item.getSongName().contains("rock"))
                                training_genre[i] = 3;
                        else if (item.getSongName().contains("pop"))
                                training_genre[i] = 4;
                        else // (item.getSongName().contains("hiphop"))
                                training_genre[i] = 5;
                        i++;
                }
                i=0;
                for (Song item : test_songs){
                     //   for(int j = 0; j < 30;j++){
                      //          test_dataset[i][j] = item.getMFCC()[j];
                       // }
                        test_dataset[i][0] = item.getIntensity();
                        if ( item.getSongName().contains("classic"))
                                test_genre[i] = 1;
                        else if (item.getSongName().contains("jazz"))
                                test_genre[i] = 2;
                        else if (item.getSongName().contains("rock"))
                                test_genre[i] = 3;
                        else if (item.getSongName().contains("pop"))
                                test_genre[i] = 4;
                        else // (item.getSongName().contains("hiphop"))
                                test_genre[i] = 5;
                        i++;
                }
                 }

        public void runSVM(){
                try {

                        SVM svm = new SVM(new GaussianKernel(8.0), 28.0, Math.max(training_genre)+1, SVM.Multiclass.ONE_VS_ONE);
                        svm.learn(training_dataset,training_genre);
                        svm.finish();

                        int error = 0;
                        for (int i = 0; i < test_dataset.length; i++){
                                if (svm.predict(test_dataset[i]) != test_genre[i]){
                                        error++;
                                }
                        }
                        System.out.format("Error rate = %.2f%%\n...........",100.0*error/test_dataset.length);
                        //rest of code done for svm more epoch to increase efficiency
                        int n = 0; 
                        for (int i = 0; i < n; i++){
                                System.out.println("Epoch........"+i);
                                for (int j = 0; j < training_dataset.length; j++){
                                        int index = Math.randomInt(training_dataset.length);
                                        svm.learn(training_dataset[index],training_genre[index]);

                                }
                                svm.finish();
                                error = 0;
                                for (int k = 0; k < test_dataset.length; k++){
                                        if (svm.predict(test_dataset[k]) != test_genre[k]){
                                                error++;
                                        }
                                }
                                System.out.format("Error rate = %.2f%%\n",100.0*error/test_dataset.length);
                        }
                }
                catch (Exception ex){
                        System.err.println(ex);
                }
        }

}