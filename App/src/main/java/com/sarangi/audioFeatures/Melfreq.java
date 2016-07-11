package com.sarangi.audioFeatures;

import java.io.*;
import javax.sound.sampled.*;
import java.nio.*;
import java.util.*;
import java.util.logging.*;
import java.lang.*;
import be.tarsos.dsp.mfcc.MFCC;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

public class Melfreq{

    //The vector for the mel frequency coefficients
    private Logger logger = Logger.getLogger("Melfreq");
    float[] Mfcc=new float[30];

    public Melfreq(double[] audioSamples)
    {
      float[] floatArray = new float[audioSamples.length];
      for (int i = 0 ; i < audioSamples.length; i++)
      {
        floatArray[i] = (float) audioSamples[i];
      }
      try{
        AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromFloatArray(floatArray,44100,1024,512);
        MFCC mfcc = new MFCC(1024,44100);
        audioDispatcher.addAudioProcessor(mfcc);
        audioDispatcher.run();
        Mfcc= mfcc.getMFCC();
        // for(float temp : Mfcc)
        // {
        //   System.out.println(temp);
        // }
      }
      catch(Exception ex)
      {
          logger.log(Level.SEVERE,ex.toString(),ex);
      }
    }

    public float[] getCoefficients()
    {
      return Mfcc;
    }
}
