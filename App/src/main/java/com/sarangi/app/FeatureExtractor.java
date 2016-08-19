/*
 * @(#) FeatureExtractor.java   2.0     June 9,2016
 *
 * Mahendra Thapa
 *
 * Institute of Engineering
 */

package com.sarangi.app;

import com.sarangi.audioTools.*;
import com.sarangi.audioFeatures.*;
import com.sarangi.json.*;
import com.sarangi.structures.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import java.util.logging.Level;

/**
 * A class for extracting the features of the audio songs. The audio songs is stored
 * in resources folder. The audio features is extract in json format and stores in feature.txt file.
 * The audio features extracted at once for one song and the features.txt file is updated when
 * the new song is added.
 *
 * <p>Include constructor for setting the level of logger.
 *
 * <p>Include method to extract the audio feature form audio songs and store in file in json format.
 *
 * @author  Mahendra Thapa
 */

public class FeatureExtractor{

    /*FIELDS**************************************************/

    /**
     * A reference to the frame size;
     */
    private static final int frameSize = 1024;

    /**
     * A reference to the overlapping of the audio signal.
     */
    private static final int overLapSize = 512;


    /**
     * Extract the feature of song present in given folder Name and stores the features vector in given file name is json format.
     *
     * @param   fileName        A reference to file name in which the features in json format is stored.
     *
     * @param   folderName      A reference to folder name which contain the songs.
     *
     * @throws  Exception       Throws an Exception if any error occurs.
     */

    public static void extractFeature(String fileName, String folderName){

        LoggerHandler loggerHandler = LoggerHandler.getInstance();

        List<Song> songs = new ArrayList<Song>();

        SongHandler fileHandler = new SongHandler(fileName);
        List<Song> tempSong = fileHandler.loadSongs();

        File folder = new File(folderName);

        File[] listOfFiles = folder.listFiles();

        Arrays.sort(listOfFiles);

        int numOfFiles = listOfFiles.length;

        for(int i=0; i<numOfFiles; ++i){

            boolean flag = false;
            String songName = listOfFiles[i].getName();

            for(Song singleSong : tempSong){

                if(singleSong.getSongName().equals(songName)){

                    flag = true;
                    tempSong.remove(singleSong);
                    break;

                }

            }

            try{
                if(!flag){

                    String singleFileName = folderName +"/" + songName;
                    Song song = extractSongFeature(singleFileName);
                    songs.add(song);

                    loggerHandler.loggingSystem(LoggerHandler.LogType.FEATURE_EXTRACTION,
                            Level.INFO,
                            "Feature of " +singleFileName+ " is extracted successfully");
                }

            } catch(UnsupportedAudioFileException ex){

                loggerHandler.loggingSystem(LoggerHandler.LogType.FEATURE_EXTRACTION,
                        Level.SEVERE,
                        ExceptionPrint.getExceptionPrint(ex));
                continue;

            } catch(IOException ex){

                loggerHandler.loggingSystem(LoggerHandler.LogType.FEATURE_EXTRACTION,
                        Level.SEVERE,
                        ExceptionPrint.getExceptionPrint(ex));
                continue;

            } catch(IllegalArgumentException ex){

                loggerHandler.loggingSystem(LoggerHandler.LogType.FEATURE_EXTRACTION,
                        Level.SEVERE,
                        ExceptionPrint.getExceptionPrint(ex));
                continue;
            }
        }

        fileHandler.storeSongs(songs,true);
    }

    /**
     * Extract the feature of given song and stores the features vector in given file name is json format.
     *
     * @param   songName      A reference to the name of the song file.
     *
     * @throws  Exception     Throws an Exception if any error occurs.
     */

    public static Song extractSongFeature(String songName)
        throws UnsupportedAudioFileException, IOException, IllegalArgumentException 
    {

        File songFile = new File(songName);
        AudioSample audioSample = new AudioSample(songFile);

        double[] samples = audioSample.getAudioSamples();

        double samplingRate = audioSample.getAudioFormat().getSampleRate();

        List<double[]> audioFrames = AudioPreProcessor.getAudioFrame(samples,frameSize,overLapSize,false,false);
        List<double[]> windowAudioFrames = AudioPreProcessor.getAudioFrame(samples,frameSize,overLapSize,true,false);

        double[][] powerSpectrumFrame = PowerSpectrum.extractFeature(windowAudioFrames,samplingRate);
        double[][] magnitudeSpectrumFrame = MagnitudeSpectrum.extractFeature(windowAudioFrames,samplingRate);
        double[][] rmsFrame = RMS.extractFeature(windowAudioFrames,samplingRate);

        double[] compactness = Statistics.getAvgSD(Compactness.extractFeature(magnitudeSpectrumFrame,samplingRate));
        double[] melFreq = Statistics.getAvgSD(MelFreq.extractFeature(audioFrames,samplingRate));
        double[] pitch = Statistics.getPitchAnalysis(Pitch.extractFeature(audioFrames,samplingRate));
        double[] rhythm = Statistics.getRhythmAnalysis(Rhythm.extractFeature(rmsFrame,samplingRate));
        double[] rms = Statistics.getAvgSD(rmsFrame);
        double[] spectralCentroid = Statistics.getAvgSD(SpectralCentroid.extractFeature(powerSpectrumFrame,samplingRate));
        double[] spectralFlux = Statistics.getAvgSD(SpectralFlux.extractFeature(magnitudeSpectrumFrame,samplingRate));
        double[] spectralRolloffPoint = Statistics.getAvgSD(SpectralRolloffPoint.extractFeature(powerSpectrumFrame,samplingRate));
        double[] spectralVariablility = Statistics.getAvgSD(SpectralVariability.extractFeature(magnitudeSpectrumFrame,samplingRate));
        double[] zeroCrossing = Statistics.getAvgSD(ZeroCrossings.extractFeature(windowAudioFrames,samplingRate));

        return new Song(songName,compactness,melFreq,pitch,rhythm,rms,spectralCentroid,
                spectralFlux,spectralRolloffPoint,spectralVariablility,zeroCrossing);
    }
}
