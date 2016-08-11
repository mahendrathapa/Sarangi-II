/**
 * @(#) CommandClassify.java 2.0     August 11, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.app.commands;

import com.beust.jcommander.Parameter; 
import com.beust.jcommander.Parameters; 

/**
 * Command to classify the given file/files.
 *
 *
 * @author Bijay Gurung
 */


@Parameters(separators = "=", commandDescription = "Classify the given music file/files.")
public class CommandClassify {
         
        @Parameter(names = {"-f","--file"},description = "Music File to be classified")
                public String file;

        @Parameter(names = "--help", help = true)
                public boolean help;
}

