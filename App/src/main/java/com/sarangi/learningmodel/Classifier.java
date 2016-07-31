/**
 * @(#) Classifier.java 2.0     July 29, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

import com.sarangi.structures.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Interface for all Sarangi Classifiers.
 *
 *
 * @author Bijay Gurung
 */

public interface Classifier {

        public int predict(Song song);
        public Result test(List<Song> testSongs);

}
