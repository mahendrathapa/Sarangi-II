/**
 * @(#) ClassifierRunner.java 2.0     July 31, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel; 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

/**
 * Class to handle all the necessary classifiers.
 * The main method can work with this class instead of directly dealing 
 * with the classifiers.
 *
 * @author Bijay Gurung
 */


public class ClassifierRunner {

        /**
         * The labels used by the classifiers.
         *
         */
        protected String[] labels;

        /**
         * Constructor.
         *
         */

        public ClassifierRunner(String[] labels) {
        
                this.labels = labels;
        }

        /**
         * Run the given classifier. 
         *
         *
         * @param trainingFilename The file where the training dataset is stored.
         * @param testFilename The file where the test dataset is stored.
         * @param featureType The type of feature to be used for classification.
         * @param classifierType The type of classifier to run.
         *
         */

        public void run(String trainingFilename, String testFilename, FeatureType featureType, String classifierType) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SongHandler testSongHandler = new SongHandler(testFilename);
                List<Song> testSongs = testSongHandler.loadSongs();


                SarangiClassifier classifier = ClassifierFactory.getClassifier(trainingSongs,this.labels,featureType,classifierType);

                String classifierFile = new String("src/resources/song/classifier.txt");
                ClassifierFactory.storeClassifier(classifier,classifierFile);

                SarangiClassifier loadedClassifier = ClassifierFactory.loadClassifier(classifierFile,"SVM",this.labels,featureType);

                Result result = loadedClassifier.test(testSongs);

                result.printData();

        }

        /**
         * Run K-fold Cross Validation.
         *
         * @param filename The file holding the feature data.
         * @param featureType The type of feature to be used for testing.
         * @param k The number of partitions to create.
         * @param classifierType The type of classifier to run.
         *
         */

        public void runCrossValidation(String filename, FeatureType featureType, int k, String classifierType) throws FileNotFoundException, IOException  {

                SongHandler songHandler = new SongHandler(filename);
                List<Song> songs = songHandler.loadSongs();

                int partitionSize = songs.size()/k;
                Collections.shuffle(songs);

                double overallAvgAccuracy = 0.0;

                ClassifierFactory factory = new ClassifierFactory();

                for (int i=0; i < k; i++) {
                        int lowerIndex = i*partitionSize;
                        int upperIndex = (i+1)*partitionSize;

                        List<Song> trainingSongs = new ArrayList<Song>(songs);
                        List<Song> testSongs = new ArrayList<Song>(trainingSongs.subList(lowerIndex,upperIndex));

                        trainingSongs.removeAll(testSongs);

                        SarangiClassifier classifier = factory.getClassifier(trainingSongs,this.labels,featureType, classifierType);
                
                        Result result = classifier.test(testSongs);

                        result.printData();

                        overallAvgAccuracy += result.getAccuracy();

                }

                System.out.format("K-fold accuracy: %.2f%%", overallAvgAccuracy/k);

        }
}
