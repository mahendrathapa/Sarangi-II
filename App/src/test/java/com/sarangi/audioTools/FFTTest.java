package com.sarangi.audioTools;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;

import java.io.*;
import javax.sound.sampled.*;
import java.util.Arrays;

/**
 * Unit test for FFT.
 */

public class FFTTest extends TestCase{


        public void testGetRealOutput() throws UnsupportedAudioFileException,IOException,IllegalArgumentException{

                //AudioSample audioSample = new AudioSample(new File("src/resources/song/extra/abc.wav"));

              //  double[] samples = audioSample.getAudioSamples();
              //

            try{
/*
                double[] samples = new double[]{2.0,0.3, 0.6, 1.22,4.0, 3.03, 0.45};

                FFT fft = new FFT(samples,null,false,false);

                double[] realOutput = fft.getRealValues();
                double[] imagOutput = fft.getImaginaryValues();

                FFT ifft = new FFT(realOutput,imagOutput,true,false);
                double[] output = ifft.getRealValues();
                double[] ioutput = ifft.getImaginaryValues();

                System.out.println(Arrays.toString(samples));

                System.out.println(Arrays.toString(realOutput));
                System.out.println(Arrays.toString(imagOutput));
                
                System.out.println(Arrays.toString(output));
                System.out.println(Arrays.toString(ioutput));
*/
            } catch (Exception ex){
                    System.out.println(ex);
            }

        }

}


