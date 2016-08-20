/**
 * @(#) ClassifierRunner.java 2.0     July 31, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel; 

import com.sarangi.audioTools.Statistics;
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

        public void run(String trainingFilename, String testFilename, FeatureType featureType, ClassifierType classifierType) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SongHandler testSongHandler = new SongHandler(testFilename);
                List<Song> testSongs = testSongHandler.loadSongs();

                SarangiClassifier classifier = ClassifierFactory.getClassifier(trainingSongs,this.labels,classifierType);

                String classifierFile = new String("src/resources/song/classifier.txt");
                ClassifierFactory.storeClassifier(classifier,classifierFile);

                SarangiClassifier loadedClassifier = ClassifierFactory.loadClassifier(classifierFile,ClassifierType.SARANGI_SVM,this.labels);

                Result result = classifier.test(testSongs);
                Result result2 = loadedClassifier.test(testSongs);

                System.out.println("Direct classifier");
                result.printData();

                System.out.println("Classifier stored and loaded");
                result2.printData();

        }

        /**
         * Store the classifier.
         *
         *
         * @param trainingFilename The file where the training dataset is stored.
         * @param featureType The type of feature to be used for classification.
         * @param classifierType The type of classifier to run.
         *
         */

        public void storeClassifier(String trainingFilename, String classifierFile, ClassifierType classifierType) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SarangiClassifier classifier = ClassifierFactory.getClassifier(trainingSongs,this.labels,classifierType);

                ClassifierFactory.storeClassifier(classifier,classifierFile);

        }


        /**
         * Run K-fold Cross Validation.
         *
         * @param filename The file holding the feature data.
         * @param featureType The type of feature to be used for testing.
         * @param k The number of partitions to create.
         * @param classifierType The type of classifier to run.
         * @param random Shuffle the songs or create 'nice' partitions
         *
         */

        public void runCrossValidation(String filename, int k, ClassifierType classifierType, boolean random) throws FileNotFoundException, IOException  {

                SongHandler songHandler = new SongHandler(filename);
                List<Song> songs = songHandler.loadSongs();

                int partitionSize = songs.size()/k;

                if (random) {
                    Collections.shuffle(songs);
                } else {
                        List<Song> tempSongs = new ArrayList<Song>();

                        int numOfLabelSongsInPartition = partitionSize/this.labels.length;

                        int[] songIndex = new int[this.labels.length];

                        // For Every Partition
                        for (int i=0; i<k; i++) {

                                // For Every Label, get numOfLabelSongsInPartition songs.
                                for (int j=0; j<this.labels.length; j++) {

                                        int songCount = 0;
                                        while (songCount != numOfLabelSongsInPartition) {
                                            if (songs.get(songIndex[j]).getSongName().contains(this.labels[j])) {
                                                    tempSongs.add(songs.get(songIndex[j]));
                                                    songCount++;
                                            }
                                            songIndex[j]++;
                                        }
                                }
                        }

                        songs = tempSongs;
                }

                double overallAvgFMeasure = 0.0;
                double overallAvgPrecision = 0.0;
                double overallAvgRecall = 0.0;

                int[][] overallConfusionMatrix = new int[labels.length][labels.length];


                double[] overallAccuracies = new double[k];
                double[][] labelsAccuracy = new double[k][labels.length];

                ClassifierFactory factory = new ClassifierFactory();

                for (int i=0; i < k; i++) {
                        int lowerIndex = i*partitionSize;
                        int upperIndex = (i+1)*partitionSize;

                        List<Song> trainingSongs = new ArrayList<Song>(songs);
                        List<Song> testSongs = new ArrayList<Song>(trainingSongs.subList(lowerIndex,upperIndex));

                        trainingSongs.removeAll(testSongs);

                        SarangiClassifier classifier = factory.getClassifier(trainingSongs,this.labels, classifierType);

                        Result result = classifier.test(testSongs);

                        result.printData();

                        overallAccuracies[i] = result.accuracy;

                        overallAvgFMeasure += result.fMeasure;
                        overallAvgPrecision += result.precision;
                        overallAvgRecall += result.recall;

                        int[][] tempConfusionMatrix = result.getConfusionMatrix();

                        for (int j=0; j<labels.length; j++) {
                            for (int l=0; l<labels.length; l++) {
                                overallConfusionMatrix[j][l] += tempConfusionMatrix[j][l];
                            }
                        }

                        for (int j=0; j<labels.length; j++) {
                            labelsAccuracy[i][j] += result.getLabelAccuracy(j);
                        }

                }

                System.out.format("\n\nResults with %d-fold cross validation\n\n",k);

                System.out.println("Overall Confusion Matrix");

                for (int i=0; i<overallConfusionMatrix.length; i++ ){
                    for (int j=0; j<overallConfusionMatrix[i].length; j++) {
                        System.out.format("%2d ",overallConfusionMatrix[i][j]);
                    }
                    System.out.println();
                }

                System.out.println();

                for (int i=0; i<labels.length; i++) {
                    System.out.format("%10s : %.2f%% +- %.2f%%\n",labels[i],
                            Statistics.getAverage(labelsAccuracy[i]),
                            Statistics.getStandardDeviation(labelsAccuracy[i]));
                }

                System.out.println();
                System.out.format("[====> K-fold accuracy: %.2f%% <====]\n\n", Statistics.getAverage(overallAccuracies));
                System.out.format("[====> K-fold Precision: %.2f <====]\n\n", overallAvgPrecision/k);
                System.out.format("[====> K-fold Recall: %.2f <====]\n\n", overallAvgRecall/k);
                System.out.format("[====> K-fold FMeasure: %.2f <====]\n\n", overallAvgFMeasure/k);

        }
}
