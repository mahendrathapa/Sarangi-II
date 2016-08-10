
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
 * A class for calculating the pitch of the given audio samples.
 *
 * <p> Include constructor in which the logger is defined and pitch is calculated.
 *
 * <p> Include method for accessing the pitch features of the given audio sample.
 *
 * @author Ayush Shakya
 *
 */

public class Pitch{


        public static double[][] extractFeature(List<double[]> Frames,double samplingRate){

                int dimension = 1;
                int length = Frames.get(0).length;
               
                double[][] feature = new double[Frames.size()][dimension];

                final List<Double> pitchList = new ArrayList<Double>();

                for(double[] frame : Frames){

                        float[] floatFrame = Statistics.convertDoubleArrayToFloatArray(frame);

                        try{
                                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(floatFrame,(int)samplingRate,length,0);
                                PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                                        @Override
                                        public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                                                pitchList.add((double)pitchDetectionResult.getPitch());
                                                }

                                };
                                audioDispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN,(int)samplingRate,length,pitchDetectionHandler));
                                audioDispatcher.run();

                        } catch(UnsupportedAudioFileException ex){
                        
                        }        
                }

                int count = 0;
                for(double pitch : pitchList){

                        feature = Statistics.assign1Dto2DArray(feature,new double[]{pitch},count);
                        ++count;
                }

                return feature;
        }
}


