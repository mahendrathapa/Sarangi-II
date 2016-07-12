package com.sarangi.ann;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import java.lang.reflect.Type;

import com.sarangi.kmeans.*;

import com.sarangi.structures.Song;

public class Kmeans {

        List<SongCluster> clusters;
        List<SarangiSong> allSongs;
        int k = 5;
        final int MAX_ITERATION = 1000;
        SarangiSong maxsong;
        SarangiSong minsong;
        SarangiSong pop,jazz,classic,rock,hiphop;

        public Kmeans() {
                this.k = 1;
                clusters = new ArrayList<SongCluster>();
                allSongs = new ArrayList<SarangiSong>();
                maxsong = new SarangiSong();
                minsong = new SarangiSong();
                pop = new SarangiSong();
                classic = new SarangiSong();
                jazz = new SarangiSong();
                rock = new SarangiSong();
                hiphop = new SarangiSong();
                maxsong.setIntensity(0.0d);
                minsong.setIntensity(2.0d);
                for(int i = 0; i < 16; i++){
                         maxsong.setMFCC(i,-400.0d);
                         minsong.setMFCC(i,400.0d);
                 }
        }

        public Kmeans(int k) {
                this.k = k;
                clusters = new ArrayList<SongCluster>();
                allSongs = new ArrayList<SarangiSong>();
                maxsong = new SarangiSong();
                minsong = new SarangiSong();
                pop = new SarangiSong();
                classic = new SarangiSong();
                jazz = new SarangiSong();
                rock = new SarangiSong();
                hiphop = new SarangiSong();
                maxsong.setIntensity(0.1d);
                minsong.setIntensity(2.0d);

                for(int i = 0; i < 16; i++){
                         maxsong.setMFCC(i,-400.0d);
                         minsong.setMFCC(i,400.0d);
                 }
        }

        public void readAllSongs(String filename) 
                throws FileNotFoundException, IOException {

                 Gson gson = new Gson();
                 JsonReader json = new JsonReader(new FileReader(filename));
 
                 Type listType = new TypeToken<List<Song>>(){}.getType();
                 List<Song> songs = gson.fromJson(json,listType);

                 double songintensity = 0.0d;

                 for (Song item: songs) {

                       //  item.normalizeFeature();//needs the portion weighting of MFCC
                       
                         //a genre specific initialization
                         if(item.getSongName().equals("Pop-2.mp3"))
                                 pop = new SarangiSong(item);
                         if(item.getSongName().equals("Classic-2.mp3"))
                                 classic = new SarangiSong(item);
                         if(item.getSongName().equals("Rock-2.mp3"))
                                 rock = new SarangiSong(item);
                         if(item.getSongName().equals("Jazz-2.mp3"))
                                 jazz = new SarangiSong(item);
                         if(item.getSongName().equals("Hiphop-2.mp3"))
                                 hiphop = new SarangiSong(item);

                         //////////////////////////
                         
                         allSongs.add(new SarangiSong(item));
                         songintensity = item.getIntensity();
                         if(maxsong.getIntensity() < songintensity)
                                 maxsong.setIntensity(songintensity);
                         if(minsong.getIntensity() > songintensity)
                                 minsong.setIntensity(songintensity);

                         for(int i = 0; i < 16; i++){
                                 if(maxsong.getMFCC().get(i) < item.getMFCC()[i])
                                 {
                                         maxsong.setMFCC(i,item.getMFCC()[i]);
                                 }
                                 if(minsong.getMFCC().get(i) > item.getMFCC()[i])
                                 {
                                         minsong.setMFCC(i,item.getMFCC()[i]);
                                 }
                         }
                 }
                 //todo
                 //setsomeoffset to highest value in MFCC and intensity
                 
/*
                 for (Song item:songs){
                         item.setIntensity((item.getIntensity()-minsong.getIntensity())/(maxsong.getIntensity()-minsong.getIntensity()));
                                 for(int i = 0; i < 16; i++){
                                         item.setMFCC(i,(item.getMFCC()[i]-minsong.getMFCC().get(i))/(maxsong.getMFCC().get(i)-minsong.getMFCC().get(i)));
                                 }
                                 allSongs.add(new SarangiSong(item));
                 }
                 */
        }

