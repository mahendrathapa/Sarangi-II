package com.sarangi.learningmodel.svm; 

import java.io.*;

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

import com.sarangi.app.Config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.XStreamException;

import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;
import smile.math.kernel.MercerKernel;
import smile.math.Math;
import java.lang.Math.*;
import java.util.*;

/**
 * Support Vector Machine Class
 *
 * @author Mehang Rai
 * */


public class SarangiSVM extends SarangiClassifier {

    /* FIELDS **************************************************/

    /**
     * The SMILE SVM object
     *
     */

    public SVM svm; 

    public static double SIGMA = 30.0d;

    /* CONSTRUCTORS *******************************************/

    /**
     * Constructor.
     *
     */
    public SarangiSVM() {
        svm = new SVM(new GaussianKernel(SarangiSVM.SIGMA), 2.0d);
    }

    /**
     * Constructor.
     *
     * @param trainingSongs The songs to be used for training
     * @param labels The string labels
     * @param featureType The type of feature to be used
     *
     */
    public SarangiSVM(List<Song>trainingSongs, String[] labels) {

        super(trainingSongs, labels);

    }

    /**
     * Train the model using SVM
     *
     * @param trainingSongs The songs to be used for training.
     *
     */

    @Override
    public void train(List<Song> trainingSongs) {

        this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels);

        // ANALYSIS: Kernel, Soft-margin penalty parameter, Strategy
        svm = new SVM(new GaussianKernel(SarangiSVM.SIGMA), 2.0d, Math.max(trainingSet.labelIndices)+1, SVM.Multiclass.ONE_VS_ONE);

        svm.setTolerance(Config.SVM_TOLERANCE);

        for (int i=0; i<Config.SVM_EPOCH; i++) {
            svm.learn(trainingSet.dataset,trainingSet.labelIndices);
        }
        svm.finish();
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

        return svm.predict(dataset);
    }

    /**
     * Store the svm object.
     *
     * @param filename The file where the object is to be stored.
     *
     */

    public void store(String filename) throws IOException, XStreamException {
            FileWriter fileWriter = new FileWriter(filename);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            XStream xstream = new XStream(new StaxDriver());
            String xml = xstream.toXML(svm);

            bufferedWriter.write(xml);

            bufferedWriter.close();

    }

    /**
     * Load the svm object.
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
            this.svm = (SVM)xstream.fromXML(xml);

            bufferedReader.close();

    }

}
