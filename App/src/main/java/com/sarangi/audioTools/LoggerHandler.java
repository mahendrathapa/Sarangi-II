/**
 * @(#) LoggerHandler.java 2.0   August 04, 2016
 *
 * Mahendra Thapa
 *
 * Insitute of Engineering
 */

package com.sarangi.audioTools;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerHandler{

        private static LoggerHandler instance;

        private Logger logger;
        private FileHandler fileHandler;
        private SimpleFormatter formatter;

        private final int FILE_SIZE = 1024 * 1024;

        private LoggerHandler(){

                logger = Logger.getLogger("SarangiLog");
                logger.setLevel(Level.ALL);
                formatter = new SimpleFormatter();
        }


        public enum LogType{

                FEATURE_EXTRACTION,
                LEARNING_MODEL
        }

        public static synchronized LoggerHandler getInstance(){

                if(instance == null){
                        instance = new LoggerHandler();
                }

                return instance;
        }

        public void loggingSystem(LogType logType, Level level, String logMessage){

                try{

                        if(logType == LogType.FEATURE_EXTRACTION)
                                fileHandler = new FileHandler("src/resources/log/featureExtractor.log",FILE_SIZE,10,true);

                        else if(logType == LogType.LEARNING_MODEL)
                                fileHandler = new FileHandler("src/resources/log/learningModel.log",FILE_SIZE,10,true);

                }catch(IOException ex){
                        logger.warning("Failed to initilize logger handler");
                }

                logger.addHandler(fileHandler);
                fileHandler.setFormatter(formatter);

                logger.log(level,logMessage);
                fileHandler.close();
        }

        public void setLogLevel(Level level){
                logger.setLevel(level);
        }

}

