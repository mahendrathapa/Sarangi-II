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
                String[] arousalLabels = {"low_arousal","high_arousal"};
                String[] valenceLabels = {"low_valence","high_valence"};

                List<String[]> labelsArray = new ArrayList<String[]>();
                labelsArray.add(labels);
                labelsArray.add(arousalLabels);
                labelsArray.add(valenceLabels);

                try {

                jc.parse(args);

                    if (jc.getParsedCommand().equals("extract")) {

                        FeatureExtractor.extractFeature(extract.file,extract.folder);

                    }else if(jc.getParsedCommand().equals("train")) {

                        ClassifierRunner runner = new ClassifierRunner(labelsArray.get(train.labelIndex));
                        runner.storeClassifier(train.file, train.classifierFile,FeatureType.SARANGI_ALL,"NeuralNetwork");

                    }else if(jc.getParsedCommand().equals("test")) {

                            if (test.kfoldFile != null) {
                                    ClassifierRunner runner = new ClassifierRunner(labelsArray.get(test.labelIndex));
                                    runner.runCrossValidation(test.kfoldFile, FeatureType.SARANGI_ALL,10,"NeuralNetwork");
                            }

                    }else if(jc.getParsedCommand().equals("classify")) {

                            // Extract Song Feature to temp file
                            SongHandler fileHandler = new SongHandler("src/resources/song/songFeatures/temp.txt");
                            List<Song> oneSong = new ArrayList<Song>();
                            oneSong.add(FeatureExtractor.extractSongFeature(classify.file));
                            fileHandler.storeSongs(oneSong);

                            //TODO Bind label to classifier.
                            int i = 0;
                            for (String classifierFile: classify.classifierFiles) {
                                System.out.println("Classifier: "+classifierFile);
                                SarangiClassifier classifier = ClassifierFactory.loadClassifier(classifierFile,"NeuralNetwork",labelsArray.get(i),FeatureType.SARANGI_ALL);
                                int labelIndex = classifier.predict(oneSong.get(0));
                                System.out.println("Classification: "+(labelsArray.get(i))[labelIndex-1]);
                                i++;
                            }

                    }

                } catch (Exception e) {
                        e.printStackTrace();
                        //jc.usage();
                        System.exit(1);
                }

                if (cm.help || jc.getParsedCommand() == null) {
                        jc.usage();
                        System.exit(0);
                }

        }
}
