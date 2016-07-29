
/*
 * @(#) Pitch.java  2.0   July 17,2016.
 *
 * Ayush Shakya
 *
 * Institue of Engineering
 *
 */

package com.sarangi.audioFeatures;

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

        /*FIELDS ***************************************************/

        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("Pitch");

        /**
         * A List for storing the  pitch features as time series which is extracted for the given song.
         *
         */
        private List<Float> pitchFrameWise = new ArrayList<Float>();

        /**
         * An array for the storing the pitch features as a graph between the strength vs pitch for the given song.
         */

        private int[] pitchGraph;

        /**
         * Range of the pitch
         */
        private final int range = 30;


        /**
         * The gap of the pitch
         */
        private final int gap = 50;

        /**
         * Sampling frequency of the audio sample
         */
        private  int samplingFrequency;



        /*CONSTRUCTORS **********************************************************/

        /**
         * Extract the pitch features from the given audio Samples.
         * Also set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         *
         * @param   audioFrame          A reference to the audio frame of the song.
         *
         * @param   audioFormat         A reference to the audio format which is associated with given audio samples.
         * 
         * @throws  Exception           Throw an exception if any occur.
         *
         */

        public Pitch(List<float[]> audioFrame, AudioFormat audioFormat)

        {
                pitchGraph = new int[range];
                
                samplingFrequency = (int)audioFormat.getSampleRate();
                
                int length = audioFrame.get(0).length;

                logger.setLevel(Level.SEVERE);

                try{

                        for(float[] singleFrame:audioFrame){

                                AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(singleFrame,samplingFrequency,length,0);

                                PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                                        @Override
                                        public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {

                                                float pitch = pitchDetectionResult.getPitch();
                                        
                                                if(Math.abs(pitch + 1) > 0.1)
                                                        pitchFrameWise.add(pitch);

                                        }
                                };

                                audioDispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN,samplingFrequency,length,pitchDetectionHandler));
                                audioDispatcher.run();
                        }

                        for(float singlePitch : pitchFrameWise){
                                int index = (int)singlePitch/gap;

                                if(index<range)
                                        ++pitchGraph[(int)singlePitch/gap];
                        }

                } catch(UnsupportedAudioFileException ex){
                        logger.log(Level.SEVERE,ex.toString(),ex);
                }
        }

        /**
         * A method for getting the pitch features of the audio sample.
         *
         * @return      Pitch features of the audio sample.
         */

        public List<Float> getPitchFrameWise(){
                return pitchFrameWise;
        }

        /**
         * A method for getting the pitch graph of the audio sample.
         *
         * @return      Pitch graph of the audio sample.
         */

        public int[] getPitchGraph(){
                return pitchGraph;
        }
}

