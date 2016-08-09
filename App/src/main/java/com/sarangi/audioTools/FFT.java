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
 * A class for calculating the fast fourier transform of the audio single.
 *
 * <p> Includes constructor for calculating the fast fourier transform of the input audio samples.
 *
 * <p> Includes method to get the real output obtained by the FFT of the audio samples.
 *
 * <p> Includes method to get the imaginary output obtained by the FFT of the audio samples.
 *
 * @author Mahendra Thapa
 */

public class FFT{

        /* FIELDS **********************************/

        /**
         * DoubleFFT_1D is used to compute 1D Discrete Fourier Transform of complex and real, double precision data.
         */
        protected DoubleFFT_1D fft;

        /**
         * It is used to store the real output of the input audio signal. The real output is the results of fourier transform
         * of the audio signal.
         */
        protected double[] realOutput;

        /**
         * It is used to store the imaginary output of the input audio signal. The imaginary output is the results of the fourier transform
         * of the audio signal.
         */
        protected double[] imaginaryOutput;
        protected double[] outputMagnitude;
        protected double[] outputPower;

        /*CONSTRUCTORS *******************************************/

        /**
         * Calculating the fast fourier transform of the input audio samples and stores the result in the 
         * realOutput and imaginaryOutput.
         *
         * @param   samples         Stores the input audio signal sample values.
         *
         */
        public FFT(double[] samples){

                fft = new DoubleFFT_1D(samples.length);
                realOutput = new double[samples.length];
                imaginaryOutput = new double[samples.length];

                double[] fftData = new double[samples.length * 2];

                for(int i=0; i<samples.length; ++i){

                        fftData[2 * i] = samples[i];
                        fftData[2 * i + 1] = 0;
                }

                fft.complexForward(fftData);

                for(int i = 0; i<samples.length; ++i){

                        realOutput[i] = fftData[2 * i];
                        imaginaryOutput[i] = fftData[2 * i + 1];
                }

        }

        /**
         * Returns the real output obtained by the FFT of the audio samples.
         *
         * @return  Real output obtained by the FFT of the audio samples.
         *
         */

        public double[] getRealOutput(){
                return realOutput;
        }

        /**
         * Returns the imaginary output obtained by the FFT of the audio samples.
         *
         * @return  imaginary output obtained by the FFT of the audio samples.
         *
         */

        public double[] getImaginaryOutput(){
                return imaginaryOutput;
        }

        public double[] getMagnitudeSpectrum(){

                int length = realOutput.length;
                outputMagnitude = new double[length];

                for(int i=0; i<length; ++i)
                        outputMagnitude[i] = (Math.sqrt(realOutput[i] * realOutput[i] + imaginaryOutput[i] * imaginaryOutput[i] )) / length;

                return outputMagnitude;
        }

        public double[] getPowerSpectrum(){

                int length = realOutput.length;
                outputPower = new double[length];

                for(int i=0; i<length; ++i)
                        outputPower[i] = (realOutput[i] * realOutput[i] + imaginaryOutput[i] * imaginaryOutput[i])/length;

                return outputPower;

        }

}

