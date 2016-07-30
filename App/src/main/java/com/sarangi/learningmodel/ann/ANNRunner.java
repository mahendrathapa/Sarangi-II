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
 * TODO This and SVMRunner could have an abstract parent class
 *
 * @author Bijay Gurung
 */


public class ANNRunner {

        private String[] labels;

        public ANNRunner(String[] labels) {
        
                this.labels = labels;
        }

        public void run(String trainingFilename, String testFilename) throws FileNotFoundException, IOException  {

                SongHandler trainingSongHandler = new SongHandler(trainingFilename);
                List<Song> trainingSongs = trainingSongHandler.loadSongs();

                SongHandler testSongHandler = new SongHandler(testFilename);
                List<Song> testSongs = testSongHandler.loadSongs();

                SarangiANN ann = new SarangiANN(trainingSongs,this.labels,DatasetUtil.FeatureType.SARANGI_PITCH);
                
                Result result = ann.test(testSongs);

                result.printData();
        }

}
