import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;
import com.sarangi.audioFeatures.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

/**
 * Unit test for Rhythm
 */

public class RhythmTest extends TestCase{

        public void testRhythm(){

                try{

                        AudioSample audioSample = new AudioSample(new File("src/resources/song/Mood_training/hvha.1.wav"));

                        float[] sample = audioSample.getAudioSamples();

                        AudioFormat audioFormat = audioSample.getAudioFormat();

                        AudioPreProcessor audioPreProcessor = new AudioPreProcessor();

                        List<float[]> audioFrame = audioPreProcessor.getAudioFrame(sample,1024,512);

                        Rhythm  rhythm = new Rhythm(audioFrame,audioFormat);

                        int[] rhythmGraph = rhythm.getRhythmGraph();

                        System.out.println(Arrays.toString(rhythmGraph));

                }catch(UnsupportedAudioFileException ex){
                        System.out.println(ex);
                }catch(IOException ex){
                        System.out.println(ex);
                }catch(IllegalArgumentException ex){
                        System.out.println(ex);
                }



        }
}
