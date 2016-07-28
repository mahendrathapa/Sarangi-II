/**
 * @(#) SongHandler.java 2.0     July 19, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.json;

import com.sarangi.structures.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Class for loading all Songs stored beforehand.
 * @see com.sarangi.structures.Song
 *
 *
 * @author Bijay Gurung
 */

public class SongHandler {

        /* FIELDS **************************************************/


        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("SongLoader");

        /**
         * The songs to be loaded from the file.
         *
         */
        private List<Song> loadedSongs;

        /**
         * The file from which to load the songs.
         *
         */
        private String fileName;


        /* CONSTRUCTORS *******************************************/

        /**
         * Initialize the fileName.
         * Also, set the level of the log according to the status in which the program is used.
         * The log levels are SEVERE, WARNING and INFO mainly.
         *
         * @param fileName The file from which to load the songs.
         */
        public SongHandler(String fileName) {

                this.fileName = fileName;
                logger.setLevel(Level.SEVERE);

        }

        /**
         * Set the fileName.
         *
         * @param fileName The file from which to load the songs
         */
        public void setFilename(String fileName) {
                loadedSongs = new ArrayList<Song>();
                this.fileName = fileName;
        }



        /** First the given arraylist is converted into json format and then stored
         * in the text file.
         *
         * @param   songs            A reference to the array list of an audio features of the audio samples.    
         *
         * @throws  IOException     Throws an IOException if any error occurs.
         */

        public void storeSongs(List<Song> songs){

                Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

                try{

                        FileWriter fileWriter = new FileWriter(fileName,true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        for(Song song : songs){

                                String json = gson.toJson(song);
                                bufferedWriter.write(json + '\n');
                        }

                        bufferedWriter.close();

                } catch(IOException ex){

                        logger.log(Level.SEVERE,ex.toString(),ex);
                        System.out.println("Program Terminating");
                        System.exit(0);
                }
        }


        /** 
         * Return the arraylist of the features. Text file is read where is feature vector is stored
         * int the form of json format and then converted into array list.
         *
         * @throws  IOException     Throw an exception if any exception occurs.
         *
         */

        public List<Song> loadSongs(){

                Gson gson = new Gson();

                List<Song> tempLoadedSongs = new ArrayList<Song>();

                try{

                        File file = new File(fileName);

                        if(file.length() == 0)
                                return new ArrayList<Song>();

                        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

                        for(String line; (line = bufferedReader.readLine()) != null;){

                                tempLoadedSongs.add(gson.fromJson(line,Song.class));
                        }

                        loadedSongs = tempLoadedSongs;
                        return tempLoadedSongs;

                } catch(IOException ex){

                        return new ArrayList<Song>();
                }
        }

        /**
         * Return the loaded songs.
         *
         * @return Loaded songs.
         *
         *
         */
        public List<Song> getSongs() {

                return loadedSongs;

        }

}
