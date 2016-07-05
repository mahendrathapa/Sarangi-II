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

/**
 * A class for extracting the features of the audio songs. The audio songs is stored
 * in resources folder. The audio features is extract in json format and stores in feature.txt file.
 * The audio features extracted at once for one song and the features.txt file is updated when
 * the new song is added. 
 *
 * <p>Include method ot extract the audio feature form audio songs and store in file in json format.
 *
 * @author  Mahendra Thapa
 */

public class FeatureExtractor{

        /**
         * Extract the feature of song present in given folder Name and stores the features vector in given file name is json format.
         *
         * @param   fileName        A reference to file name in which the features in json format is stored.
         *
         * @param   folderName      A reference to folder name which contain the songs.
         */

        public void extractFeature(String fileName, String folderName){

                JSONFormat jsonFormat = new JSONFormat();
                List<Song> song = new ArrayList<Song>();

                try{

                        File file = new File(fileName);

                        List<Song> tempSong = jsonFormat.convertJSONtoArray(fileName);

                        File folder = new File(folderName);

                        File[] listOfFiles = folder.listFiles();
                        
                        Arrays.sort(listOfFiles);

                        int numOfFiles = listOfFiles.length;

                        for(int i=0; i<numOfFiles; ++i){

                                boolean flag = false;
                                String songName = listOfFiles[i].getName();

                                for(Song singleSong : tempSong){
                                        if(singleSong.songName.equals(songName)){
                                                song.add(singleSong);
                                                flag = true;
                                                tempSong.remove(singleSong);
                                                break;

                                        }

                                }

                                try{
                                        if(!flag){

                                                File singleFileName = new File(folderName+"/"+songName);

                                                System.out.println(singleFileName);

                                                AudioSample audioSample = new AudioSample(singleFileName);

                                                double[] samples = audioSample.getAudioSamples();

                                                AudioFormat audioFormat = audioSample.getAudioFormat();

                                                Intensity intensity = new Intensity(samples,audioFormat);
                                                double intensityFeatures = intensity.getIntensityFeatures();

                                                song.add(new Song(songName,intensityFeatures));

                                        }

                                }catch(Exception ex){
                                        ex.printStackTrace();
                                }
                        }

                        jsonFormat.convertArrayToJSON(song,fileName);

                }catch(Exception ex){
                        ex.printStackTrace();
                }
        }


}

