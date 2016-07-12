package com.sarangi.audioTools;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;
import java.util.*;

/**
 * Unit test for AudioPreprocessing.
 */

public class AudioPreprocessingTest extends TestCase{

        public void testGetAudioFrame(){

                AudioPreprocessing audioPreprocessing = new AudioPreprocessing();

                float[] temp = new float[]{(float)1.0, (float)2.0, (float)1.0, (float)2.2, (float)1.1, (float)3.4, (float)1, (float)1, (float)34, (float)8, (float)7};

                List<float[]> output = audioPreprocessing.getAudioFrame(temp,2);

                for(float[] print : output){
//                        System.out.println(Arrays.toString(print));
                }

        }
}


