package com.sarangi.learningmodel.ann; 

import java.io.*;
import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.XStreamException;

import com.sarangi.app.Config;
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
    public SarangiANN(List<Song>trainingSongs, String[] labels) {

        super(trainingSongs,labels);

    }

    /**
     * Train the model in a Neural Network.
     *
     * @param trainingSongs The songs to be used for training.
     *
     */

    @Override
    public void train(List<Song> trainingSongs) {
        this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels);

        ann = new NeuralNetwork(NeuralNetwork.ErrorFunction.LEAST_MEAN_SQUARES,
                                NeuralNetwork.ActivationFunction.LOGISTIC_SIGMOID,
                                trainingSet.dataset[0].length,Config.ANN_HIDDEN_NODES,
                                this.labels.length + 1);

        ann.setLearningRate(Config.ANN_LEARNING_RATE);
        ann.setMomentum(Config.ANN_MOMENTUM); 
        ann.setWeightDecay(Config.ANN_WEIGHT_DECAY);

        for (int i=0; i<Config.ANN_EPOCH; i++) {
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

        double[] dataset = DatasetUtil.getSongData(song);
        return ann.predict(dataset);

    }
    
    /**
     * Store the ann object.
     *
     * @param filename The file where the object is to be stored.
     *
     */

    public void store(String filename) throws IOException, XStreamException {
            FileWriter fileWriter = new FileWriter(filename);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            XStream xstream = new XStream(new StaxDriver());
            String xml = xstream.toXML(ann);

            bufferedWriter.write(xml);

            bufferedWriter.close();

   }    

    /**
     * Load the ann object.
     *
     * @param filename The file where the object is to be loaded from..
     *
     */

    public void load(String filename) throws IOException, XStreamException {

            File file = new File(filename);

            if(file.length() == 0)
                return;

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String xml = bufferedReader.readLine();

            XStream xstream = new XStream(new StaxDriver());
            this.ann = (NeuralNetwork)xstream.fromXML(xml);

            bufferedReader.close();

    }
}
