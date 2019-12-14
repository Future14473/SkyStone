//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

// C++: class KAZE
//javadoc: KAZE

public class KAZE extends Feature2D {

    public static final int
            DIFF_PM_G1 = 0,
            DIFF_PM_G2 = 1,
            DIFF_WEICKERT = 2,
            DIFF_CHARBONNIER = 3;

    protected KAZE(long addr) { super(addr); }

    //javadoc: KAZE::create(extended, upright, threshold, nOctaves, nOctaveLayers, diffusivity)
    public static KAZE create(boolean extended, boolean upright, float threshold, int nOctaves, int nOctaveLayers, int diffusivity)
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_0(extended, upright, threshold, nOctaves, nOctaveLayers, diffusivity));
        
        return retVal;
    }


    //
    // C++: static Ptr_KAZE cv::KAZE::create(bool extended = false, bool upright = false, float threshold = 0.001f, int nOctaves = 4, int nOctaveLayers = 4, int diffusivity = KAZE::DIFF_PM_G2)
    //

    // internal usage only
    public static KAZE __fromPtr__(long addr) { return new KAZE(addr); }

    // C++: static Ptr_KAZE cv::KAZE::create(bool extended = false, bool upright = false, float threshold = 0.001f, int nOctaves = 4, int nOctaveLayers = 4, int diffusivity = KAZE::DIFF_PM_G2)
    private static native long create_0(boolean extended, boolean upright, float threshold, int nOctaves, int nOctaveLayers, int diffusivity);

    //javadoc: KAZE::create(extended, upright, threshold, nOctaves, nOctaveLayers)
    public static KAZE create(boolean extended, boolean upright, float threshold, int nOctaves, int nOctaveLayers)
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_1(extended, upright, threshold, nOctaves, nOctaveLayers));
        
        return retVal;
    }

    private static native long create_1(boolean extended, boolean upright, float threshold, int nOctaves, int nOctaveLayers);

    //javadoc: KAZE::create(extended, upright, threshold, nOctaves)
    public static KAZE create(boolean extended, boolean upright, float threshold, int nOctaves)
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_2(extended, upright, threshold, nOctaves));
        
        return retVal;
    }

    private static native long create_2(boolean extended, boolean upright, float threshold, int nOctaves);

    //javadoc: KAZE::create(extended, upright, threshold)
    public static KAZE create(boolean extended, boolean upright, float threshold)
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_3(extended, upright, threshold));
        
        return retVal;
    }


    //
    // C++:  String cv::KAZE::getDefaultName()
    //

    private static native long create_3(boolean extended, boolean upright, float threshold);


    //
    // C++:  bool cv::KAZE::getExtended()
    //

    //javadoc: KAZE::create(extended, upright)
    public static KAZE create(boolean extended, boolean upright)
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_4(extended, upright));
        
        return retVal;
    }


    //
    // C++:  bool cv::KAZE::getUpright()
    //

    private static native long create_4(boolean extended, boolean upright);


    //
    // C++:  double cv::KAZE::getThreshold()
    //

    //javadoc: KAZE::create(extended)
    public static KAZE create(boolean extended)
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_5(extended));
        
        return retVal;
    }


    //
    // C++:  int cv::KAZE::getDiffusivity()
    //

    private static native long create_5(boolean extended);


    //
    // C++:  int cv::KAZE::getNOctaveLayers()
    //

    //javadoc: KAZE::create()
    public static KAZE create()
    {
        
        KAZE retVal = KAZE.__fromPtr__(create_6());
        
        return retVal;
    }


    //
    // C++:  int cv::KAZE::getNOctaves()
    //

    private static native long create_6();


    //
    // C++:  void cv::KAZE::setDiffusivity(int diff)
    //

    //javadoc: KAZE::getDefaultName()
    public  String getDefaultName()
    {
        
        String retVal = getDefaultName_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::KAZE::setExtended(bool extended)
    //

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }


    //
    // C++:  void cv::KAZE::setNOctaveLayers(int octaveLayers)
    //

    // native support for java finalize()
    private static native void delete(long nativeObj);


    //
    // C++:  void cv::KAZE::setNOctaves(int octaves)
    //

    // C++:  String cv::KAZE::getDefaultName()
    private static native String getDefaultName_0(long nativeObj);


    //
    // C++:  void cv::KAZE::setThreshold(double threshold)
    //

    //javadoc: KAZE::getExtended()
    public  boolean getExtended()
    {
        
        boolean retVal = getExtended_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::KAZE::setUpright(bool upright)
    //

    //javadoc: KAZE::setExtended(extended)
    public  void setExtended(boolean extended)
    {
        
        setExtended_0(nativeObj, extended);
        
        return;
    }

    // C++:  void cv::KAZE::setExtended(bool extended)
    private static native void setExtended_0(long nativeObj, boolean extended);

    // C++:  bool cv::KAZE::getExtended()
    private static native boolean getExtended_0(long nativeObj);

    //javadoc: KAZE::getUpright()
    public  boolean getUpright()
    {
        
        boolean retVal = getUpright_0(nativeObj);
        
        return retVal;
    }

    //javadoc: KAZE::setUpright(upright)
    public  void setUpright(boolean upright)
    {
        
        setUpright_0(nativeObj, upright);
        
        return;
    }

    // C++:  void cv::KAZE::setUpright(bool upright)
    private static native void setUpright_0(long nativeObj, boolean upright);

    // C++:  bool cv::KAZE::getUpright()
    private static native boolean getUpright_0(long nativeObj);

    //javadoc: KAZE::getThreshold()
    public  double getThreshold()
    {
        
        double retVal = getThreshold_0(nativeObj);
        
        return retVal;
    }

    //javadoc: KAZE::setThreshold(threshold)
    public  void setThreshold(double threshold)
    {
        
        setThreshold_0(nativeObj, threshold);
        
        return;
    }

    // C++:  void cv::KAZE::setThreshold(double threshold)
    private static native void setThreshold_0(long nativeObj, double threshold);

    // C++:  double cv::KAZE::getThreshold()
    private static native double getThreshold_0(long nativeObj);

    //javadoc: KAZE::getDiffusivity()
    public  int getDiffusivity()
    {
        
        int retVal = getDiffusivity_0(nativeObj);
        
        return retVal;
    }

    //javadoc: KAZE::setDiffusivity(diff)
    public  void setDiffusivity(int diff)
    {
        
        setDiffusivity_0(nativeObj, diff);
        
        return;
    }

    // C++:  void cv::KAZE::setDiffusivity(int diff)
    private static native void setDiffusivity_0(long nativeObj, int diff);

    // C++:  int cv::KAZE::getDiffusivity()
    private static native int getDiffusivity_0(long nativeObj);

    //javadoc: KAZE::getNOctaveLayers()
    public  int getNOctaveLayers()
    {
        
        int retVal = getNOctaveLayers_0(nativeObj);
        
        return retVal;
    }

    //javadoc: KAZE::setNOctaveLayers(octaveLayers)
    public  void setNOctaveLayers(int octaveLayers)
    {
        
        setNOctaveLayers_0(nativeObj, octaveLayers);
        
        return;
    }

    // C++:  void cv::KAZE::setNOctaveLayers(int octaveLayers)
    private static native void setNOctaveLayers_0(long nativeObj, int octaveLayers);

    // C++:  int cv::KAZE::getNOctaveLayers()
    private static native int getNOctaveLayers_0(long nativeObj);

    //javadoc: KAZE::getNOctaves()
    public  int getNOctaves()
    {
        
        int retVal = getNOctaves_0(nativeObj);
        
        return retVal;
    }

    //javadoc: KAZE::setNOctaves(octaves)
    public  void setNOctaves(int octaves)
    {
        
        setNOctaves_0(nativeObj, octaves);
        
        return;
    }

    // C++:  void cv::KAZE::setNOctaves(int octaves)
    private static native void setNOctaves_0(long nativeObj, int octaves);

    // C++:  int cv::KAZE::getNOctaves()
    private static native int getNOctaves_0(long nativeObj);

}
