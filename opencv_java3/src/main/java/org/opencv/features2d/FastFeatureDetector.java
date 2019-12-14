//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

// C++: class FastFeatureDetector
//javadoc: FastFeatureDetector

public class FastFeatureDetector extends Feature2D {

    public static final int
            TYPE_5_8 = 0,
            TYPE_7_12 = 1,
            TYPE_9_16 = 2,
            THRESHOLD = 10000,
            NONMAX_SUPPRESSION = 10001,
            FAST_N = 10002;

    protected FastFeatureDetector(long addr) { super(addr); }

    //javadoc: FastFeatureDetector::create(threshold, nonmaxSuppression, type)
    public static FastFeatureDetector create(int threshold, boolean nonmaxSuppression, int type)
    {
        
        FastFeatureDetector retVal = FastFeatureDetector.__fromPtr__(create_0(threshold, nonmaxSuppression, type));
        
        return retVal;
    }


    //
    // C++: static Ptr_FastFeatureDetector cv::FastFeatureDetector::create(int threshold = 10, bool nonmaxSuppression = true, int type = FastFeatureDetector::TYPE_9_16)
    //

    // internal usage only
    public static FastFeatureDetector __fromPtr__(long addr) { return new FastFeatureDetector(addr); }

    // C++: static Ptr_FastFeatureDetector cv::FastFeatureDetector::create(int threshold = 10, bool nonmaxSuppression = true, int type = FastFeatureDetector::TYPE_9_16)
    private static native long create_0(int threshold, boolean nonmaxSuppression, int type);

    //javadoc: FastFeatureDetector::create(threshold, nonmaxSuppression)
    public static FastFeatureDetector create(int threshold, boolean nonmaxSuppression)
    {
        
        FastFeatureDetector retVal = FastFeatureDetector.__fromPtr__(create_1(threshold, nonmaxSuppression));
        
        return retVal;
    }

    private static native long create_1(int threshold, boolean nonmaxSuppression);


    //
    // C++:  String cv::FastFeatureDetector::getDefaultName()
    //

    //javadoc: FastFeatureDetector::create(threshold)
    public static FastFeatureDetector create(int threshold)
    {
        
        FastFeatureDetector retVal = FastFeatureDetector.__fromPtr__(create_2(threshold));
        
        return retVal;
    }


    //
    // C++:  bool cv::FastFeatureDetector::getNonmaxSuppression()
    //

    private static native long create_2(int threshold);


    //
    // C++:  int cv::FastFeatureDetector::getThreshold()
    //

    //javadoc: FastFeatureDetector::create()
    public static FastFeatureDetector create()
    {
        
        FastFeatureDetector retVal = FastFeatureDetector.__fromPtr__(create_3());
        
        return retVal;
    }


    //
    // C++:  int cv::FastFeatureDetector::getType()
    //

    private static native long create_3();


    //
    // C++:  void cv::FastFeatureDetector::setNonmaxSuppression(bool f)
    //

    //javadoc: FastFeatureDetector::getDefaultName()
    public  String getDefaultName()
    {
        
        String retVal = getDefaultName_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::FastFeatureDetector::setThreshold(int threshold)
    //

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }


    //
    // C++:  void cv::FastFeatureDetector::setType(int type)
    //

    // native support for java finalize()
    private static native void delete(long nativeObj);

    // C++:  String cv::FastFeatureDetector::getDefaultName()
    private static native String getDefaultName_0(long nativeObj);

    //javadoc: FastFeatureDetector::getNonmaxSuppression()
    public  boolean getNonmaxSuppression()
    {
        
        boolean retVal = getNonmaxSuppression_0(nativeObj);
        
        return retVal;
    }

    //javadoc: FastFeatureDetector::setNonmaxSuppression(f)
    public  void setNonmaxSuppression(boolean f)
    {
        
        setNonmaxSuppression_0(nativeObj, f);
        
        return;
    }

    // C++:  void cv::FastFeatureDetector::setNonmaxSuppression(bool f)
    private static native void setNonmaxSuppression_0(long nativeObj, boolean f);

    // C++:  bool cv::FastFeatureDetector::getNonmaxSuppression()
    private static native boolean getNonmaxSuppression_0(long nativeObj);

    //javadoc: FastFeatureDetector::getThreshold()
    public  int getThreshold()
    {
        
        int retVal = getThreshold_0(nativeObj);
        
        return retVal;
    }

    //javadoc: FastFeatureDetector::setThreshold(threshold)
    public  void setThreshold(int threshold)
    {
        
        setThreshold_0(nativeObj, threshold);
        
        return;
    }

    // C++:  void cv::FastFeatureDetector::setThreshold(int threshold)
    private static native void setThreshold_0(long nativeObj, int threshold);

    // C++:  int cv::FastFeatureDetector::getThreshold()
    private static native int getThreshold_0(long nativeObj);

    //javadoc: FastFeatureDetector::getType()
    public  int getType()
    {
        
        int retVal = getType_0(nativeObj);
        
        return retVal;
    }

    //javadoc: FastFeatureDetector::setType(type)
    public  void setType(int type)
    {
        
        setType_0(nativeObj, type);
        
        return;
    }

    // C++:  void cv::FastFeatureDetector::setType(int type)
    private static native void setType_0(long nativeObj, int type);

    // C++:  int cv::FastFeatureDetector::getType()
    private static native int getType_0(long nativeObj);

}
