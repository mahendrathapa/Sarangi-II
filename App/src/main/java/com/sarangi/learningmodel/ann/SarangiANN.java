package com.sarangi.learningmodel.ann; 

import java.io.*;
import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import com.sarangi.structures.*;
import com.sarangi.json.SongHandler;
import com.sarangi.learningmodel.*;

import smile.classification.NeuralNetwork;
import smile.math.Math;
import java.lang.Math.*;

/**
 * Artificial Neural Network Class.
 * For supervised learning of the music features
 *
 * @author Mehang Rai
 * */

public class SarangiANN extends SarangiClassifier {

    /* FIELDS **************************************************/

    /**
     * The NeuralNetwork object.
     *
     */
    public NeuralNetwork ann;


    /**
     * Constructor.
     *
     */
    public SarangiANN() {

    }

    /**
     * Constructor.
     *
     * @param trainingSongs The songs to be used for training
     * @param labels The string labels
     * @param featureType The type of feature to be used
     *
     */
    public SarangiANN(List<Song>trainingSongs, String[] labels, FeatureType featureType) {

        super(trainingSongs,labels,featureType);

    }

    /**
     * Train the model in a Neural Network.
     *
     * @param trainingSongs The songs to be used for training.
     *
     */

    @Override
    public void train(List<Song> trainingSongs) {
        this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels, featureType);

        ann = new NeuralNetwork(NeuralNetwork.ErrorFunction.LEAST_MEAN_SQUARES,NeuralNetwork.ActivationFunction.LOGISTIC_SIGMOID,DatasetUtil.DATASET_SIZE,10,this.labels.length + 1);
        int epoch = 2000;
        for (int i=0; i<epoch; i++) {
            ann.learn(trainingSet.dataset,trainingSet.labelIndices);
        }
    }

    /**
     * Predict the label for the given song.
     *
     * @param song The Song object whose label is to be predicted.
     *
     * @return The label index.
     */

    @Override 
    public int predict(Song song) {
        List<Song> oneSong = new ArrayList<Song>();
        oneSong.add(song);

        // TODO Get a better solution than this Hack
        LearningDataset songDataset = DatasetUtil.getSongwiseDataset(oneSong,this.labels,this.featureType);
        return ann.predict(songDataset.dataset[0]);
    }
    
    /**
     * Store the ann object.
     *
     * @param filename The file where the object is to be stored.
     *
     */

    public void store(String filename) {
        try{
            FileWriter fileWriter = new FileWriter(filename);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            XStream xstream = new XStream(new StaxDriver());
            String xml = xstream.toXML(ann);

            bufferedWriter.write(xml);

            bufferedWriter.close();

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load the ann object.
     *
     * @param filename The file where the object is to be loaded from..
     *
     */

    public void load(String filename) {
        try{

            File file = new File(filename);

            if(file.length() == 0)
                return;

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String xml = bufferedReader.readLine();

            XStream xstream = new XStream(new StaxDriver());
            this.ann = (NeuralNetwork)xstream.fromXML(xml);

            bufferedReader.close();

        } catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
