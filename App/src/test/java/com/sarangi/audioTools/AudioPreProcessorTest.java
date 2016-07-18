package com.sarangi.audioTools;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;

import java.util.*;

/**
 * Unit test for Audio Pre-Processing.
 */

public class AudioPreProcessorTest extends TestCase{

        public void testAudioPreProcessor(){

                AudioPreProcessor audioPreProcessor = new AudioPreProcessor();

                float[] input = new float[]{
                        (float)1.0,(float)2.0,(float)3.0,(float)4.0,(float)5.0,(float)6.0,(float)7.0,(float)8.0,(float)9.0,(float)10.0
                };

                List<float[]> output = audioPreProcessor.getAudioFrame(input,3,1);

                System.out.println(Arrays.toString(input));
                
                for(float[] temp: output)
                        System.out.println(Arrays.toString(temp));



        }
}


