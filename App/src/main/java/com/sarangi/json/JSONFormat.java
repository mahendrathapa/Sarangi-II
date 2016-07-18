/**
 * @(#) JSONFormat.java 2.0     June 9,2015
 *
 * Mahendra Thapa
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
 * A class for converting the json format into Array List and vice versa. The features are stored
 * in json format for their standariztion.
 *
 * <p>Includes constructor for setting the level of logger.
 *
 * <p>Includes methods for converting the json file into array list.
 *
 * <p>Includes methods for converting the array list into json file.
 *
 * @author Mahendra Thapa
 */

public class JSONFormat{

        /* FILEDS **************************************************/


        /**
         * Logger is used to maintain the log of the program. The log contain the error message generated during the
         * execution of the program, warning messages to the user and information about the status of the program
         * to the user. The log is also beneficial during program debugging.
         */
        private Logger logger = Logger.getLogger("JSONFormat");


        /* CONSTRUCTORS *******************************************/

        /**
         * Set the level of the log according to the status in which the program is used.
         * The log levels are SEVERS, WARNING and INFO mainly.
         */

        public JSONFormat(){

                logger.setLevel(Level.SEVERE);
        }

        /** First the given arraylist is converted into json format and then stored
         * in the text file.
         *
         * @param   song            A reference to the array list of an audio features of the audio samples.    
         *
         * @param   fileName        A referece to the file name which store the feature vector in json format.
         *
         * @throws  IOException     Throws an IOException if any error occurs.
         */

        public void convertArrayToJSON(List<Song> song, String fileName){

                Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

                String json = gson.toJson(song);

                try{
                        FileWriter fileWriter = new FileWriter(fileName);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(json);
                        bufferedWriter.close();

                }catch(IOException ex){
                        logger.log(Level.SEVERE,ex.toString(),ex);
                        System.out.println("Program Terminating");
                        System.exit(0);
                }
        
        }


        /** 
         * Return the arraylist of the features. Text file is read where is feature vector is stored
         * int the form of json format and then converted into array list.
         *
         * @param   fileName        A referece to the file in which the features vector is stored in json format.
         *
         * @throws  IOException     Throw an exception if any exception occurs.
         *
         */

        public List<Song> convertJSONtoArray(String fileName){

                Gson gson = new Gson();


                try{

                        File file = new File(fileName);

                        if(file.length() == 0)
                                return new ArrayList<Song>();

                        JsonReader json = new JsonReader(new FileReader(fileName));

                        Type listType = new TypeToken<List<Song>>(){}.getType();

                        return gson.fromJson(json,listType);

                }catch(IOException ex){

                        return new ArrayList<Song>();
                }

        }

}


