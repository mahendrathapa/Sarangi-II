/* @(#) Song.java   2.0     June 9, 2016.
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
    double[] pitch;
    double[] rhythm;
    double[] rms;
    double[] spectralCentroid;
    double[] spectralFlux;
    double[] spectralRolloffPoint;
    double[] spectralVariability;
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

    public Song(String songName,double[] compactness,double[] melFreq,double[] pitch,double[] rhythm,double[] rms,double[] spectralCentroid,double[] spectralFlux,double[] spectralRolloffPoint,double[] spectralVariability,double[] zeroCrossing){

        this.songName = songName;
        this.compactness = compactness.clone();
        this.melFreq = melFreq.clone();
        this.pitch = pitch.clone();
        this.rhythm = rhythm.clone();
        this.rms = rms.clone();
        this.spectralCentroid = spectralCentroid.clone();
        this.spectralFlux = spectralFlux.clone();
        this.spectralRolloffPoint = spectralRolloffPoint.clone();
        this.spectralVariability = spectralVariability.clone();
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

    public double[] getPitch(){
        return pitch;
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

    public double[] getSpectralVariability(){
        return spectralVariability;
    }  

    public double[] getZeroCrossing(){
        return zeroCrossing;
    }       

    public double[] getFeature(FeatureType featureType) {
        if (featureType == FeatureType.SARANGI_MELFREQ) {

            int melFreqLength = featureType.getLength();
            double[] melFreq =new double[melFreqLength];

            System.arraycopy(getMelFreq(),0,melFreq,0,melFreqLength/2);
            System.arraycopy(getMelFreq(),30,melFreq,melFreqLength/2,melFreqLength/2);

            return melFreq;

        } else if (featureType == FeatureType.SARANGI_PITCH) {

            return getPitch();

        } else if (featureType == FeatureType.SARANGI_COMPACTNESS) {

            return getCompactness();

        } else if (featureType == FeatureType.SARANGI_RHYTHM) {

            return getRhythm();

        } else if (featureType == FeatureType.SARANGI_RMS) {

            return getRMS();

        } else if (featureType == FeatureType.SARANGI_SPECTRALCENTROID) {

            return getSpectralCentroid();

        } else if (featureType == FeatureType.SARANGI_SPECTRALFLUX) {

            return getSpectralFlux();

        } else if (featureType == FeatureType.SARANGI_SPECTRALROLLOFFPOINT) {

            return getSpectralRolloffPoint();

        } else if (featureType == FeatureType.SARANGI_SPECTRALVARIABILITY) {

            return getSpectralVariability();

        } else if (featureType == FeatureType.SARANGI_ZEROCROSSING) {

            return getZeroCrossing();

        }

        return null;
    }
}
