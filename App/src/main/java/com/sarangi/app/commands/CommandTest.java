/**
 * @(#) CommandTest.java 2.0     August 11, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.app.commands;

import com.beust.jcommander.Parameter; 
import com.beust.jcommander.Parameters; 

/**
 * Command to test the classifier.
 *
 *
 * @author Bijay Gurung
 */


@Parameters(separators = "=", commandDescription = "Test the classifier")
public class CommandTest {
         
        @Parameter(names = {"-k","--kfold"},description = "Run K-fold validation on given file")
                public String kfoldFile;

        @Parameter(names = "--help", help = true)
                public boolean help;
}

