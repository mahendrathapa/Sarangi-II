/*
 * @(#) Song.java   2.0     June 9, 2016.
 *
 * Mahendra Thapa
 *
 * Institute of Engineering
 */

package com.sarangi.structures;
import java.util.*;

/**
 * A class for simple data structure to hold information related to a song.
 *
 * <p>Includes constructures for storing the song name and its audio features in corresponding fields.
 *
 * <p>Includes methods for accessing the song name.
 *
 * <p>Includes methods for accessing the intensity of the song.
 *
 * <p>Includes methods for accessing the mfcc of the song.
 *
 * @author  Mahendra Thapa
 */

public class Song{

        /* FIELDS **********************************************/

        /**
         * Name of the song from which features is extracted.
         */
        protected String songName;

        /**
         *  features of the given song.
         */
        double[] compactness;
        double[] melFreq;
        double[] rhythm;
        double[] rms;
        double[] spectralCentroid;
        double[] spectralFlux;
        double[] spectralRolloffPoint;
        double[] spectralVariablility;
        double[] zeroCrossing;

        /* CONSTRUCTORS *****************************************/

        /**
         * Store the given audio features into the respected field.
         *
         * @param   songName        A reference to the name of the song.
         *
         * @param   intensity       A reference to the intensity features of the song.
         *
         * @param   mfcc            A reference to the mfcc features of the song.
         *
         * @param   pitch           A reference to the pitch features of the song.
         */

          public Song(String songName,double[] compactness,double[] melFreq,double[] rhythm,double[] rms,double[] spectralCentroid,double[] spectralFlux,double[] spectralRolloffPoint,double[] spectralVariablility,double[] zeroCrossing){

                  this.songName = songName;
                  this.compactness = compactness.clone();
                  this.melFreq = melFreq.clone();
                  this.rhythm = rhythm.clone();
                  this.rms = rms.clone();
                  this.spectralCentroid = spectralCentroid.clone();
                  this.spectralFlux = spectralFlux.clone();
                  this.spectralRolloffPoint = spectralRolloffPoint.clone();
                  this.spectralVariablility = spectralVariablility.clone();
                  this.zeroCrossing = zeroCrossing.clone();

          }
                

        /**
         * Return the songName of the song.
         *
         * @return      The song name of the song.
         */

        public String getSongName(){
                return songName;
        }

        public double[] getCompactness(){
                return compactness;
        }
 
        public double[] getMelFreq(){
                return melFreq;
        }

        public double[] getRhythm(){
                return rhythm;
        }

       public double[] getRMS(){
                return rms;
        }
        public double[] getSpectralCentroid(){
                return spectralCentroid;
        }
        public double[] getSpectralFlux(){
                return spectralFlux;
        }

        public double[] getSpectralRolloffPoint(){
                return spectralRolloffPoint;
        }  
        public double[] getSpectralVariablility(){
                return spectralVariablility;
        }  
        public double[] getZeroCrossing(){
                return zeroCrossing;
        }       
}
