/**
 * @(#) DatasetUtil.java 2.0     July 29, 2016
 *
 * Bijay Gurung
 *
 * Insitute of Engineering
 */
package com.sarangi.structures;

/**
 * Enumerations for different features used for classification.
 *
 * @author Bijay Gurung
 * 
 */

public enum FeatureType {

    SARANGI_MELFREQ(60),
    SARANGI_PITCH(10),
    SARANGI_COMPACTNESS(2),
    SARANGI_RHYTHM(12),
    SARANGI_RMS(2),
    SARANGI_SPECTRALCENTROID(2),
    SARANGI_SPECTRALFLUX(2),
    SARANGI_SPECTRALROLLOFFPOINT(2),
    SARANGI_SPECTRALVARIABILITY(2),
    SARANGI_ZEROCROSSING(2);

    private int length;

    FeatureType(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

}
