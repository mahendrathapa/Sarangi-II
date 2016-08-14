/**
 * @(#) CommandExtract.java 2.0     August 11, 2016
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


@Parameters(separators = "=", commandDescription = "Extract features of songs in the specified folder")
public class CommandExtract {

    @Parameter(names = {"-F","--folder"},description = "The folder to be used")
        public String folder;

    @Parameter(names = {"-f","--file"},description = "The file where the features are to be stored")
        public String file;

    @Parameter(names = "--help", help = true)
        public boolean help;
}

