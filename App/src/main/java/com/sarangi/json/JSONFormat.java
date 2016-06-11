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

/**
 * A class for converting the json format into Array List and vice versa. The features are stored
 * in json format for their standariztion. 
 *
 * <p>Includes methods for converting the json file into array list.
 *
 * <p>Includes methods for converting the array list into json file.
 *
 * @author Mahendra Thapa
 */

public class JSONFormat{

        /**
         * First the given arraylist is converted into json format and then stored
         * in the text file.
         *
         * @param   song            A reference to the array list of an audio features of the audio samples.    
         *
         * @param   fileName        A referece to the file name which store the feature vector in json format.
         *
         * @throws  IOException     Throws an IOException if any error occurs.
         */

        public void convertArrayToJSON(ArrayList<Song> song, String fileName) throws IOException{

                Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
                String json = gson.toJson(song);

                try{
                        FileWriter fileWriter = new FileWriter(fileName);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(json);
                        bufferedWriter.close();

                }catch(IOException ex){
                        throw new IOException(ex);
                }

        }


        /** 
         * Return the arraylist of the features. Text file is read where is feature vector is stored
         * int the form of json format and then converted into array list.
         *
         * @param   fileName        A referece to the file in which the features vector is stored in json format.
         *
         * @throws Exception        Throw an exception if any exception occurs.
         *
         */

        public ArrayList<Song> convertJSONtoArray(String fileName)throws Exception{

                Gson gson = new Gson();

                ArrayList<Song> allSongs;

                try{
                        JsonReader json = new JsonReader(new FileReader(fileName));

                        Type listType = new TypeToken<ArrayList<Song>>(){}.getType();
                        allSongs = gson.fromJson(json,listType);

                }catch(Exception ex){
                        throw new Exception(ex);

                }

                return allSongs;

        }

}


