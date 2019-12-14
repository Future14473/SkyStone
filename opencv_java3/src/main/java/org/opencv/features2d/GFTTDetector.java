//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

// C++: class GFTTDetector
//javadoc: GFTTDetector

public class GFTTDetector extends Feature2D {

    protected GFTTDetector(long addr) { super(addr); }

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize, useHarrisDetector, k)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, boolean useHarrisDetector, double k)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_0(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize, useHarrisDetector, k));
        
        return retVal;
    }

    //
    // C++: static Ptr_GFTTDetector cv::GFTTDetector::create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, bool useHarrisDetector = false, double k = 0.04)
    //

    // internal usage only
    public static GFTTDetector __fromPtr__(long addr) { return new GFTTDetector(addr); }

    // C++: static Ptr_GFTTDetector cv::GFTTDetector::create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, bool useHarrisDetector = false, double k = 0.04)
    private static native long create_0(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, boolean useHarrisDetector, double k);

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize, useHarrisDetector)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, boolean useHarrisDetector)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_1(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize, useHarrisDetector));
        
        return retVal;
    }


    //
    // C++: static Ptr_GFTTDetector cv::GFTTDetector::create(int maxCorners = 1000, double qualityLevel = 0.01, double minDistance = 1, int blockSize = 3, bool useHarrisDetector = false, double k = 0.04)
    //

    private static native long create_1(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, boolean useHarrisDetector);

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_2(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize));
        
        return retVal;
    }

    private static native long create_2(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize);

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance, blockSize, useHarrisDetector, k)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, boolean useHarrisDetector, double k)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_3(maxCorners, qualityLevel, minDistance, blockSize, useHarrisDetector, k));
        
        return retVal;
    }

    // C++: static Ptr_GFTTDetector cv::GFTTDetector::create(int maxCorners = 1000, double qualityLevel = 0.01, double minDistance = 1, int blockSize = 3, bool useHarrisDetector = false, double k = 0.04)
    private static native long create_3(int maxCorners, double qualityLevel, double minDistance, int blockSize, boolean useHarrisDetector, double k);

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance, blockSize, useHarrisDetector)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, boolean useHarrisDetector)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_4(maxCorners, qualityLevel, minDistance, blockSize, useHarrisDetector));
        
        return retVal;
    }

    private static native long create_4(int maxCorners, double qualityLevel, double minDistance, int blockSize, boolean useHarrisDetector);


    //
    // C++:  String cv::GFTTDetector::getDefaultName()
    //

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance, blockSize)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_5(maxCorners, qualityLevel, minDistance, blockSize));
        
        return retVal;
    }


    //
    // C++:  bool cv::GFTTDetector::getHarrisDetector()
    //

    private static native long create_5(int maxCorners, double qualityLevel, double minDistance, int blockSize);


    //
    // C++:  double cv::GFTTDetector::getK()
    //

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel, minDistance)
    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_6(maxCorners, qualityLevel, minDistance));
        
        return retVal;
    }


    //
    // C++:  double cv::GFTTDetector::getMinDistance()
    //

    private static native long create_6(int maxCorners, double qualityLevel, double minDistance);


    //
    // C++:  double cv::GFTTDetector::getQualityLevel()
    //

    //javadoc: GFTTDetector::create(maxCorners, qualityLevel)
    public static GFTTDetector create(int maxCorners, double qualityLevel)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_7(maxCorners, qualityLevel));
        
        return retVal;
    }


    //
    // C++:  int cv::GFTTDetector::getBlockSize()
    //

    private static native long create_7(int maxCorners, double qualityLevel);


    //
    // C++:  int cv::GFTTDetector::getMaxFeatures()
    //

    //javadoc: GFTTDetector::create(maxCorners)
    public static GFTTDetector create(int maxCorners)
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_8(maxCorners));
        
        return retVal;
    }


    //
    // C++:  void cv::GFTTDetector::setBlockSize(int blockSize)
    //

    private static native long create_8(int maxCorners);


    //
    // C++:  void cv::GFTTDetector::setHarrisDetector(bool val)
    //

    //javadoc: GFTTDetector::create()
    public static GFTTDetector create()
    {
        
        GFTTDetector retVal = GFTTDetector.__fromPtr__(create_9());
        
        return retVal;
    }


    //
    // C++:  void cv::GFTTDetector::setK(double k)
    //

    private static native long create_9();


    //
    // C++:  void cv::GFTTDetector::setMaxFeatures(int maxFeatures)
    //

    //javadoc: GFTTDetector::getDefaultName()
    public  String getDefaultName()
    {
        
        String retVal = getDefaultName_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::GFTTDetector::setMinDistance(double minDistance)
    //

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }


    //
    // C++:  void cv::GFTTDetector::setQualityLevel(double qlevel)
    //

    // native support for java finalize()
    private static native void delete(long nativeObj);

    // C++:  String cv::GFTTDetector::getDefaultName()
    private static native String getDefaultName_0(long nativeObj);

    //javadoc: GFTTDetector::getHarrisDetector()
    public  boolean getHarrisDetector()
    {
        
        boolean retVal = getHarrisDetector_0(nativeObj);
        
        return retVal;
    }

    //javadoc: GFTTDetector::setHarrisDetector(val)
    public  void setHarrisDetector(boolean val)
    {
        
        setHarrisDetector_0(nativeObj, val);
        
        return;
    }

    // C++:  void cv::GFTTDetector::setHarrisDetector(bool val)
    private static native void setHarrisDetector_0(long nativeObj, boolean val);

    // C++:  bool cv::GFTTDetector::getHarrisDetector()
    private static native boolean getHarrisDetector_0(long nativeObj);

    //javadoc: GFTTDetector::getK()
    public  double getK()
    {
        
        double retVal = getK_0(nativeObj);
        
        return retVal;
    }

    //javadoc: GFTTDetector::setK(k)
    public  void setK(double k)
    {
        
        setK_0(nativeObj, k);
        
        return;
    }

    // C++:  void cv::GFTTDetector::setK(double k)
    private static native void setK_0(long nativeObj, double k);

    // C++:  double cv::GFTTDetector::getK()
    private static native double getK_0(long nativeObj);

    //javadoc: GFTTDetector::getMinDistance()
    public  double getMinDistance()
    {
        
        double retVal = getMinDistance_0(nativeObj);
        
        return retVal;
    }

    //javadoc: GFTTDetector::setMinDistance(minDistance)
    public  void setMinDistance(double minDistance)
    {
        
        setMinDistance_0(nativeObj, minDistance);
        
        return;
    }

    // C++:  void cv::GFTTDetector::setMinDistance(double minDistance)
    private static native void setMinDistance_0(long nativeObj, double minDistance);

    // C++:  double cv::GFTTDetector::getMinDistance()
    private static native double getMinDistance_0(long nativeObj);

    //javadoc: GFTTDetector::getQualityLevel()
    public  double getQualityLevel()
    {
        
        double retVal = getQualityLevel_0(nativeObj);
        
        return retVal;
    }

    //javadoc: GFTTDetector::setQualityLevel(qlevel)
    public  void setQualityLevel(double qlevel)
    {
        
        setQualityLevel_0(nativeObj, qlevel);
        
        return;
    }

    // C++:  void cv::GFTTDetector::setQualityLevel(double qlevel)
    private static native void setQualityLevel_0(long nativeObj, double qlevel);

    // C++:  double cv::GFTTDetector::getQualityLevel()
    private static native double getQualityLevel_0(long nativeObj);

    //javadoc: GFTTDetector::getBlockSize()
    public  int getBlockSize()
    {
        
        int retVal = getBlockSize_0(nativeObj);
        
        return retVal;
    }

    //javadoc: GFTTDetector::setBlockSize(blockSize)
    public  void setBlockSize(int blockSize)
    {
        
        setBlockSize_0(nativeObj, blockSize);
        
        return;
    }

    // C++:  void cv::GFTTDetector::setBlockSize(int blockSize)
    private static native void setBlockSize_0(long nativeObj, int blockSize);

    // C++:  int cv::GFTTDetector::getBlockSize()
    private static native int getBlockSize_0(long nativeObj);

    //javadoc: GFTTDetector::getMaxFeatures()
    public  int getMaxFeatures()
    {
        
        int retVal = getMaxFeatures_0(nativeObj);
        
        return retVal;
    }

    //javadoc: GFTTDetector::setMaxFeatures(maxFeatures)
    public  void setMaxFeatures(int maxFeatures)
    {
        
        setMaxFeatures_0(nativeObj, maxFeatures);
        
        return;
    }

    // C++:  void cv::GFTTDetector::setMaxFeatures(int maxFeatures)
    private static native void setMaxFeatures_0(long nativeObj, int maxFeatures);

    // C++:  int cv::GFTTDetector::getMaxFeatures()
    private static native int getMaxFeatures_0(long nativeObj);

}