        /*
        //song based initialization
       public void initialize(){
                for(int i = 0; i<k;i++){
                        SongCluster newcluster = new SongCluster(i);
                        switch(i){
                                case 0:
                                        newcluster.setCentroid(classic);
                                        break;
                                case 1:
                                        newcluster.setCentroid(rock);
                                        break;
                                case 2:
                                        newcluster.setCentroid(jazz);
                                        break;
                                case 3:
                                        newcluster.setCentroid(pop);
                                        break;
                                case 4:
                                        newcluster.setCentroid(hiphop);
                                        break;

                        }
                                        clusters.add(newcluster);
                }
        }
        
       */ 

/*
//partition initialization
        public void initialize() {
                SarangiSong maxminsong_diff = new SarangiSong(maxsong.subtract(minsong));
                maxminsong_diff = maxminsong_diff.divide(5);

                SarangiSong uppertemp = new SarangiSong();
                uppertemp = minsong;

                SarangiSong lowertemp = new SarangiSong();
                lowertemp = minsong;

                SarangiSong centroidcandidate[] = new SarangiSong[5];

                uppertemp = maxminsong_diff.divide(2);

                lowertemp = lowertemp.add(uppertemp); 

                Integer intensityindex[] = {0,1,2,3,4};
                Integer mfccindex[] = {0,1,2,3,4};

                //shuffling for random unscrambled index
                Collections.shuffle(Arrays.asList(intensityindex));
                Collections.shuffle(Arrays.asList(mfccindex));

                //creating all centroid candidate
                //initialization not correct
                for (int i = 0; i<k; i++){
                        centroidcandidate[i] = new SarangiSong();
                        centroidcandidate[i] = lowertemp;
                        lowertemp = lowertemp.add(maxminsong_diff);
                }

                // Use the first k numbers from the randomly shuffled list as index 
                for (int i=0; i<k; i++) {
                        // i is the id for the cluster
                        SongCluster newCluster = new SongCluster(i);

                       //centroid assigned in unscrambled way 
                        SarangiSong centroid = new SarangiSong();
                        centroid.setMFCC(centroidcandidate[mfccindex[i]].getMFCC());
                        centroid.setIntensity(centroidcandidate[intensityindex[i]].getIntensity());


                        // Set the song with the index as the centroid
                        newCluster.setCentroid(centroidcandidate[i]);

                        //lowertemp = lowertemp.add(maxminsong_diff);

                        clusters.add(newCluster);
                }
        }

   */
//random initialization
        public void initialize() {

                ArrayList<Integer> list = new ArrayList<Integer>();

                int size = allSongs.size();
                for (int i=0; i<size; i++) {
                        list.add(new Integer(i));
                }

                long seed = System.nanoTime();
                Collections.shuffle(list, new Random(seed));

                // Use the first k numbers from the randomly shuffled list as index 
                for (int i=0; i<k; i++) {
                        int index = list.get(i);

                        // i is the id for the cluster
                        SongCluster newCluster = new SongCluster(i);

                        // Set the song with the index as the centroid
                        newCluster.setCentroid(allSongs.get(index));
                        clusters.add(newCluster);
                }
        }


        public void showRandomDistances() {
                Random rand = new Random();

                for (int m=0; m<5; m++) {
                    int i = rand.nextInt(allSongs.size());
                    int j = rand.nextInt(allSongs.size());

                    System.out.println("Distance:"+allSongs.get(i).getName()+" to "+allSongs.get(j).getName()+" => "
                                    + SarangiSong.distance(allSongs.get(i),allSongs.get(j)));
                }


        }

        public List<SongCluster> run() {

                int numOfSongs = allSongs.size();
                int iteration = 1;

                while (true) {

                        for (int j=0; j<k; j++) {
                                clusters.get(j).clearCluster();
                        }

                        // For each song
                        for (int i=0; i<numOfSongs; i++) {

                                SarangiSong song = allSongs.get(i);

                                int closestClusterIndex = -1;
                                /*
                                double minDistance = clusters.get(0)
                                        .distanceToCentroid(song);
                                        */
                                double minDistance = 1000000000.0d;

                                for (int j=0; j<k; j++) {

                                        double dist = clusters.get(j)
                                                .distanceToCentroid(song);

                                        /*
                                        System.out.println("Song num: "+i);
                                        System.out.println("Iteration Num: "+iteration);
                                        System.out.println("Cluster num"+j);
                                        System.out.println("Distance"+dist);
                                        System.out.println("Min Distance"+minDistance);
                                        */

                                        if (dist<minDistance) {
                                                minDistance = dist;
                                                closestClusterIndex = j;
                                        }

                                }

                                // Assign to closest cluster
                                clusters.get(closestClusterIndex).addSong(song);
                        }

                        boolean sameCentroid = true;

                        for (int j=0; j<k; j++) {
                                boolean sameCentroidTemp = clusters.get(j).computeCentroid();
                                sameCentroid = sameCentroid && sameCentroidTemp;
                        }

                        iteration++;

                        if (iteration > MAX_ITERATION) {
                                System.out.println("Iteration limit reached... Exiting...");
                                break;
                        }

                        if (sameCentroid) {
                                break;
                        }

                        /*
                        if ((sameCentroid) || (iteration > MAX_ITERATION)) {
                                break;
                        }
                        */

                }

                System.out.println("Iterations: "+iteration);

                return clusters;
        }


}
