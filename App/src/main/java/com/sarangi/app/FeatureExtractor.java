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
import java.util.logging.*;
import javax.sound.sampled.*;

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

        /* FILEDS **************************************************/

        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("FeatureExtractor");

        /**
         * AudioPreProcessor is used for pre-processing of the audio signal.
         */

        private AudioPreProcessor audioPreProcessor = new AudioPreProcessor();

        /**
         * A reference to the frame size;
         *
         */
        private final int frameSize = 1024;

        /**
         * A reference to the overlapping of the audio signal.
         */
        private final int overLapSize = 0;

        /* CONSTRUCTORS *******************************************/

        /**
         * Set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         *
         * Also, Define the AudioPreprocessing.
         */

        public FeatureExtractor(){

                logger.setLevel(Level.INFO);

        }


        /**
         * Extract the feature of song present in given folder Name and stores the features vector in given file name is json format.
         *
         * @param   fileName        A reference to file name in which the features in json format is stored.
         *
         * @param   folderName      A reference to folder name which contain the songs.
         *
         * @throws  Exception       Throws an Exception if any error occurs.
         */

        public void extractFeature(String fileName, String folderName){

                JSONFormat jsonFormat = new JSONFormat();

                List<Song> song = new ArrayList<Song>();

                List<Song> tempSong = jsonFormat.convertJSONtoArray(fileName);

                File folder = new File(folderName);

                File[] listOfFiles = folder.listFiles();

                Arrays.sort(listOfFiles);

                int numOfFiles = listOfFiles.length;

                for(int i=0; i<numOfFiles; ++i){

                        boolean flag = false;
                        String songName = listOfFiles[i].getName();

                        for(Song singleSong : tempSong){
                                if(singleSong.getSongName().equals(songName)){
                                        song.add(singleSong);
                                        flag = true;
                                        tempSong.remove(singleSong);
                                        break;

                                }

                        }

                        try{
                                if(!flag){

                                        File singleFileName = new File(folderName+"/"+songName);

                                        logger.info(singleFileName.toString());

                                        AudioSample audioSample = new AudioSample(singleFileName);

                                        float[] samples = audioSample.getAudioSamples();

                                        AudioFormat audioFormat = audioSample.getAudioFormat();

                                        List<float[]> audioFrame = audioPreProcessor.getAudioFrame(samples,frameSize,overLapSize);
                                        
                                        Intensity intensity = new Intensity(audioFrame,audioFormat);
                                        List<Float> intensityFeatures = intensity.getIntensityFeatures();

                                        Melfreq melfreq = new Melfreq(audioFrame,audioFormat);
                                        List<float[]> mfccFeatures = melfreq.getMfccFeatures();

                                        song.add(new Song(songName,intensityFeatures,mfccFeatures));

                                }

                        } catch(UnsupportedAudioFileException ex){
                                logger.log(Level.SEVERE,ex.toString(),ex);
                                continue;

                        } catch(IOException ex){
                                logger.log(Level.SEVERE,ex.toString(),ex);
                                continue;

                        } catch(IllegalArgumentException ex){
                                logger.log(Level.SEVERE,ex.toString(),ex);
                                continue;
                        }
                }

                jsonFormat.convertArrayToJSON(song,fileName);

        }


}
