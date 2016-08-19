/*
 * @(#) MelFreq.java 2.0      August 8, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.io.*;
import javax.sound.sampled.*;
import java.nio.*;
import java.util.*;
import java.util.logging.*;
import java.lang.*;
import be.tarsos.dsp.mfcc.MFCC;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import java.util.logging.Level;

/**
 * A class that extracts the MFCC features of the song. 
 *
 * This is calcuated by using the tarsos dsp library.
 *
 * @author Mahendra Thapa
 */

public class MelFreq{

    /* PUBLIC METHODS *****************************************************/

    /**
     * Extracts this feature from the given audio frames at the given sampling
     * rate.
     
     * @param audioFrames       The audio frame to extract the feature from.
     *
     * @param samplingRate      The sampling rate that the samples are encoded with.
     *
     * @return                  The extracted feature values.
     *
     */

    public static double[][] extractFeature(List<double[]> audioFrames,double samplingRate){

        LoggerHandler loggerHandler = LoggerHandler.getInstance();

        int dimension = 30;
        int length = audioFrames.get(0).length;

        double[][] feature = new double[audioFrames.size()][dimension];

        int count = 0;

        try{
            for(double[] frame : audioFrames){

                float[] floatFrame = Statistics.convertDoubleArrayToFloatArray(frame);


                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(floatFrame,(int)samplingRate,length,0);
                MFCC mfcc = new MFCC(length,(int)samplingRate);
                audioDispatcher.addAudioProcessor(mfcc);
                audioDispatcher.run();

                feature = Statistics.assign1Dto2DArray(feature,Statistics.convertFloatArrayToDoubleArray(mfcc.getMFCC()),count);
                ++count;

            }

            return feature;

        }catch(Exception ex){

            loggerHandler.loggingSystem(LoggerHandler.LogType.FEATURE_EXTRACTION,
                    Level.SEVERE,
                    ExceptionPrint.getExceptionPrint(ex));

            return new double[][]{};

        }
    }

}


