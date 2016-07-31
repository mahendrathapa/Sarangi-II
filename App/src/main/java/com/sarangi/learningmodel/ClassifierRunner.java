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

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

/**
 * Abstract Class representing the runners for different classifiers.
 *
 * @author Bijay Gurung
 */


public abstract class ClassifierRunner {

        protected String[] labels;

        public ClassifierRunner(String[] labels) {
        
                this.labels = labels;
        }

        public abstract void run(String trainingFilename, String testFilename, DatasetUtil.FeatureType featureType) throws FileNotFoundException, IOException;

}
