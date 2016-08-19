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

    public static List<double[]> getAudioFrame(double[] audioSamples,int frameSize,int overLapSize,boolean useWindow,boolean usePreEmphasis){

        List<double[]> output = new ArrayList<double[]>();  


        int length = audioSamples.length - frameSize;
        int hopSize = frameSize - overLapSize;

        if(length/hopSize > 9030)
            length = 9030*hopSize - frameSize;


        double[] input = new double[audioSamples.length];

        if(usePreEmphasis)
            input = preEmphasis(audioSamples).clone();
        else 
            input = audioSamples.clone();

        for(int i=0; i<=length; i+= hopSize){

            double[] temp = new double[frameSize];
            System.arraycopy(input,i,temp,0,frameSize);

            if(useWindow)
                output.add(hammingWindow(temp));
            else
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

    public static double[] hanningWindow(double[] data){

        int length = data.length;
        double[] output = new double[length];

        for(int i=0; i<length; ++i)
            output[i] = data[i]*0.5*(1 - Math.cos(2 * Math.PI * i / (length -1)));

        return output;

    }

    public static double[] rectangularWindow(double[] data){
        return data;
    }
    
    public static double[] bartlettWindow(double[] data){

        int length = data.length;
        double[] output = new double[length];

        for(int i=0; i<length; ++i)
            output[i] = data[i] * (1 - 2 * Math.abs(i - (length-1)/2)/(length - 1));

        return output;
    }

    public static double[] blackmannWindow(double[] data){

        int length = data.length;
        double[] output = new double[length];

        for(int i=0; i<length;++i)
            output[i] = data[i] * (0.42 - 0.5 * Math.cos(2 * Math.PI * i / (length - 1)) + 0.08 * Math.cos(4 * Math.PI * i /(length -1)));

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

