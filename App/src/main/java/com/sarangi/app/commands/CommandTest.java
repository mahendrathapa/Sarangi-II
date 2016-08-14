/**
 * @(#) CommandTest.java 2.0     August 11, 2016
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
 * Command to test the classifier.
 *
 *
 * @author Bijay Gurung
 */


@Parameters(separators = "=", commandDescription = "Test the classifier")
public class CommandTest {

    @Parameter(names = {"-k","--kfold"},description = "Run K-fold validation on given file")
        public String kfoldFile;

    @Parameter(names = {"-C","--classifier-type"},description = "Type of Classifier to use.")
        public String classifierType = "SVM";

    @Parameter(names = {"-l","--label"},description = "The label to be used for training")
        public int labelIndex = 0;

    @Parameter(names = "--help", help = true)
        public boolean help;
}

