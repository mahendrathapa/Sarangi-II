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

public class MelFreq extends SarangiFeature{


        private  int frameLevelDimension = 5;

        public MelFreq(){

                this(10);
        }

        public MelFreq(int dimension){

                super("MFCC","MFCC calculations based on the TarsosDSP library",dimension);
                frameLevelDimension = dimension / 2;
        }


        public double[] extractFeature(List<double[]> audioFrames,double samplingRate){

                int length = audioFrames.get(0).length;

                double[][] frameMFCC = new double[audioFrames.size()][frameLevelDimension];

                int count = 0;

                for(double[] frame : audioFrames){

                        float[] floatFrame = Statistics.convertDoubleArrayToFloatArray(frame);


                        try{
                                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(floatFrame,(int)samplingRate,length,0);
                                MFCC mfcc = new MFCC(length,(int)samplingRate);
                                audioDispatcher.addAudioProcessor(mfcc);
                                audioDispatcher.run();

                                float[] output = mfcc.getMFCC(); 

                                frameMFCC = Statistics.assign1Dto2DArray(frameMFCC,Statistics.convertFloatArrayToDoubleArray(output),count);
                                ++count;

                        }catch(Exception ex){
                                System.out.println(ex);
                        }
                }

                feature = Statistics.getAverageAndStandardDeviation(frameMFCC,dimension);
        
                return feature;
        }
}


