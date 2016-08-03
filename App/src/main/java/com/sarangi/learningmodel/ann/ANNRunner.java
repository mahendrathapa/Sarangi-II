/**
 * @(#) ANNRunner.java 2.0     July 30, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel.ann; 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.logging.*;
import java.lang.reflect.Type;

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

import smile.classification.NeuralNetwork;
import smile.math.Math;
import java.lang.Math.*;


/**
 * Class to Run SarangiSVM class.
 *
 * @author Bijay Gurung
 */


public class ANNRunner extends ClassifierRunner {

        public ANNRunner(String[] labels) {
                super(labels);
        }

        public void run(String trainingFilename, String testFilename, DatasetUtil.FeatureType featureType) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SongHandler testSongHandler = new SongHandler(testFilename);
                List<Song> testSongs = testSongHandler.loadSongs();

                SarangiFrameANN ann = new SarangiFrameANN(trainingSongs,this.labels,featureType);
                
                Result result = ann.test(testSongs);

                result.printData();
        }

        /**
         * Run K-fold Cross Validation.
         *
         * TODO This and the one in SVMRunner could be abstracted out.
         *
         * @param filename The file holding the feature data.
         * @param featureType The type of feature to be used for testing.
         * @param k The number of partitions to create.
         *
         */

        public void runCrossValidation(String filename, DatasetUtil.FeatureType featureType, int k) throws FileNotFoundException, IOException  {

                SongHandler songHandler = new SongHandler(filename);
                List<Song> songs = songHandler.loadSongs();

                int partitionSize = songs.size()/k;
                Collections.shuffle(songs);

                double overallAvgAccuracy = 0.0;

                for (int i=0; i < k; i++) {
                        int lowerIndex = i*partitionSize;
                        int upperIndex = (i+1)*partitionSize;

                        List<Song> trainingSongs = new ArrayList<Song>(songs);
                        List<Song> testSongs = new ArrayList<Song>(trainingSongs.subList(lowerIndex,upperIndex));

                        trainingSongs.removeAll(testSongs);

                        SarangiANN ann = new SarangiANN(trainingSongs,this.labels,featureType);
                
                        Result result = ann.test(testSongs);

                        result.printData();

                        overallAvgAccuracy += result.getAccuracy();

                }

                System.out.format("K-fold accuracy: %.2f%%", overallAvgAccuracy/k);
        }

}
