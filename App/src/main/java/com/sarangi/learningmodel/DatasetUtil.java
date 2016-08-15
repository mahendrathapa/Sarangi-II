/**
 * @(#) DatasetUtil.java 2.0     July 29, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

import com.sarangi.structures.*;
import com.sarangi.audioFeatures.*;
import com.sarangi.audioTools.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Class for holding the dataset for training and testing
 *
 *
 * @author Bijay Gurung
 */

public class DatasetUtil {

    /**
     * The size of each dataset.
     * It is the sum of length of each feature used.
     * 
     */
    public static int DATASET_SIZE = 20;


    /**
     * Get the LearningDataset object on a song by song basis.
     *
     * @param dataSongs The songs to use.
     * @param labelArray The array of label strings.
     * @param featureType The type of feature to extract into the dataset.
     *
     * @return The LearningDataset object corresponding to the given input.
     *
     */

    public static LearningDataset getSongwiseDataset(List<Song> dataSongs, String[] labelArray, FeatureType featureType) {

        LearningDataset learningDataset = new LearningDataset();

        learningDataset.labelIndices = new int[dataSongs.size()];

        if(featureType == FeatureType.SARANGI_ALL){

            learningDataset.dataset = new double[dataSongs.size()][20];

            int i = 0;

            for(Song song : dataSongs){

                double[] compactness = song.getCompactness();

                double[] melFreq =new double[20];
                System.arraycopy(song.getMelFreq(),0,melFreq,0,10);
                System.arraycopy(song.getMelFreq(),30,melFreq,10,10);

                double[] pitch = song.getPitch();
                double[] rhythm = song.getRhythm();
                double[] rms = song.getRMS();
                double[] spectralCentroid = song.getSpectralCentroid();
                double[] spectralFlux = song.getSpectralFlux();
                double[] spectralRolloffPoint = song.getSpectralRolloffPoint();
                double[] spectralVariablility = song.getSpectralVariablility();
                double[] zeroCrossing = song.getZeroCrossing();

                learningDataset.dataset[i] = Statistics.mergeArrays(melFreq);
                /*
                   learningDataset.dataset[i] = Statistics.mergeArrays(compactness,
                   melFreq,
                   rms,
                   spectralCentroid,
                   spectralFlux,
                   spectralRolloffPoint,
                   spectralVariablility,
                   zeroCrossing);
                   */
                learningDataset.labelIndices[i] = getIndexOfLabel(song.getSongName(),labelArray);

                ++i;

            }


        }

        /*
           if (featureType == FeatureType.SARANGI_PITCH) {
           learningDataset.dataset = new double[dataSongs.size()][dataSongs.get(0).getPitch().length];
           int i = 0;
           for (Song song: dataSongs) {

           int[] pitch = song.getPitch();

           learningDataset.dataset[i] = convertIntArrayToDoubleArray(pitch);
           learningDataset.labelIndices[i] = getIndexOfLabel(song.getSongName(), labelArray);

           i++;

           }

           }else if(featureType == FeatureType.SARANGI_RHYTHM){

           learningDataset.dataset = new double[dataSongs.size()][dataSongs.get(0).getRhythm().length];
           int i = 0;
           for (Song song: dataSongs) {

           float[] rhythm = song.getRhythm();

           learningDataset.dataset[i] = convertFloatsToDoubles(rhythm);
           learningDataset.labelIndices[i] = getIndexOfLabel(song.getSongName(), labelArray);

           i++;

           }

           }else if (featureType == FeatureType.SARANGI_MFCC) {

// We average the value of MFCC over all frames to get one set of MFCC for the whole song.

learningDataset.dataset = new double[dataSongs.size()][Melfreq.NUM_OF_COEFFICIENTS];

int i = 0;
for (Song song: dataSongs) {

List<float[]> mfcc = song.getMelcoeff();

double[] avgMFCC = new double[Melfreq.NUM_OF_COEFFICIENTS];

for (float[] singleFrameMFCC: mfcc) {
        // Sum the MFCC coefficients 
        for (int j=0; j<Melfreq.NUM_OF_COEFFICIENTS; j++) {
        avgMFCC[j] += singleFrameMFCC[j];
        }
}

int numberOfFrames = mfcc.size();
for (int j=0; j<avgMFCC.length; j++) {
avgMFCC[j] /= numberOfFrames;
}

learningDataset.dataset[i] = avgMFCC;

learningDataset.labelIndices[i] = getIndexOfLabel(song.getSongName(), labelArray);

i++;

}
           }
           */

return learningDataset;

    }

/**
 * Get the LearningDataset object on a frame by frame basis.
 *
 * @param dataSongs The songs to use.
 * @param labelArray The array of label strings.
 * @param featureType The type of feature to extract into the dataset.
 *
 * @return The LearningDataset object corresponding to the given input.
 *
 */
public static LearningDataset getFramewiseDataset(List<Song> dataSongs, String[] labelArray, FeatureType featureType) {

    LearningDataset learningDataset = new LearningDataset();
    /*
       if (featureType == FeatureType.SARANGI_MFCC) {

    // Store the songs.
    List<double[]> dataset = new ArrayList<double[]>();

    List<Integer> labelIndices = new ArrayList<Integer>();

    for (Song song: dataSongs) {

    // Convert float to doubles
    List<float[]> mfccFloats = song.getMelcoeff();
    List<double[]> mfcc = new ArrayList<double[]>();
    int indexOfLabel = getIndexOfLabel(song.getSongName(), labelArray);
    for (float[] mfccFloat: mfccFloats) {
    mfcc.add(convertFloatsToDoubles(mfccFloat));
    labelIndices.add(indexOfLabel);
    }

    dataset.addAll(mfcc);

    }

    learningDataset.dataset = new double[dataset.size()][];
    learningDataset.labelIndices = new int[dataset.size()];

    for (int i = 0; i < dataset.size(); i++) {

    learningDataset.dataset[i] = dataset.get(i);
    learningDataset.labelIndices[i] = labelIndices.get(i);

    }
       }
       */

    return learningDataset;

}

/**
 * Get the index of the labelArray member which contains the given string.
 *
 * @param str The string which is supposed to contain a label string.
 * @param labelArray The array of labels.
 *
 * @return The index
 *
 */

public static int getIndexOfLabel(String str, String[] labelArray) {

    for (int i=0; i<labelArray.length; i++) {
        if (str.contains(labelArray[i])) {
            return (i+1);
        }
    }

    return -1;

}

/**
 * Convert the given int array to an array of doubles.
 *
 * @param arr The int array to be converted.
 *
 * @return The double array.
 *
 */

public static double[] convertIntArrayToDoubleArray(int[] arr) {

    double[] retArray = new double[arr.length];
    for (int i=0; i<arr.length; i++) {
        retArray[i] = (double) arr[i];
    }

    return retArray;
}

/**
 * Return array of doubles using an array of floats
 * 
 * @param input Array of floats
 *
 * @return Array of doubles
 *
 */
public static double[] convertFloatsToDoubles(float[] input) {
    if (input == null) {
        return null; // Or throw an exception - your choice
    }

    double[] output = new double[input.length];

    for (int i = 0; i < input.length; i++) {
        output[i] = input[i];
    }
    return output;
}


}
