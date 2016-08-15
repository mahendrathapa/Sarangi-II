/**
 * @(#) LabelNotFoundException.java 2.0     August 15, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */

package com.sarangi.learningmodel;

/**
 * Thrown when the label string given is not in labels array.
 *
 * @author Bijay Gurung
 *
 */

public class LabelNotFoundException extends Exception {

    public LabelNotFoundException() {
    }

    public LabelNotFoundException(String msg) {
        super(msg);
    }

    public LabelNotFoundException(Throwable cause) {
        super(cause);
    }

    public LabelNotFoundException(String msg, Throwable cause) {
        super(msg,cause);
    }

}
