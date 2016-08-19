
/*
 * @(#) Pitch.java  2.0   July 17,2016.
 *
 * Ayush Shakya
 *
 * Institue of Engineering
 *
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioTools.*;
import java.util.*;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import javax.sound.sampled.*;
import java.util.logging.*;

/**
 * A class that extracts the Pitch features of the song.
 *
 * This is calculated by using the tarsos dsp library.
 *
 * @author Ayush Shakya
 *
 */

public class Pitch{

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


    public static double[][] extractFeature(List<double[]> Frames,double samplingRate){

        LoggerHandler loggerHandler = LoggerHandler.getInstance();

        int dimension = 1;
        int length = Frames.get(0).length;

        double[][] feature = new double[Frames.size()][dimension];

        final List<Double> pitchList = new ArrayList<Double>();

        try{
            for(double[] frame : Frames){

                float[] floatFrame = Statistics.convertDoubleArrayToFloatArray(frame);

                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(floatFrame,(int)samplingRate,length,0);
                PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                        pitchList.add((double)pitchDetectionResult.getPitch());
                    }

                };
                audioDispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN,(int)samplingRate,length,pitchDetectionHandler));
                audioDispatcher.run();

            }

            int count = 0;
            for(double pitch : pitchList){

                feature = Statistics.assign1Dto2DArray(feature,new double[]{pitch},count);
                ++count;
            }

            return feature;

        } catch(UnsupportedAudioFileException ex){

            loggerHandler.loggingSystem(LoggerHandler.LogType.FEATURE_EXTRACTION,
                    Level.SEVERE,
                    ExceptionPrint.getExceptionPrint(ex));

            return new double[][]{};
        }
    }
}


