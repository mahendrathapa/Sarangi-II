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

    public static List<double[]> getAudioFrame(double[] audioSamples,int frameSize,int overLapSize){

        List<double[]> output = new ArrayList<double[]>();  


        int length = audioSamples.length - frameSize;
        int hopSize = frameSize - overLapSize;

        for(int i=0; i<=length; i+= hopSize){

            double[] temp = new double[frameSize];
            System.arraycopy(audioSamples,i,temp,0,frameSize);
            output.add(temp);
        }

        return output;
    }

    public static double[] hammingWindow(double[] data){

        int length = data.length;
        double[] output = new double[length];

        for(int i=0; i<length; ++i)
            output[i] = data[i]*(0.54 - 0.46 * Math.cos(2*Math.PI * i /(length -1)));

        return output;

    }


    public static double[] preEmphasis(double[] data){

        double alpha = 0.95;
        int length = data.length;
        double[] output = new double[length];

        output[0] = 0.0;

        for(int i=1; i<length; ++i)
            output[i] = data[i] - 0.95*data[i-1];

        return output;
            
    }
}

