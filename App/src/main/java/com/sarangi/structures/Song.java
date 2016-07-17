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
         * Intensity features of the given song.
         */
        protected List<Float> intensity = new ArrayList<Float>();

        /**
         * MFCC features of the given song.
         */
        protected List<float[]> mfcc = new ArrayList<float[]>();


        /* CONSTRUCTORS *****************************************/

        /**
         * Store the given audio features into the respected field.
         *
         * @param   songName        A reference to the name of the song.
         *
         * @param   intensity       A reference to the intensity features of the song.
         *
         * @param   mfcc            A reference to the mfcc features of the song.
         */

        public Song(String songName, List<Float> intensity, List<float[]> mfcc){

                this.songName = songName;
                this.intensity.addAll(intensity);
                this.mfcc.addAll(mfcc);
        }

        /**
         * Return the songName of the song.
         *
         * @return      The song name of the song.
         */

        public String getSongName(){
                return songName;
        }

        /**
         * Return the intensity features of the song.
         *
         * @return      Intensity features of the song.
         */
        public List<Float> getIntensity(){
                return intensity;
        }

        /**
         * Return the mfcc features of the song.
         *
         * @return      MFCC features of the song.
         *
         */
        public List<float[]> getMelcoeff(){
                return mfcc;
        }

}
