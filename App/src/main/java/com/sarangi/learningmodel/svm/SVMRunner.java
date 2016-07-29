/**
 * @(#) SVMRunner.java 2.0     July 27, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel.svm; 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.logging.*;
import java.lang.reflect.Type;

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

import smile.classification.SVM;
import smile.classification.NeuralNetwork;
import smile.math.kernel.GaussianKernel;
import smile.math.Math;
import java.lang.Math.*;


/**
 * Class to Run SarangiSVM class 
 *
 * @author Bijay Gurung
 */


public class SVMRunner {

        private String[] labels;

        public SVMRunner(String[] labels) {
        
                this.labels = labels;
        }

        public void run(String trainingFilename, String testFilename) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SongHandler testSongHandler = new SongHandler(testFilename);
                List<Song> testSongs = testSongHandler.loadSongs();

                SarangiSVM svm = new SarangiSVM(trainingSongs,this.labels,DatasetUtil.FeatureType.SARANGI_MFCC);
                
                Result result = svm.test(testSongs);

                System.out.format("%.2f%%",result.accuracy);

        }

}
