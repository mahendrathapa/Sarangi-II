/**
 * @(#) CommandTrain.java 2.0     August 11, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.app.commands;

import com.beust.jcommander.Parameter; 
import com.beust.jcommander.Parameters; 

import com.sarangi.learningmodel.ClassifierType;

/**
 * Command to train a classifier.
 *
 *
 * @author Bijay Gurung
 */


@Parameters(separators = "=", commandDescription = "Train the classifier using the given features file.")
public class CommandTrain {

    @Parameter(names = {"-f","--file"},description = "The file where the features are stored")
        public String file;

    @Parameter(names = {"-c","--classifier"},description = "The file to store the classifier")
        public String classifierFile;

    @Parameter(names = {"-C","--classifier-type"},description = "Type of Classifier to use.")
        public String classifierType = "SVM";

    @Parameter(names = {"-l","--label-type"},description = "The label to be used for training")
        public String label = "genre";

    @Parameter(names = "--help", help = true)
        public boolean help;
}

