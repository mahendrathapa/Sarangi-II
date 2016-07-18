/*
 * (@)# AudioPreProcessor.java  2.0     July 16,2016.
 *
 * Mahendra Thapa
 *
 * Institute of Engineering
 *
 */

package com.sarangi.audioTools;
import java.util.logging.*;
import java.util.*;
import java.lang.System.*;

/**
 * A class for audio pre-processing of the audio signal. 
 *
 * <p> Include constructor in which the logger is defined.
 *
 * <p> Include a method which break the whole audio signal into different frame 
 * according to the given parameter.
 *
 * @author Mahendra Thapa
 */

public class AudioPreProcessor{

        /*FIELDS**********************************************************/

        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("AudioPreProcessor");


        /*CONSTRUCTORS****************************************************/

        /**
         *Set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         */

        public AudioPreProcessor(){

                logger.setLevel(Level.SEVERE);
        }

        /**
         * A method to break down the audio sample into the frame of given size.
         *
         * @param   audioSamples    A reference to the audio sample of the given song.
         *
         * @param   frameSize       A reference to the size of the frame.
         *
         * @param   overLapSize     A reference to the overlapping of the frame.
         *
         * @return                   The list of the frame.
         *
         */

        public List<float[]> getAudioFrame(float[] audioSamples,int frameSize,int overLapSize){

              List<float[]> output = new ArrayList<float[]>();  


              int length = audioSamples.length - frameSize;
              int hopSize = frameSize - overLapSize;

              for(int i=0; i<=length; i+= hopSize){
                      
                      float[] temp = new float[frameSize];
                      System.arraycopy(audioSamples,i,temp,0,frameSize);
                      output.add(temp);

              }

              return output;
        }
}

