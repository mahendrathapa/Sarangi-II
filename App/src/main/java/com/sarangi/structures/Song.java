package com.sarangi.structures;

import java.lang.Math.*;

/**
 * Simple Data Structure to hold information related to a song.
 *
 */

public class Song{

   public String songName;
   public double intensity;
   public double[] mfcc;
   public double[] rhythm;

   public Song(String songName, double intensity, double[] mfcc, double[] rhythm) {

        this.songName = songName;
        this.intensity = intensity;
        this.mfcc = mfcc.clone();
        this.rhythm = rhythm.clone();

   }

   public Song(String songName, double intensity){

	this.songName = songName;
	this.intensity = intensity; 

   }

   /* Return attribute*/

   public String getSongName(){
           return this.songName;
   }

   public double getIntensity(){
           return this.intensity;
   }

   public void setIntensity(double intensity){
           this.intensity = intensity;
   }

   public double[] getMFCC(){
           return this.mfcc;
   } 

   public void setMFCC(int i, double mfccpart){
           this.mfcc[i] = mfccpart;
   }

//a normalization approach
   public void normalizeFeature(){
           this.intensity = (1/(1+Math.exp(-this.intensity)));
           for(int i = 0; i < 16; i++){
                   this.mfcc[i] = (1/(1+Math.exp(-this.mfcc[i])));
           }
   }

   
}
