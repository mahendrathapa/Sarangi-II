package com.sarangi.audioTools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.*;
import javax.sound.sampled.*;

public class ExceptionPrint{
        
        private static StringWriter stringWriter = new StringWriter();
        private static PrintWriter printWriter = new PrintWriter(stringWriter);             
                
        public static String getExceptionPrint(Exception ex){

                ex.printStackTrace(printWriter);
                return stringWriter.toString().toUpperCase();
        }      

        public static String getExceptionPrint(UnsupportedAudioFileException ex){

                ex.printStackTrace(printWriter);
                return stringWriter.toString().toUpperCase();
        }  

        public static String getExceptionPrint(IOException ex){

                ex.printStackTrace(printWriter);
                return stringWriter.toString().toUpperCase();
        }  
        public static String getExceptionPrint(IllegalArgumentException ex){

                ex.printStackTrace(printWriter);
                return stringWriter.toString().toUpperCase();
        }       
        
}
