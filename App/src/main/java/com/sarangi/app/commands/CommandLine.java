/**
 * @(#) CommandLine.java 2.0     August 11, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.app.commands;

import com.beust.jcommander.Parameter; 
import com.beust.jcommander.Parameters; 

/**
 * Command to extract the features.
 *
 *
 * @author Bijay Gurung
 */


public class CommandLine {
    /*
       @Parameter
       private List<String> parameters = new ArrayList<>();
       */

    @Parameter(names = "--help", help = true)
        public boolean help;
}

