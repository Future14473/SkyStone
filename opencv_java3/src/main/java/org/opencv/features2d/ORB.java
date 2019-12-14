//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

// C++: class ORB
//javadoc: ORB

public class ORB extends Feature2D {

    public static final int
            kBytes = 32,
            HARRIS_SCORE = 0,
            FAST_SCORE = 1;

    protected ORB(long addr) { super(addr); }

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType, patchSize, fastThreshold)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType, int patchSize, int fastThreshold)
    {
        
        ORB retVal = ORB.__fromPtr__(create_0(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType, patchSize, fastThreshold));
        
        return retVal;
    }


    //
    // C++: static Ptr_ORB cv::ORB::create(int nfeatures = 500, float scaleFactor = 1.2f, int nlevels = 8, int edgeThreshold = 31, int firstLevel = 0, int WTA_K = 2, int scoreType = ORB::HARRIS_SCORE, int patchSize = 31, int fastThreshold = 20)
    //

    // internal usage only
    public static ORB __fromPtr__(long addr) { return new ORB(addr); }

    // C++: static Ptr_ORB cv::ORB::create(int nfeatures = 500, float scaleFactor = 1.2f, int nlevels = 8, int edgeThreshold = 31, int firstLevel = 0, int WTA_K = 2, int scoreType = ORB::HARRIS_SCORE, int patchSize = 31, int fastThreshold = 20)
    private static native long create_0(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType, int patchSize, int fastThreshold);

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType, patchSize)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType, int patchSize)
    {
        
        ORB retVal = ORB.__fromPtr__(create_1(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType, patchSize));
        
        return retVal;
    }

    private static native long create_1(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType, int patchSize);

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType)
    {
        
        ORB retVal = ORB.__fromPtr__(create_2(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType));
        
        return retVal;
    }

    private static native long create_2(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType);

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K)
    {
        
        ORB retVal = ORB.__fromPtr__(create_3(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K));
        
        return retVal;
    }

    private static native long create_3(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K);

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel)
    {
        
        ORB retVal = ORB.__fromPtr__(create_4(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel));
        
        return retVal;
    }

    private static native long create_4(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel);


    //
    // C++:  String cv::ORB::getDefaultName()
    //

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels, edgeThreshold)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold)
    {
        
        ORB retVal = ORB.__fromPtr__(create_5(nfeatures, scaleFactor, nlevels, edgeThreshold));
        
        return retVal;
    }


    //
    // C++:  double cv::ORB::getScaleFactor()
    //

    private static native long create_5(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold);


    //
    // C++:  int cv::ORB::getEdgeThreshold()
    //

    //javadoc: ORB::create(nfeatures, scaleFactor, nlevels)
    public static ORB create(int nfeatures, float scaleFactor, int nlevels)
    {
        
        ORB retVal = ORB.__fromPtr__(create_6(nfeatures, scaleFactor, nlevels));
        
        return retVal;
    }


    //
    // C++:  int cv::ORB::getFastThreshold()
    //

    private static native long create_6(int nfeatures, float scaleFactor, int nlevels);


    //
    // C++:  int cv::ORB::getFirstLevel()
    //

    //javadoc: ORB::create(nfeatures, scaleFactor)
    public static ORB create(int nfeatures, float scaleFactor)
    {
        
        ORB retVal = ORB.__fromPtr__(create_7(nfeatures, scaleFactor));
        
        return retVal;
    }


    //
    // C++:  int cv::ORB::getMaxFeatures()
    //

    private static native long create_7(int nfeatures, float scaleFactor);


    //
    // C++:  int cv::ORB::getNLevels()
    //

    //javadoc: ORB::create(nfeatures)
    public static ORB create(int nfeatures)
    {
        
        ORB retVal = ORB.__fromPtr__(create_8(nfeatures));
        
        return retVal;
    }


    //
    // C++:  int cv::ORB::getPatchSize()
    //

    private static native long create_8(int nfeatures);


    //
    // C++:  int cv::ORB::getScoreType()
    //

    //javadoc: ORB::create()
    public static ORB create()
    {
        
        ORB retVal = ORB.__fromPtr__(create_9());
        
        return retVal;
    }


    //
    // C++:  int cv::ORB::getWTA_K()
    //

    private static native long create_9();


    //
    // C++:  void cv::ORB::setEdgeThreshold(int edgeThreshold)
    //

    //javadoc: ORB::getDefaultName()
    public  String getDefaultName()
    {
        
        String retVal = getDefaultName_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::ORB::setFastThreshold(int fastThreshold)
    //

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }


    //
    // C++:  void cv::ORB::setFirstLevel(int firstLevel)
    //

    // native support for java finalize()
    private static native void delete(long nativeObj);


    //
    // C++:  void cv::ORB::setMaxFeatures(int maxFeatures)
    //

    // C++:  String cv::ORB::getDefaultName()
    private static native String getDefaultName_0(long nativeObj);


    //
    // C++:  void cv::ORB::setNLevels(int nlevels)
    //

    //javadoc: ORB::getScaleFactor()
    public  double getScaleFactor()
    {
        
        double retVal = getScaleFactor_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::ORB::setPatchSize(int patchSize)
    //

    //javadoc: ORB::setScaleFactor(scaleFactor)
    public  void setScaleFactor(double scaleFactor)
    {
        
        setScaleFactor_0(nativeObj, scaleFactor);
        
        return;
    }


    //
    // C++:  void cv::ORB::setScaleFactor(double scaleFactor)
    //

    // C++:  void cv::ORB::setScaleFactor(double scaleFactor)
    private static native void setScaleFactor_0(long nativeObj, double scaleFactor);


    //
    // C++:  void cv::ORB::setScoreType(int scoreType)
    //

    // C++:  double cv::ORB::getScaleFactor()
    private static native double getScaleFactor_0(long nativeObj);


    //
    // C++:  void cv::ORB::setWTA_K(int wta_k)
    //

    //javadoc: ORB::getEdgeThreshold()
    public  int getEdgeThreshold()
    {
        
        int retVal = getEdgeThreshold_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setEdgeThreshold(edgeThreshold)
    public  void setEdgeThreshold(int edgeThreshold)
    {
        
        setEdgeThreshold_0(nativeObj, edgeThreshold);
        
        return;
    }

    // C++:  void cv::ORB::setEdgeThreshold(int edgeThreshold)
    private static native void setEdgeThreshold_0(long nativeObj, int edgeThreshold);

    // C++:  int cv::ORB::getEdgeThreshold()
    private static native int getEdgeThreshold_0(long nativeObj);

    //javadoc: ORB::getFastThreshold()
    public  int getFastThreshold()
    {
        
        int retVal = getFastThreshold_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setFastThreshold(fastThreshold)
    public  void setFastThreshold(int fastThreshold)
    {
        
        setFastThreshold_0(nativeObj, fastThreshold);
        
        return;
    }

    // C++:  void cv::ORB::setFastThreshold(int fastThreshold)
    private static native void setFastThreshold_0(long nativeObj, int fastThreshold);

    // C++:  int cv::ORB::getFastThreshold()
    private static native int getFastThreshold_0(long nativeObj);

    //javadoc: ORB::getFirstLevel()
    public  int getFirstLevel()
    {
        
        int retVal = getFirstLevel_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setFirstLevel(firstLevel)
    public  void setFirstLevel(int firstLevel)
    {
        
        setFirstLevel_0(nativeObj, firstLevel);
        
        return;
    }

    // C++:  void cv::ORB::setFirstLevel(int firstLevel)
    private static native void setFirstLevel_0(long nativeObj, int firstLevel);

    // C++:  int cv::ORB::getFirstLevel()
    private static native int getFirstLevel_0(long nativeObj);

    //javadoc: ORB::getMaxFeatures()
    public  int getMaxFeatures()
    {
        
        int retVal = getMaxFeatures_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setMaxFeatures(maxFeatures)
    public  void setMaxFeatures(int maxFeatures)
    {
        
        setMaxFeatures_0(nativeObj, maxFeatures);
        
        return;
    }

    // C++:  void cv::ORB::setMaxFeatures(int maxFeatures)
    private static native void setMaxFeatures_0(long nativeObj, int maxFeatures);

    // C++:  int cv::ORB::getMaxFeatures()
    private static native int getMaxFeatures_0(long nativeObj);

    //javadoc: ORB::getNLevels()
    public  int getNLevels()
    {
        
        int retVal = getNLevels_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setNLevels(nlevels)
    public  void setNLevels(int nlevels)
    {
        
        setNLevels_0(nativeObj, nlevels);
        
        return;
    }

    // C++:  void cv::ORB::setNLevels(int nlevels)
    private static native void setNLevels_0(long nativeObj, int nlevels);

    // C++:  int cv::ORB::getNLevels()
    private static native int getNLevels_0(long nativeObj);

    //javadoc: ORB::getPatchSize()
    public  int getPatchSize()
    {
        
        int retVal = getPatchSize_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setPatchSize(patchSize)
    public  void setPatchSize(int patchSize)
    {
        
        setPatchSize_0(nativeObj, patchSize);
        
        return;
    }

    // C++:  void cv::ORB::setPatchSize(int patchSize)
    private static native void setPatchSize_0(long nativeObj, int patchSize);

    // C++:  int cv::ORB::getPatchSize()
    private static native int getPatchSize_0(long nativeObj);

    //javadoc: ORB::getScoreType()
    public  int getScoreType()
    {
        
        int retVal = getScoreType_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setScoreType(scoreType)
    public  void setScoreType(int scoreType)
    {
        
        setScoreType_0(nativeObj, scoreType);
        
        return;
    }

    // C++:  void cv::ORB::setScoreType(int scoreType)
    private static native void setScoreType_0(long nativeObj, int scoreType);

    // C++:  int cv::ORB::getScoreType()
    private static native int getScoreType_0(long nativeObj);

    //javadoc: ORB::getWTA_K()
    public  int getWTA_K()
    {
        
        int retVal = getWTA_K_0(nativeObj);
        
        return retVal;
    }

    //javadoc: ORB::setWTA_K(wta_k)
    public  void setWTA_K(int wta_k)
    {
        
        setWTA_K_0(nativeObj, wta_k);
        
        return;
    }

    // C++:  void cv::ORB::setWTA_K(int wta_k)
    private static native void setWTA_K_0(long nativeObj, int wta_k);

    // C++:  int cv::ORB::getWTA_K()
    private static native int getWTA_K_0(long nativeObj);

}
