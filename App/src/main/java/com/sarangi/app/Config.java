/*
 * @(#) Config.java   2.0     August 20, 2016
 *
 * Bijay Gurung
 *
 * Institute of Engineering
 */

package com.sarangi.app;

import com.sarangi.structures.FeatureType;


/**
 * A class for storing the configurations of the application. 
 *
 * @author  Bijay Gurung
 */

public class Config {

    /**
     * The features to be used.
     *
     */
    public static FeatureType[] features = { 
        //FeatureType.SARANGI_SPECTRALCENTROID,
        FeatureType.SARANGI_MELFREQ,
        //FeatureType.SARANGI_ZEROCROSSING, 
        //FeatureType.SARANGI_PITCH,
        FeatureType.SARANGI_COMPACTNESS, 
        FeatureType.SARANGI_RHYTHM,     
        //FeatureType.SARANGI_RMS,
        //FeatureType.SARANGI_SPECTRALFLUX,
        //FeatureType.SARANGI_SPECTRALROLLOFFPOINT,
        //FeatureType.SARANGI_SPECTRALVARIABILITY,
    };

    /**
     * The size of each dataset.
     * It is the sum of length of each feature used.
     * Default is 60.
     * But should be calculated at the start of application using 
     * calculateDatasetSize()
     * 
     */
    public static int DATASET_SIZE = 60;

    /*NEURAL NETWORK PARAMETERS**********************************/

    public static int ANN_EPOCH = 1000;

    public static double ANN_LEARNING_RATE = 0.1;
    public static double ANN_MOMENTUM = 0.0;
    public static double ANN_WEIGHT_DECAY = 0.0;

    public static int ANN_HIDDEN_NODES = 30;

    public static double[] ANN_MEANS = { 509.56794169737924, 5.473508967119809, 53.03337129720517, 6.489265810181536, 
                                         3.0477862546823107, -0.6163738721174642, 4.227113947570855, -2.52671794132533, 
                                         3.4596604118696233, -3.1491751029812103, 3.5573884698068086, -4.128995992449154, 
                                         4.3849918455673205, -4.332065781832201, 4.274197078077159, -4.422497346340211, 
                                         4.267284841806094, -4.558017304631748, 4.036258688049161, -4.064609378893623, 
                                         3.8030752242245183, -3.732621403790894, 3.340533493211156, -3.2220716411355146, 
                                         2.8198761858038193, -2.472096631764542, 2.196837002872341, -1.9143501859896237, 
                                         1.5061702644761865, -1.1459755258941848, 0.7820663228857757, -0.37669430318342084, 
                                         22.244299015845463, 5.914746595283442, 4.149275779142783, 2.897236149402829, 
                                         2.7252533717114793, 2.3702543450967424, 2.0404034467949326, 1.9256924126153998, 
                                         1.8706002943280013, 1.7987159316192818, 1.7368324646731286, 1.7126340729087175, 
                                         1.6808088734591748, 1.6668974996332067, 1.6158394067206387, 1.5663768211045184, 
                                         1.5263870642549247, 1.4809505461492205, 1.4164349621943517, 1.3400192898583452, 
                                         1.279669283018391, 1.2167565939942502, 1.1563492735022232, 1.0978378820181063, 
                                         1.031013940487188, 0.9369672189670918, 0.8987236140244588, 0.8522452402242505, 
                                         0.7965151989393101, 0.8088013789904859, 103.52271350617333, 57.91223830617878 };

    public static double[] ANN_STANDARD_DEVIATIONS = { 4.868049665062224, 10.713590970648928, 35.33497790848985, 8.857371824036482, 
                                                       4.5347592136110695, 2.5614141508911525, 2.018542922889185, 1.7175503848158786, 
                                                       1.489080565717414, 1.3095235072962723, 1.230599187073634, 1.4369086588396378, 
                                                       1.2623936452251054, 1.4474685586681715, 1.3629262877864998, 1.432511477655997, 
                                                       1.388517655137448, 1.4063884963993898, 1.3034209432847919, 1.2657293434740653, 
                                                       1.1398356311553766, 1.056087766403098, 1.0486099438449017, 0.8914697881951418, 
                                                       0.7380148745924017, 0.730042061472604, 0.6048481958472299, 0.4982111847938033, 
                                                       0.44299471373766286, 0.3590074787283639, 0.28610615367976366, 0.23969092951361365, 
                                                       24.25914384014553, 1.614494091913825, 1.0998841001862456, 0.7288995386725197,
                                                       0.7587657011110204, 0.6784680474656124, 0.4571830735546759, 0.391128998087694, 
                                                       0.3946231308015115, 0.38552941323375267, 0.3734839652745406, 0.3967576313867902, 
                                                       0.3957436127188637, 0.44697286986985296, 0.4244780116167975, 0.42475638630478924, 
                                                       0.4057935710302743, 0.432597430195301, 0.3776031245919399, 0.3476243655446226, 
                                                       0.3393983077176813, 0.3143829799662227, 0.3000849640364579, 0.2917746437365922, 
                                                       0.2724798995317776, 0.22596867192073777, 0.22091323524809467, 0.21779571212451346, 
                                                       0.1770587661359036, 0.24611206100642163, 41.43474662746798, 31.61612586128391 };

    /*SVM PARAMETERS**********************************/

    public static int SVM_EPOCH = 10;

    public static double SVM_TOLERANCE = 1E-3;

    /**
     * Calculate the Dataset Size.
     * Adds the length of each feature.
     * Should be called at start-up in application entry point.
     *
     */
    public static void calculateDatasetSize() {

        int datasetSize = 0;

        for (FeatureType feature: features) {
            datasetSize += feature.getLength();
        }

        DATASET_SIZE = datasetSize;
    }


}
