/*
 * @(#) AudioSamples.java 2.0   June 5, 2016.
 *
 * Mahendra Thapa
 *
 * Institute of Engineering
 */ 

package com.sarangi.audioTools;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.*;


/**
 * A class for holding audio samples and associated audio formatting information.
 * Samples are stored as arrays of doubles, generally for use in analysis and 
 * signal processing. Samples are stored maintaining any channel segregation as
 * well as mixed down to a single channel. Values can vary between -1 and +1.
 *
 * <p>Includes constructors for generating and storing the samples form 
 * AudioInputStreams, audio files or existing arrys of sample values.
 *
 * <p>Includes methods for accessing samples as a whole.
 *
 * <p>Includes methods for accesing the AudioFormat of given audio signal.
 *
 *
 * @author  Mahendra Thapa
 *
 */

public class AudioSample{

        /* FIELDS **************************************************/

        /**
         * Audio samples, with a minimum value of -1 and a maximum value of +1. If 
         * audio is multi-channel, all channels are mixed down into this one channel.
         *
         */
        protected double[]  samples;

        /**
         * Audio samples, with a minimum value of -1 and a maximum value of +1. Is 
         * set to null if only one channel of audio is present. First indice
         * corresponds to channel and second indice corresponds to sample number.
         *
         */

        protected double[][]    channelSamples;

        /**
         * The AudioFormat used to encode the samples field. Will always involve
         * big-endian signed linear PCM encoding and a bit depth of either 8 or 16 bits.
         *
         */

        protected AudioFormat   audioFormat;


        /* CONSTRUCTORS ******************************************/

        /**
         * Store the given audio files as samples and the corresponding 
         * AudioFormat.
         *
         * <p> Regardless of the AudioFormat in the given file,
         * it will be converted and stored using big-endian signed linear PCM encoding.
         * Sampling rate and number of channels is maintained, but bit depth will be changed to 16 bits if it
         * not either 8 or 16 bits.
         *
         * @param   audioFile       A reference to an audio file from which to extract 
         *                          and store samples as double values.
         *
         * @throws  EXception       Throws an information exception if the samples cannot be 
         *                          extracted from the file.
         *
         */

        public AudioSample(File audioFile) throws Exception{

                if(!audioFile.exists())
                        throw new Exception("File " + audioFile.getName() + "does not exist.");

                if(!audioFile.exists())
                        throw new Exception("File " + audioFile.getName() + " does not exist.");
                if(audioFile.isDirectory())
                        throw new Exception("File " + audioFile.getName() + " is a directory.");

                AudioInputStream audioInputStream = null;

                try{
                        audioInputStream = AudioSystem.getAudioInputStream(audioFile); 
                } catch(UnsupportedAudioFileException ex){
                        throw new Exception("File " + audioFile.getName() + " has an unsupported audio format.");
                } catch(IOException ex){
                        throw new Exception("File " + audioFile.getName() + " is not readable.");
                }

                AudioFormat originalAudioFormat = audioInputStream.getFormat();
                audioFormat = getConvertedAudioFormat(originalAudioFormat);


                if(!audioFormat.matches(originalAudioFormat)){

                        audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
                        audioFormat = audioInputStream.getFormat();
                }

                channelSamples = extractSampleValues(audioInputStream);

                samples = getSamplesMixedDownIntoOneChannel(channelSamples);

                audioInputStream.close();


        }

         /**
         * Returns an AudioFormat with the same sampling rate and number of channels as the passed AudioFormat.
         * If the bit depth is something other than 8 or 16 bits, then it is converted to 16 bits. The returned
         * AudioFormat, also, will use big-endian signed linear PCM encoding, regardless of the passed format.
         *
         * @param   originalFormat      The format form which to extract sample rate, 
         *                              bit depth and number of channels.
         * 
         * @return                      The new format using big-endian signed linear PCM
         *                              encoding and either 8 or 16 bit depth.
         */

