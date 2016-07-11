package com.sarangi.ann;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import com.sarangi.ann.*;

import com.sarangi.structures.Song;

public class Analyzer {

        private List<SongCluster> clusters;
        private List<String> labels;
        private List<List<Integer>> allCombinations;
        private double accuracy;

        public Analyzer(List<SongCluster> clusters, List<String> labels) {

                int numOfClusters = clusters.size();
                this.clusters = new ArrayList<SongCluster>();
                for (int i=0; i<numOfClusters; i++) {
                        this.clusters.add(clusters.get(i));
                }

                this.labels = new ArrayList<String>();

                int numOfLabels = labels.size();
                for (int i=0; i<numOfLabels; i++) {
                        this.labels.add(labels.get(i));
                }

                allCombinations = new  ArrayList<List<Integer>>();

                accuracy = 0.0d;
        }

        public void populateAllCombos() {
                List<Integer> indices = new ArrayList<Integer>();
                for (int i=0; i<labels.size(); i++) {
                        indices.add(i);
                }
                allCombinations = generatePerm(indices);
        }

        public List<List<Integer>> generatePerm(List<Integer> original) {

                if (original.size() == 0) { 
                        List<List<Integer>> result = new ArrayList<List<Integer>>();
                        result.add(new ArrayList<Integer>());
                        return result;
                }

                Integer firstElement = original.remove(0);
                List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
                List<List<Integer>> permutations = generatePerm(original);

                for (List<Integer> smallerPermutated : permutations) {

                  for (int index=0; index <= smallerPermutated.size(); index++) {
                        List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
                        temp.add(index, firstElement);
                        returnValue.add(temp);
                  }

                }

                return returnValue;
        }

        public void assignBestLabels() {

                populateAllCombos();

                int numOfClusters = clusters.size();
                int totalSongs = 0;

                for (SongCluster cluster: clusters) {
                        totalSongs += cluster.getNumOfSongs();
                }

                int maxSongsCorrectlyAssigned = 0;

                List<Integer> bestCombination = new ArrayList<Integer>();

                for (List<Integer> combination: allCombinations) {
                        
                    int songsCorrectlyAssigned = 0;

                    for (int i=0; i<numOfClusters; i++) {
                            songsCorrectlyAssigned += clusters.get(i).countSongsWithLabel(labels.
                                            get(combination.get(i)));
                    }

                    System.out.println(songsCorrectlyAssigned*1.0d/totalSongs);

                    if (songsCorrectlyAssigned > maxSongsCorrectlyAssigned) {
                            maxSongsCorrectlyAssigned = songsCorrectlyAssigned;
                            bestCombination.clear();
                            for (int i=0; i<combination.size(); i++) {
                                    bestCombination.add(combination.get(i));
                            }
                    }

                }


                for (int i=0; i<numOfClusters; i++) {
                        clusters.get(i).setLabel(labels.get(bestCombination.get(i)));
                }

                // Return the accuracy
                this.accuracy =  maxSongsCorrectlyAssigned * 1.0d/totalSongs;

        }

        public double getAccuracy() {
                return accuracy;
        }

        public void display() {
                System.out.println("Accuracy:"+accuracy);
                for (SongCluster cluster: clusters) {
                        cluster.display();
                }
        }

        public void displayConfusionMatrix() {
                        System.out.format("%20s","[Assigned\\Actual]");
                        for (SongCluster cluster: clusters) {
                                System.out.format("%15s",cluster.getLabel());
                        }
                        System.out.println();

                for (SongCluster cluster: clusters) {
                        System.out.format("%20s",cluster.getLabel());
                        for (SongCluster innerCluster: clusters) {
                                System.out.format("%15d",cluster.countSongsWithLabel(innerCluster.getLabel()));
                        }
                        System.out.println();
                }
        }


}
