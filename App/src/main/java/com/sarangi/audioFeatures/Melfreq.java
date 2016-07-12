/*
 * @(#) Melfreq.java  2.0   July 11,2016.
 *
 * Ayush Shakya
 *
 * Institue of Engineering
 *
 */

package com.sarangi.audioFeatures;

import java.io.*;
import javax.sound.sampled.*;
import java.nio.*;
import java.util.*;
import java.util.logging.*;
import java.lang.*;
import be.tarsos.dsp.mfcc.MFCC;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

/**
 * A class for calculating the mfcc of the given audio samples.
 *
 * <p> Include constructor in which the logger is defined and Mel-Frequency Cepstral coefficients is calcuated.
 *
 * <p> Include method for accesing the mfcc features of the given audio sample.
 *
 *
 * @author Ayush Shakya
 *
 */

public class Melfreq{

        /*FIELDS ***************************************************/

        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("Melfreq");

        /**
         * A List for storing the mel frequency coefficients which is extracted from the given song.
         *
         */
        private List<float[]> mfccFeatures = new ArrayList<float[]>();

        /*CONSTRUCTORS **********************************************************/

        /**
         * Extract the MFCC features from the given audio Samples.
         * Also set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         *
         * @param   audioSamples        Storing the audio sample of the song.
         *
         * @param   audioFormat         The audio format which is associated with given audio samples.
         *
         * @throws  Exception           Throw an exception if any occur.
         *
         */

        public Melfreq(List<float[]> audioFrame,AudioFormat audioFormat)
        {

                logger.setLevel(Level.SEVERE);

                int sampleFrequency = (int)audioFormat.getSampleRate();

                for(float[] frame : audioFrame){

                        try{
                                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(frame,sampleFrequency,frame.length,0);
                                MFCC mfcc = new MFCC(frame.length, sampleFrequency);
                                audioDispatcher.addAudioProcessor(mfcc);
                                audioDispatcher.run();

                                float[] result = mfcc.getMFCC();

                                mfccFeatures.add(result);

                        }
                        catch(Exception ex)
                        {
                                logger.log(Level.SEVERE,ex.toString(),ex);
                        }

                }
        }

        /**
         * Returns the MFCC features of the song.
         *
         * @return      Mfcc features of the song.
         *
         */

        public List<float[]> getMfccFeatures()
        {
                return mfccFeatures;
        }
}
