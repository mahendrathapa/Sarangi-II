/**
 * @(#) FileHandler.java 2.0     July 19, 2016
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

public class FileHandler {

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
        private String filename;


        /* CONSTRUCTORS *******************************************/

        /**
         * Initialize the filename.
         * Also, set the level of the log according to the status in which the program is used.
         * The log levels are SEVERE, WARNING and INFO mainly.
         *
         * @param filename The file from which to load the songs.
         */
        public FileHandler(String filename) {

                this.filename = filename;
                logger.setLevel(Level.SEVERE);

        }

        /**
         * Set the filename.
         *
         * @param filename The file from which to load the songs
         */
        public void setFilename(String filename) {
                this.filename = filename;
        }

        /**
         * Load the Songs.
         *
         * <p>
         * Uses @see com.sarangi.json.JSONFormat to load the songs.
         *
         */
        public void loadSongs() {

                //TODO JSONFormat is being instantiated unnecessarily.
                
                JSONFormat jsonFormat = new JSONFormat();

                if (loadedSongs!=null) {
                    loadedSongs.clear();
                }

                loadedSongs = jsonFormat.convertJSONtoArray(filename);

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
