package com.sarangi.learningmodel.svm; 

import java.io.*;

import com.sarangi.structures.*;
import com.sarangi.json.*;
import com.sarangi.learningmodel.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

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

        public static double SIGMA = 60.0d;


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
        public SarangiSVM(List<Song>trainingSongs, String[] labels, FeatureType featureType) {

                super(trainingSongs, labels, featureType);

        }

        /**
         * Train the model using SVM
         *
         * @param trainingSongs The songs to be used for training.
         *
         */

        @Override
        public void train(List<Song> trainingSongs) {
                this.trainingSet = DatasetUtil.getSongwiseDataset(trainingSongs, labels, featureType);

                svm = new SVM(new GaussianKernel(SarangiSVM.SIGMA), 2.0d, Math.max(trainingSet.labelIndices)+1, SVM.Multiclass.ONE_VS_ONE);
                svm.learn(trainingSet.dataset,trainingSet.labelIndices);
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
                List<Song> oneSong = new ArrayList<Song>();
                oneSong.add(song);

                // TODO Get a better solution than this Hack
                LearningDataset songDataset = DatasetUtil.getSongwiseDataset(oneSong,this.labels,this.featureType);

                return svm.predict(songDataset.dataset[0]);
        }

        public void store(String filename) {
                try{
                        FileWriter fileWriter = new FileWriter(filename,true);

                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        /*
                        final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(MercerKernel.class, new MercerKernelInstanceCreator());
                        final Gson gson = gsonBuilder.create();

                        gson.toJson(svm,bufferedWriter);
                        */

                        XStream xstream = new XStream(new StaxDriver());
                        String xml = xstream.toXML(svm);

                        bufferedWriter.write(xml);

                        bufferedWriter.close();

                }catch(Exception ex) {
                        ex.printStackTrace();
                }
        }

        public void load(String filename) {
                try{

                        File file = new File(filename);

                        if(file.length() == 0)
                                return;

                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String xml = bufferedReader.readLine();

                        /*
                        final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(MercerKernel.class, new MercerKernelInstanceCreator());
                        final Gson gson = gsonBuilder.create();

                        this.svm = gson.fromJson(jsonResponse,SVM.class);
                        */

                        XStream xstream = new XStream(new StaxDriver());
                        this.svm = (SVM)xstream.fromXML(xml);

                        bufferedReader.close();

                } catch(Exception ex){
                        ex.printStackTrace();
                }

        }

}
