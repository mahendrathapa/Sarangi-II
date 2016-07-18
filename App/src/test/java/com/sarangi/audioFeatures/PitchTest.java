import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;
import com.sarangi.audioFeatures.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

/**
 * Unit test for Pitch
 */

public class PitchTest extends TestCase{

        public void testPitch(){

                try{

                        AudioSample audioSample = new AudioSample(new File("src/resources/song/training/jazz.00001.au"));

                        float[] sample = audioSample.getAudioSamples();

                        AudioFormat audioFormat = audioSample.getAudioFormat();

                        AudioPreProcessor audioPreProcessor = new AudioPreProcessor();

                        List<float[]> audioFrame = audioPreProcessor.getAudioFrame(sample,1024,0);

                        System.out.println(audioFrame.size());

                        Pitch  pitch = new Pitch(audioFrame,audioFormat);

                        List<Float> pitchOutput = pitch.getPitchFeatures();

                        for(float temp: pitchOutput)
                                System.out.print(temp + " ");

                }catch(UnsupportedAudioFileException ex){
                        System.out.println(ex);
                }catch(IOException ex){
                        System.out.println(ex);
                }catch(IllegalArgumentException ex){
                        System.out.println(ex);
                }



        }
}