        public AudioFormat getConvertedAudioFormat(AudioFormat originalFormat){

                int bitDepth = originalFormat.getSampleSizeInBits();

                if(bitDepth != 8 && bitDepth != 16)
                        bitDepth = 16;

                return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                originalFormat.getSampleRate(),
                                bitDepth,
                                originalFormat.getChannels(),
                                originalFormat.getChannels()*(bitDepth/8),
                                originalFormat.getSampleRate(),
                                true);
        }

        /**
         * Returns an array of doubles representing the samples for each channel in the given AudioInputStream
         *
         * @param   audioInputStream    The AudioInputStream to convert to sample value.
         *
         * @return                      A 2-D array of sample values whose first indices indicates sample umber
         *                              channel and whose second indice indicates sample number.
         *                              In stereo, indice 0 corresponds to left and 1 to right.
         *                              All samples should fall between -1 and +1.
         *
         * @throws  Exception           Throws an informative exception if an invalid parameter is
         *                              provided.
         */

        public double[][] extractSampleValues(AudioInputStream audioInputStream) throws Exception{

                //Converts the contents of audioInputStream into an array of bytes

                byte[] audioBytes = getBytesFromAudioInputStream(audioInputStream);

                int numberBytes = audioBytes.length;

                AudioFormat thisAudioFormat = audioInputStream.getFormat();

                //Extract information from thisAudioFormat
                int numberOfChannels = thisAudioFormat.getChannels();
                int bitDepth = thisAudioFormat.getSampleSizeInBits();

                //Find the number of samples in the audio bytes
                int numberOfBytes = audioBytes.length;
                int bytesPerSample = bitDepth/8;
                int numberSamples = numberOfBytes / bytesPerSample / numberOfChannels;


                //Find the maximum possible value that a sample may have with the given bit depth
                double maxSampleValue = Math.pow(2,audioFormat.getSampleSizeInBits()-1);

                double[][] sampleValue = new double[numberOfChannels][numberSamples];

                //Convert the bytes to double samples
                ByteBuffer byteBuffer = ByteBuffer.wrap(audioBytes);

                if(bitDepth == 8){

                        for (int samp = 0; samp < numberSamples; ++samp)
                                for(int chan = 0; chan <numberOfChannels; ++chan)
                                        sampleValue[chan][samp] = (double)byteBuffer.get()/maxSampleValue;

                }else if(bitDepth == 16){

                        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();

                        for (int samp = 0; samp < numberSamples; ++samp)
                                for(int chan = 0; chan <numberOfChannels; ++chan)
                                        sampleValue[chan][samp] = (double)shortBuffer.get()/maxSampleValue;
                }

                return sampleValue;
        }

        /**
         * Generates an array of audio bytes based on the contents of the given AudioInputStream
         *
         * @param   audioInputStream    The AudioInputStream to extract the bytes from.
         *
         * @return                      The audio bytes extracted from the AudioInputStream.
         *                              Has the same AudioFileFormat as the specified AudioInputStream.
         *
         * @throws  Exception           Throws an expcetion if a problem occurs.
         */

        public byte[] getBytesFromAudioInputStream(AudioInputStream audioInputStream) throws Exception{

                //Calculate the buffer size to use
                float bufferDurationInSeconds = 0.25F;
                int bufferSize = getNumberBytesNeeded(bufferDurationInSeconds,audioInputStream.getFormat());

                byte rwBuffer[] = new byte[bufferSize + 2];

                //Read the bytes into rwBuffer and then into the ByteArrayOutputStream

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                int position = audioInputStream.read(rwBuffer,0,rwBuffer.length);

                while(position > 0){

                        byteArrayOutputStream.write(rwBuffer,0,position);
                        position = audioInputStream.read(rwBuffer,0,rwBuffer.length);
                }

                byte[] results = byteArrayOutputStream.toByteArray();

                try{
                        byteArrayOutputStream.close();
                }catch(IOException e){

                        System.out.println(e);
                        System.exit(0);
                }

                return results;
        }


        /**
         * Returns the number of bytes needed to store samples corresponding to audio of fixed duration.
         *
         * @param   durationInSeconds   The duration, in seconds, fo the audio that needs to be stored.
         *
         * @param   audioFormat         The AudioFormat of the samples to be stored.
         *
         * @return                      The number of bytes needed to stroe the samples.
         *
         */

        public int getNumberBytesNeeded(double durationInSeconds, AudioFormat audioFormat){

                int frameSizeInBytes = audioFormat.getFrameSize();
                float frameRate = audioFormat.getFrameRate();
                return (int) (frameSizeInBytes * frameRate * durationInSeconds);
        }

        /**
         * Returns the given set of samples as a set of samples mixed down into one channel.
         *
         * @param   audioSamples    Audio samples to modify, with a minimum value of -1 and a maximum
         *                          value of +1. The first indice corresponds to the channel and the second
         *                          indice corresponds to the sample number.
         *
         * @return                  The given audio samples mixed down, with equal gain, into one channel.
         *
         */

        public double[] getSamplesMixedDownIntoOneChannel(double[][] audioSamples){

                if(audioSamples.length == 1)
                        return audioSamples[0];

                double numberChannels = (double)audioSamples.length;
                int numberSamples = audioSamples[0].length;

                double[] samplesMixedDown = new double[numberSamples];

                for(int samp=0; samp < numberSamples; ++samp){
                        double totalSoFar = 0.0;
                        for(int chan = 0; chan < numberChannels; ++chan){
                                totalSoFar += audioSamples[chan][samp];
                        }
                        samplesMixedDown[samp] = totalSoFar / numberChannels;
                }

                return samplesMixedDown;

        }

        /**
         * Returns the AudioFormat associated with the stored samples.
         *
         * @return  The AudioFormat associated with the stored samples.
         *
         */

        public AudioFormat getAudioFormat(){
                return audioFormat;
        }

        /**
         * Returns the audio samples
         *
         * @return  The audios samples of the given audio signal.
         */
        public double[] getAudioSamples(){
                return samples;
        }


}

