
package com.sarangi.audioTools;

public class Statistics{

        public static float[] convertDoubleArrayToFloatArray(double[] data){

                int length = data.length;
                float[] output = new float[length];

                for(int i=0; i<length; ++i)
                        output[i] = (float)data[i];

                return output;
        }
        
        public static double[] convertFloatArrayToDoubleArray(float[] data){

                int length = data.length;
                double[] output = new double[length];

                for(int i=0; i<length; ++i)
                        output[i] = (double)data[i];

                return output;
        }

        public static double getAverage(double[] data){

                double sum = 0.0;

                int length = data.length;

                for(int i=0; i<length; ++i)
                        sum += data[i];

                return (sum / ((double)length));

        }

        public static double getStandardDeviation(double[] data){

                double average = getAverage(data);
                double sum = 0.0;
                int length = data.length;

                for(int i = 0;i < length; ++i){
                        double diff = data[i] - average;
                        sum += Math.pow(diff,2);
                }

                return Math.sqrt(sum / ((double) (length-1)));
        }

        public static double[][] assign1Dto2DArray(double[][] twoDArray, double[] oneDArray, int index){

                double[] data = new double[twoDArray[0].length];

                if(twoDArray[0].length < oneDArray.length)
                        System.arraycopy(oneDArray,0,data,0,twoDArray[0].length);
                else
                        System.arraycopy(oneDArray,0,data,0,oneDArray.length);

                
                int length = data.length;

                for(int i=0; i<length; ++i)
                        twoDArray[index][i] = data[i];

                return twoDArray;
        }

        public static double[][] transposeMatrix(double[][] data){

                int row = data.length;
                int column = data[0].length;

                double[][] output = new double[column][row];

                for(int i=0; i<row; ++i)
                        for(int j=0; j<column; ++j)
                                output[j][i] = data[i][j];

                return output;

        }

        //first mean and then standar deviation
        public static double[] getAverageAndStandardDeviation(double[][] data,int dimension){

                double[][] transposeData = transposeMatrix(data);

                double[] feature = new double[dimension];

                int length = transposeData.length;

                for(int i=0; i<length; ++i){
                        feature[i] = getAverage(transposeData[i]);
                        feature[i + length] = getStandardDeviation(transposeData[i]);
                }

                return feature;
        }
}
