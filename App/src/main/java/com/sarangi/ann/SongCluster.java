package com.mycompany.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class SongCluster {

        private List<SarangiSong> songCollection;
        private SarangiSong centroid;
        private int id;
        private String label;

        public SongCluster(int id) {
                this.id = id;
                this.songCollection = new ArrayList<SarangiSong>();
                this.centroid = new SarangiSong();
        }

        public void setLabel(String label) {
                this.label = label; 
        }

        public int countSongsWithLabel(String label) {
                int count = 0;
                int numOfSongs = songCollection.size();
                for (int i=0; i<numOfSongs; i++) {
                        if (songCollection.get(i).hasLabel(label))
                                count++;
                }

                return count;
        }

        public String getLabel() {
                return this.label;
        }

        public int getID() {
                return id;
        }

        public SarangiSong getCentroid() {
                return centroid;
        }

        public void setCentroid(SarangiSong s) {
                centroid = s;
        }

        public void addSong(final SarangiSong song) {
                songCollection.add(song);
        }

        public void clearCluster() {
                songCollection.clear();
        }

        public List<SarangiSong> getSongCollection() {
                List<SarangiSong> songCollectionClone = new ArrayList<SarangiSong>(songCollection);

                return songCollectionClone;
        }

        public int getNumOfSongs() {
                return songCollection.size();
        }

        public double distanceToCentroid(SarangiSong song) {
                return SarangiSong.distance(centroid,song);
        }

        /**
         *
         * Compute the centroid from the member points (songs)
         * 
         * @return A boolean which is true if the new centroid 
         *         is the same as the old one.
         *
         */

        public boolean computeCentroid() {

                int len = songCollection.size();

                // If len is 0, no change to centroid
                if (len == 0) return false;

                SarangiSong sum = new SarangiSong(songCollection.get(0));

                for (int i=1; i<len; i++) {
                        sum.addToThis(songCollection.get(i));
                }

                SarangiSong newCentroid = sum.divide((double)len);

                boolean sameCentroid = this.centroid.equals(newCentroid);

                this.centroid = newCentroid;

                return sameCentroid;

        }

        public void display() {

                System.out.println();

                System.out.println("ID:"+id);
                System.out.println("Label:"+label);
                System.out.println("Centroid:"+centroid.getName());
                for (SarangiSong song: songCollection) {
                    System.out.println(song.getName());
                }

                System.out.println();

        }

        public SarangiSong getSong(int i) {
                return songCollection.get(i);
        }

        public void shuffleCluster() {
                Collections.shuffle(songCollection);
        }

        public void displayCentroid() {
                System.out.println("ID:"+id);
                centroid.display();
        }

}
