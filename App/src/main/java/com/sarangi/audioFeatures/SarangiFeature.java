/*
 * @(#) Feature.java 2.0      August 8, 2016.
 *
 * Mahendra Thapa
 *
 * Institue of Engineering
 */

package com.sarangi.audioFeatures;

import com.sarangi.audioFeatures.*;
import java.util.*;
//song level
public abstract class SarangiFeature{

        private String name;
        private String description;
        protected int dimension;
        protected double[] feature;

        protected SarangiFeature(String name, String description,int dimension){
                
                this.name = name;
                this.description = description;
                this.dimension = dimension;
                feature = new double[dimension];
        }


        protected abstract double[] extractFeature(List<double[]> audioFrames, double samplingRate);

        public void setDimension(int dimension){
                this.dimension = dimension;
        }

        public double[] getFeature(){
                return feature;
        }

}
