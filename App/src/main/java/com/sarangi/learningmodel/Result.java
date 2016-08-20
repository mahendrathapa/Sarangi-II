/**
 * @(#) Result.java 2.0     July 29, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

import com.sarangi.structures.*;
import com.sarangi.audioFeatures.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import smile.validation.FMeasure;
import smile.validation.Precision;
import smile.validation.Recall;
import smile.validation.Sensitivity;

/**
 * Class for holding result of a classification.
 *
 * @author Bijay Gurung
 */

public class Result {

    /* FIELDS **************************************************/

    /**
     * The actual data.
     * A two dimensional array of doubles.
     *
     */
    public double accuracy;

    /**
     * The FMeasure of the result.
     *
     */
    public double fMeasure;

    /**
     * The Precision of the result.
     *
     */
    public double precision;

    /**
     * The Recall of the result.
     *
     */
    public double recall;

    /**
     * The labels for the dataset.
     *
     */
    public String[] labels;

    /**
     * The individual accuracy for each label.
     *
     */
    public double[] labelAccuracy;

    /**
     * The Confusion Matrix
     *
     */
    public int[][] confusionMatrix;

    /**
     * The actual labels of the data.
     *
     */
    public int[] actualLabels;

    /** 
     * The labels predicted by the model.
     *
     */
    public int[] predictedLabels;

    /* CONSTRUCTORS *******************************************/

    /**
     * Empty Constructor.
     *
     */
    public Result() {
    }

    /**
     * Initialization in the constructor.
     *
     * @param actualLabels The actual labels for the data.
     * @param predictedLabels The predicted labels.
     * @param accuracy The overall accuracy.
     * @param labels The strings representing the labels. 
     * @param labelAccuracy The accuracy for each label.
     * @param confusionMatrix The confusion matrix
     *
     */
    public Result(int[] actualLabels, int[] predictedLabels, double accuracy, String[] labels, double[] labelAccuracy, int[][] confusionMatrix) {
        this.actualLabels = actualLabels;
        this.predictedLabels = predictedLabels;
        this.accuracy = accuracy;
        this.labels = labels;
        this.labelAccuracy = labelAccuracy;
        this.confusionMatrix = confusionMatrix;

        this.fMeasure = (new FMeasure()).measure(actualLabels,predictedLabels);
        this.precision = (new Precision()).measure(actualLabels,predictedLabels);
        this.recall = (new Recall()).measure(actualLabels,predictedLabels);
    }

    /**
     * Print all data contained by the object.
     *
     */
    public void printData() {

        System.out.format("\nOverall Accuracy: %.2f%%\n",this.accuracy);
        System.out.format("Precision: %.2f\n",this.precision);
        System.out.format("Recall: %.2f\n",this.recall);
        System.out.format("FMeasure: %.2f\n",this.fMeasure);

        if (labels.length != labelAccuracy.length) {
            System.out.println("Error: Number of labels and accuracy count don't match");
            return;
        }

        for (int i=0; i<labels.length; i++) {
            System.out.format("Label %s: %.2f%%\n",this.labels[i],this.labelAccuracy[i]);
        }

        for (int i=0; i<confusionMatrix.length; i++ ){
            for (int j=0; j<confusionMatrix[i].length; j++) {
                System.out.format("%2d ",confusionMatrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Get Accuracy for the particular label.
     *
     * @param i Index of the Label.
     *
     */
    public double getLabelAccuracy(int i) {

        return this.labelAccuracy[i];

    }

    /**
     * Get the Confusion Matrix
     *
     */
    public int[][] getConfusionMatrix() {

        return this.confusionMatrix;

    }

}
