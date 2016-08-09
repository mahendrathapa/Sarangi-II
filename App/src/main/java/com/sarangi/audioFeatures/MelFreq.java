/*
 * @(#) MFCC.java 2.0      August 8, 2016.
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

public class MelFreq{

        public static double[][] extractFeature(List<double[]> audioFrames,double samplingRate){

                int dimension = 30;
                int length = audioFrames.get(0).length;

                double[][] feature = new double[audioFrames.size()][dimension];

                int count = 0;

                for(double[] frame : audioFrames){

                        float[] floatFrame = Statistics.convertDoubleArrayToFloatArray(frame);


                        try{
                                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(floatFrame,(int)samplingRate,length,0);
                                MFCC mfcc = new MFCC(length,(int)samplingRate);
                                audioDispatcher.addAudioProcessor(mfcc);
                                audioDispatcher.run();

                                feature = Statistics.assign1Dto2DArray(feature,Statistics.convertFloatArrayToDoubleArray(mfcc.getMFCC()),count);
                                ++count;

                        }catch(Exception ex){
                                System.out.println(ex);
                        }
                }

                return feature;
        }
}


