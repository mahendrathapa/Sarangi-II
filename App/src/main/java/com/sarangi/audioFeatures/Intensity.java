/*
 * @(#) Intensity.java 2.0      June 5, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import java.io.*;
import javax.sound.sampled.*;
import java.nio.*;
import java.util.*;
import java.lang.*;

/**
 * A class for calculating the intensity features of the given audio samples.
 *
 * <p>Includes constructors for calulating the intensity features of the audio samples.
 *    and store the result.
 *
 * <p>Include methods for accessing intensity features of the given audio samples.
 *
 * @author  Mahendra Thapa
 */

public class Intensity{

        /* FIELDS **********************************************/

        /**
         * Rms values of the audio samples. The rms value used to give the 
         * intensity features of the audio samples.
         */
        protected List<Float> rms = new ArrayList<Float>();

        /*CONSTRUCTORS ****************************************/

        /**
         * Store the given audio sample as intensity features of the corresponding audio sample.
         *
         * @param   audioFrame    The audio samples from which the intensity features is extracted.
         *
         * @param   audioFormat     The audio format which is associated with given audio samples.
         *
         */

        public Intensity(List<float[]> audioFrame, AudioFormat audioFormat){

                int frameLength = audioFrame.get(0).length;

                for(float[] singleFrame : audioFrame){

                        float tempRms = (float)0.0;

                        for(float temp : singleFrame){
                                tempRms += temp*temp;
                        }

                        tempRms /=frameLength;
                        tempRms = (float)Math.sqrt(tempRms);

                        rms.add(tempRms);

                }
        }

        /**
         * Returns the intensity features of the audio sample.
         *
         * @return          The intensity features of the audio sample.
         *
         */

        public List<Float> getIntensityFeatures(){
                return rms;

        }
}

