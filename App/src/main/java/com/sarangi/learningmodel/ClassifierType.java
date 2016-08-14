/**
 * @(#) ClassifierType.java 2.0     August 13, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */
package com.sarangi.learningmodel;

/**
 * Enumerations for different features used for classification.
 *
 * @author Bijay Gurung
 * 
 */

public enum ClassifierType {

    SARANGI_SVM("SVM"),
    SARANGI_ANN("ANN");

    private String text;

    ClassifierType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static ClassifierType fromString(String text) throws IllegalArgumentException {
        if (text != null) {
            for (ClassifierType classifierType : ClassifierType.values()) {
                if (text.equalsIgnoreCase(classifierType.text)) {
                    return classifierType;
                }
            }
        }

        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
