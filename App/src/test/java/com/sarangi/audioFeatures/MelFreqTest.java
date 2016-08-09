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

                double[] one = new double[]{
                        1,2,3,4

                };
                double[] two = new double[]{
                        1,1
                };
                System.out.println(Arrays.toString(Statistics.mergeArrays(one,two)));

                try{
                        AudioSample audioSample = new AudioSample(new File("src/resources/song/extra/abc.wav"));

                        double[] samples = audioSample.getAudioSamples();

                        double samplingRate = audioSample.getAudioFormat().getSampleRate();

                        AudioPreProcessor audioPreProcessor = new AudioPreProcessor();
                        List<double[]> audioFrames = audioPreProcessor.getAudioFrame(samples,1024,512);

                        double[][] pSFeature  = PowerSpectrum.extractFeature(audioFrames,samplingRate);

                        double[][] mSFeature = MagnitudeSpectrum.extractFeature(audioFrames,samplingRate);

                        double[] compactnessFeature = Statistics.getAvgSD(Compactness.extractFeature(mSFeature));

                        System.out.println(Arrays.toString(compactnessFeature));
                
                }catch(UnsupportedAudioFileException ex){
                        System.out.println(ex);
                }catch(IOException ex){
                        System.out.println(ex);
                }catch(IllegalArgumentException ex){
                        System.out.println(ex);
                }

        }
}
