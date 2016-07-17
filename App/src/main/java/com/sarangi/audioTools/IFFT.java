/*
 * @(#) AudioSamples.java 2.0   June 5, 2016.
 *
 * Mahendra Thapa
 *
 * Institute of Engineering
 *
 */ 

package com.sarangi.audioTools;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * A class for calculating the inverse fast fourier transform of the audio single.
 *
 * <p> Includes constructor for calculating the inverse fast fourier transform of the input audio frequency domain samples.
 *
 * <p> Includes method to get the real output obtained by the IFFT of the audio samples.
 *
 * <p> Includes method to get the imaginary output obtained by the IFFT of the audio samples.
 *
 * @author  Mahendra Thapa
 *
 */

public class IFFT{

        /*FIELDS *********************************************/

        /**
         * DoubleFFT_1D is used to compute 1D inverset Discrete Fourier Transform of complex and real, double precision data.
         *
         */
        protected DoubleFFT_1D ifft;

        /**
         * It is used to stored the real output of the input sample. The real output is the result of inverse fourier
         * transform of the audio signal.
         */
         protected double[] realOutput;

         /**
          * It is used to stored the imaginary output of the input sample. The imaginary output is the result of inverse fourier
          * transform of the audio signal.
          */
         protected double[] imagOutput;

         /*CONSTRUCTORS ****************************************************/

         /**
          * Calculating the inverse fast fourier transform of the input audio frequecy domain samples and stores the 
          * result in realOutput and imagOutput.
          *
          * @param  realSample      Stores the real input audio signal sample values.
          *
          * @param  imagSample      Stores the imag input audio signal sample values.
          *
          */

         public IFFT(double[] realSample, double[] imagSample){

                 ifft = new DoubleFFT_1D(realSample.length);

                 double[] ifftData = new double[realSample.length*2];

                 realOutput = new double[realSample.length];
                 imagOutput = new double[imagSample.length];

                 for(int i=0; i<realSample.length; ++i){

                         ifftData[2 * i] = realSample[i];
                         ifftData[2 * i + 1] = imagSample[i];
                 }

                 ifft.complexInverse(ifftData,true);

                 for(int i=0; i<realSample.length; ++i){

                         realOutput[i] = ifftData[2 * i];
                         imagOutput[i] = ifftData[2 * i + 1];
                 }
         }

         /**
          * Returns the real output obtained by the IFFT of the audio samples.
          *
          * @return Real output obtained by the IFFT of the audio samples.
          *
          */

         public double[] getRealOutput(){

                 return realOutput;
         }
}
