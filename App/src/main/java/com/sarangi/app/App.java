/*
 * @(#) App.java 2.0    June 9, 2016
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.app;

import java.io.*;
import java.util.*;

import com.beust.jcommander.JCommander;

import com.sarangi.app.commands.*;
import com.sarangi.learningmodel.ann.*;
import com.sarangi.learningmodel.svm.*;
import com.sarangi.learningmodel.*;
import com.sarangi.structures.*;
import com.sarangi.json.*;

/**
 * A main class for interfacing all the other sub-classes.
 *
 * <p>Includes the main method which extract the features of the training set and test set.
 *
 *
 *
 * @author  Mahendra Thapa
 *
 */

public class App
{

        /*CONSTRUCTORS **********************************************/

        private App(){

        }

        /**
         * Extract the features from the training and testing sets of data and stores in the
         * training.txt and testing.txt.
         *
         * @param   args    Take an argument from the command line terminal if any.
         *
         */

        public static void main( String[] args )
                throws FileNotFoundException, IOException
        {

                CommandLine cm = new CommandLine();

                JCommander jc = new JCommander(cm);

                CommandExtract extract = new CommandExtract();
                jc.addCommand("extract",extract);

                CommandTrain train = new CommandTrain();
                jc.addCommand("train",train);

                CommandTest test = new CommandTest();
                jc.addCommand("test",test);

                CommandClassify classify = new CommandClassify();
                jc.addCommand("classify",classify);

                String[] labels = {"classical","hiphop","jazz","pop","rock"};

                try {

                jc.parse(args);

                    if (jc.getParsedCommand().equals("extract")) {

                        FeatureExtractor.extractFeature(extract.file,extract.folder);

                    }else if(jc.getParsedCommand().equals("train")) {

                        ClassifierRunner runner = new ClassifierRunner(labels);
                        runner.storeClassifier(train.file, train.classifierFile,FeatureType.SARANGI_ALL,"SVM");

                    }else if(jc.getParsedCommand().equals("test")) {

                            if (test.kfoldFile != null) {
                                    ClassifierRunner runner = new ClassifierRunner(labels);
                                    runner.runCrossValidation(test.kfoldFile, FeatureType.SARANGI_ALL,10,"SVM");
                            }

                    }else if(jc.getParsedCommand().equals("classify")) {

                            // Extract Song Feature to temp file
                            SongHandler fileHandler = new SongHandler("src/resources/song/songFeatures/temp.txt");
                            List<Song> oneSong = new ArrayList<Song>();
                            oneSong.add(FeatureExtractor.extractSongFeature(classify.file));
                            fileHandler.storeSongs(oneSong);

                            SarangiClassifier classifier = ClassifierFactory.loadClassifier(classify.classifierFile,"SVM",labels,FeatureType.SARANGI_ALL);
                            int labelIndex = classifier.predict(oneSong.get(0));
                            System.out.println("Classification: "+labels[labelIndex-1]);

                    }

                } catch (Exception e) {
                        jc.usage();
                        System.exit(1);
                }

                if (cm.help || jc.getParsedCommand() == null) {
                        jc.usage();
                        System.exit(0);
                }

                /*
                String trainingFilename = "src/resources/song/songFeatures/features.txt";
                String testFilename = "src/resources/song/songFeatures/test.txt";

                FeatureExtractor.extractFeature(trainingFilename,new String("src/resources/song/Genre_training"));
                FeatureExtractor.extractFeature(testFilename,new String("src/resources/song/Genre_testing"));

                ClassifierRunner runner = new ClassifierRunner(new String[]{"classical","hiphop","jazz","pop","rock"});
                //ClassifierRunner runner = new ClassifierRunner(new String[]{"high_arousal","low_arousal"});
                //runner.runCrossValidation(trainingFilename, FeatureType.SARANGI_MFCC,10,"SVM");
                runner.run(trainingFilename,testFilename,FeatureType.SARANGI_ALL,"SVM");
                */
        }
}
