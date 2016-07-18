import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;
import com.sarangi.audioFeatures.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

/**
 * Unit test for MFCC
 */

public class MelFreqTest extends TestCase{

        public void testMelFreq(){

                try{

                        AudioSample audioSample = new AudioSample(new File("src/resources/song/extra/abc.wav"));

                        float[] sample = audioSample.getAudioSamples();

                        System.out.println(sample.length);

                        AudioFormat audioFormat = audioSample.getAudioFormat();

                        AudioPreProcessor audioPreProcessor = new AudioPreProcessor();

                        List<float[]> input = audioPreProcessor.getAudioFrame(sample,1024,0);

                        Melfreq melfreq = new Melfreq(input,audioFormat);

                        List<float[]> outputMFCC = melfreq.getMfccFeatures();

                        System.out.println(outputMFCC.size());

                }catch(UnsupportedAudioFileException ex){
                        System.out.println(ex);
                }catch(IOException ex){
                        System.out.println(ex);
                }catch(IllegalArgumentException ex){
                        System.out.println(ex);
                }



        }
}
