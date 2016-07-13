package com.sarangi.learningmodel.ann; 

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;

import java.lang.reflect.Type;

import com.sarangi.structures.Song;

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.Math;
import java.lang.Math.*;

public class My_ANN {

        double training_dataset[][];
        int training_genre[];
        double test_dataset[][];
        int test_genre[];

        public My_ANN(){
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

        public void runANN() throws IOException,FileNotFoundException{
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



                NeuralNetwork ann = new NeuralNetwork(NeuralNetwork.ErrorFunction.CROSS_ENTROPY,NeuralNetwork.ActivationFunction.SOFTMAX,1,20);
                for (int i=0;i<150;i++)
                ann.learn(training_dataset,training_genre);
                int error = 0;
                for (int i = 0; i < test_dataset.length; i++){
                        
                        if (ann.predict(test_dataset[i]) != test_genre[i]){
                                System.out.println(ann.predict(test_dataset[i]));
                                System.out.println(ann.predict(test_dataset[i]));
                                error++;
                        }
                }
                System.out.format("Error rate = %.2f%%\n...........",100.0*error/test_dataset.length);

//need to do something

        }

}
