package com.sarangi.audioTools;

import java.util.*;
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

        public static double[][] assign1Dto2DArray(double[][] twoDArray, double[] data, int index){
/*
                double[] data = new double[twoDArray[0].length];

                if(twoDArray[0].length < oneDArray.length)
                        System.arraycopy(oneDArray,0,data,0,twoDArray[0].length);
                else
                        System.arraycopy(oneDArray,0,data,0,oneDArray.length);
*/
                
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

        //first mean and then standard deviation
        public static double[] getAvgSD(double[][] data){

                double[][] transposeData = transposeMatrix(data);


                int length = transposeData.length;
                double[] feature = new double[length*2];

                for(int i=0; i<length; ++i){
                        feature[i] = getAverage(transposeData[i]);
                        feature[i + length] = getStandardDeviation(transposeData[i]);
                }

                return feature;
        }

        public static int ensureIsPowerOfN(int x, int n){

                double logValue = logBaseN((double)x, (double)n);
                int logInt = (int)logValue;
                int validSize = pow(n,logInt);

                if(validSize != x)
                        validSize = pow(n,logInt + 1);

                return validSize;
        }

        public static double logBaseN(double x, double n){
                return (Math.log10(x) / Math.log10(n));
        }

        public static int pow(int a, int b){
                int result = a;
                for(int i = 1; i < b; ++i)
                        result *= a;
                return result;
        }

        public static double[] mergeArrays(double[] ...datas){

                int length = 0;

                for(double[] data : datas){
                        length += data.length;
                }

                double[] output = new double[length];

                int count = 0;

                for(double[] data : datas){
                        int size = data.length;
                        System.arraycopy(data,0,output,count,size);
                        count += size;
                }

                return output;
        }

        public static int getIndexOfLargest(double[] values){

                int maxIndex = 0;

                for(int i=0; i<values.length; ++i)
                        if(values[i] > values[maxIndex])
                                maxIndex = i;

                return maxIndex;
        }


        public static double[] getPitchAnalysis(double[][] features){

                int howLong = 10;

                double[] output = new double[howLong];

                Map<Integer,Integer> pitchCount = new HashMap<Integer,Integer>();

                for(double[] feature : features){

                        int pitch =  (int)feature[0];

                        if(pitch != -1){

                                if(pitchCount.containsKey(pitch)){
                                        int value = pitchCount.get(pitch);
                                        pitchCount.put(pitch,++value);
                                }else{
                                        pitchCount.put(pitch,1);
                                }
                        }
                }

                Map<Integer,Integer> sortedMap = sortByComparatorPitch(pitchCount);

                ArrayList<Integer> keys = new ArrayList<Integer>(sortedMap.keySet());

                int featureSize = features.length;

                for( int count=0 , i=keys.size()-1 ; i>=0 && count<howLong ; --i , ++count ){

                        output[count] = keys.get(i);
                        output[++count] = sortedMap.get(keys.get(i))/featureSize;
                }

                return output;

        }
        public static double[] getRhythmAnalysis(double[][] feature){

                int howLong = 12;
                int featureSize = feature.length;

                double[] strongestBeat = new double[howLong];

                Map<Double,Integer> bmpCount = new HashMap<Double,Integer>();
                Map<Double,Double> bmpEnergy = new HashMap<Double,Double>();

                for(double[] single : feature){

                        double beatPerMinute = single[0];
                        double strongest = single[1];

                        if(bmpCount.containsKey(beatPerMinute)){
                                int value = bmpCount.get(beatPerMinute);
                                bmpCount.put(beatPerMinute, ++ value);

                                double energy = bmpEnergy.get(beatPerMinute);
                                bmpEnergy.put(beatPerMinute, energy + strongest);
                        }else{

                                bmpCount.put(beatPerMinute,1);
                                bmpEnergy.put(beatPerMinute,strongest);
                        }
                }

                Map<Double,Integer> sortedMap = sortByComparatorRhythm(bmpCount);

                ArrayList<Double> keys = new ArrayList<Double>(sortedMap.keySet());

                for(int count = 0,i=keys.size()-1;i>=0 && count<howLong; --i,++count){

                        strongestBeat[count] = keys.get(i);
                        strongestBeat[++count] = sortedMap.get(keys.get(i))/featureSize;
                        strongestBeat[++count] = bmpEnergy.get(keys.get(i))/sortedMap.get(keys.get(i));
                }

                return strongestBeat;
 
        }
        
        private static Map<Double, Integer> sortByComparatorRhythm(Map<Double, Integer> unsortMap) {

                // Convert Map to List
                List<Map.Entry<Double, Integer>> list = 
                        new LinkedList<Map.Entry<Double, Integer>>(unsortMap.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<Double, Integer>>() {
                        public int compare(Map.Entry<Double, Integer> o1,
                                        Map.Entry<Double, Integer> o2) {
                                return (o1.getValue()).compareTo(o2.getValue());
                        }
                });

                // Convert sorted map back to a Map
                Map<Double, Integer> sortedMap = new LinkedHashMap<Double, Integer>();
                for (Iterator<Map.Entry<Double, Integer>> it = list.iterator(); it.hasNext();) {
                        Map.Entry<Double, Integer> entry = it.next();
                        sortedMap.put(entry.getKey(), entry.getValue());
                }
                return sortedMap;
        }
        
        private static Map<Integer, Integer> sortByComparatorPitch(Map<Integer, Integer> unsortMap) {

                // Convert Map to List
                List<Map.Entry<Integer, Integer>> list = 
                        new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
                        public int compare(Map.Entry<Integer, Integer> o1,
                                        Map.Entry<Integer, Integer> o2) {
                                return (o1.getValue()).compareTo(o2.getValue());
                        }
                });

                // Convert sorted map back to a Map
                Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
                for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) {
                        Map.Entry<Integer, Integer> entry = it.next();
                        sortedMap.put(entry.getKey(), entry.getValue());
                }
                return sortedMap;
        }
        private static void printMap(Map<Double,Integer> map){

                ArrayList<Double> keys = new ArrayList<Double>(map.keySet());

                for(int i=keys.size()-1;i>=0; --i)
                        System.out.println(keys.get(i) + " " + map.get(keys.get(i)));

                /*for(Map.Entry<Double,Integer> entry : map.entrySet()){
                  System.out.println(entry.getKey() + "  " +entry.getValue());
                  }*/
        }

}
