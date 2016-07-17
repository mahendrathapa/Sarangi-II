/*
 * @(#) AudioPreprocessing.java 2.0     July 12, 2016.
 *
 * Mahendra Thapa
 *
 * Institute of Engineering
 *
 */

package com.sarangi.audioTools;

import java.lang.System.*;
import java.util.*;
import java.util.logging.*;

/**
 * A class for preprocessing of the audio samples. The preprocessing involves the Framing of the audio signal.
 *
 * <p>Includes constructors for setting the level of logger.
 *
 * <p>Includes methods for converting the audio samples into the audio frame of size 1024.
 *
 * @author Mahendra Thapa
 *
 */

public class AudioPreprocessing{

        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("AudioPreprocessing");


        /* CONSTRUCTORS *******************************************/

        /**
         * Set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         */

        public AudioPreprocessing(){

                logger.setLevel(Level.SEVERE);

        }

        /**
         * A methods to convert the audio sample into the audio frame of given size.
         *
         * @param   audioSample     It represents the audio sample of the given audio signal.
         *
         * @param   frameSize       It represents the size of the frame.
         *
         * @return                  Returns the arrayList of array represent the frame of the given audio sample.
         *
         */

        public List<float[]> getAudioFrame(float[] audioSample,int frameSize){

                List<float[]> frame = new ArrayList<float[]>();

                int length = audioSample.length - frameSize;

                for(int i=0; i<=length; i+=frameSize){

                        float[] temp = new float[frameSize];

                        System.arraycopy(audioSample,i,temp,0,frameSize);

                        frame.add(temp);
                }

                return frame;
        }

}

