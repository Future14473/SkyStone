//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

// C++: class AKAZE
//javadoc: AKAZE

public class AKAZE extends Feature2D {

    public static final int
            DESCRIPTOR_KAZE_UPRIGHT = 2,
            DESCRIPTOR_KAZE = 3,
            DESCRIPTOR_MLDB_UPRIGHT = 4,
            DESCRIPTOR_MLDB = 5;

    protected AKAZE(long addr) { super(addr); }

    //javadoc: AKAZE::create(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves, nOctaveLayers, diffusivity)
    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves, int nOctaveLayers, int diffusivity)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_0(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves, nOctaveLayers, diffusivity));
        
        return retVal;
    }


    //
    // C++: static Ptr_AKAZE cv::AKAZE::create(int descriptor_type = AKAZE::DESCRIPTOR_MLDB, int descriptor_size = 0, int descriptor_channels = 3, float threshold = 0.001f, int nOctaves = 4, int nOctaveLayers = 4, int diffusivity = KAZE::DIFF_PM_G2)
    //

    // internal usage only
    public static AKAZE __fromPtr__(long addr) { return new AKAZE(addr); }

    // C++: static Ptr_AKAZE cv::AKAZE::create(int descriptor_type = AKAZE::DESCRIPTOR_MLDB, int descriptor_size = 0, int descriptor_channels = 3, float threshold = 0.001f, int nOctaves = 4, int nOctaveLayers = 4, int diffusivity = KAZE::DIFF_PM_G2)
    private static native long create_0(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves, int nOctaveLayers, int diffusivity);

    //javadoc: AKAZE::create(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves, nOctaveLayers)
    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves, int nOctaveLayers)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_1(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves, nOctaveLayers));
        
        return retVal;
    }

    private static native long create_1(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves, int nOctaveLayers);

    //javadoc: AKAZE::create(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves)
    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_2(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves));
        
        return retVal;
    }

    private static native long create_2(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves);

    //javadoc: AKAZE::create(descriptor_type, descriptor_size, descriptor_channels, threshold)
    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_3(descriptor_type, descriptor_size, descriptor_channels, threshold));
        
        return retVal;
    }

    private static native long create_3(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold);


    //
    // C++:  String cv::AKAZE::getDefaultName()
    //

    //javadoc: AKAZE::create(descriptor_type, descriptor_size, descriptor_channels)
    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_4(descriptor_type, descriptor_size, descriptor_channels));
        
        return retVal;
    }


    //
    // C++:  double cv::AKAZE::getThreshold()
    //

    private static native long create_4(int descriptor_type, int descriptor_size, int descriptor_channels);


    //
    // C++:  int cv::AKAZE::getDescriptorChannels()
    //

    //javadoc: AKAZE::create(descriptor_type, descriptor_size)
    public static AKAZE create(int descriptor_type, int descriptor_size)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_5(descriptor_type, descriptor_size));
        
        return retVal;
    }


    //
    // C++:  int cv::AKAZE::getDescriptorSize()
    //

    private static native long create_5(int descriptor_type, int descriptor_size);


    //
    // C++:  int cv::AKAZE::getDescriptorType()
    //

    //javadoc: AKAZE::create(descriptor_type)
    public static AKAZE create(int descriptor_type)
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_6(descriptor_type));
        
        return retVal;
    }


    //
    // C++:  int cv::AKAZE::getDiffusivity()
    //

    private static native long create_6(int descriptor_type);


    //
    // C++:  int cv::AKAZE::getNOctaveLayers()
    //

    //javadoc: AKAZE::create()
    public static AKAZE create()
    {
        
        AKAZE retVal = AKAZE.__fromPtr__(create_7());
        
        return retVal;
    }


    //
    // C++:  int cv::AKAZE::getNOctaves()
    //

    private static native long create_7();


    //
    // C++:  void cv::AKAZE::setDescriptorChannels(int dch)
    //

    //javadoc: AKAZE::getDefaultName()
    public  String getDefaultName()
    {
        
        String retVal = getDefaultName_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::AKAZE::setDescriptorSize(int dsize)
    //

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }


    //
    // C++:  void cv::AKAZE::setDescriptorType(int dtype)
    //

    // native support for java finalize()
    private static native void delete(long nativeObj);


    //
    // C++:  void cv::AKAZE::setDiffusivity(int diff)
    //

    // C++:  String cv::AKAZE::getDefaultName()
    private static native String getDefaultName_0(long nativeObj);


    //
    // C++:  void cv::AKAZE::setNOctaveLayers(int octaveLayers)
    //

    //javadoc: AKAZE::getThreshold()
    public  double getThreshold()
    {
        
        double retVal = getThreshold_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::AKAZE::setNOctaves(int octaves)
    //

    //javadoc: AKAZE::setThreshold(threshold)
    public  void setThreshold(double threshold)
    {
        
        setThreshold_0(nativeObj, threshold);
        
        return;
    }


    //
    // C++:  void cv::AKAZE::setThreshold(double threshold)
    //

    // C++:  void cv::AKAZE::setThreshold(double threshold)
    private static native void setThreshold_0(long nativeObj, double threshold);

    // C++:  double cv::AKAZE::getThreshold()
    private static native double getThreshold_0(long nativeObj);

    //javadoc: AKAZE::getDescriptorChannels()
    public  int getDescriptorChannels()
    {
        
        int retVal = getDescriptorChannels_0(nativeObj);
        
        return retVal;
    }

    //javadoc: AKAZE::setDescriptorChannels(dch)
    public  void setDescriptorChannels(int dch)
    {
        
        setDescriptorChannels_0(nativeObj, dch);
        
        return;
    }

    // C++:  void cv::AKAZE::setDescriptorChannels(int dch)
    private static native void setDescriptorChannels_0(long nativeObj, int dch);

    // C++:  int cv::AKAZE::getDescriptorChannels()
    private static native int getDescriptorChannels_0(long nativeObj);

    //javadoc: AKAZE::getDescriptorSize()
    public  int getDescriptorSize()
    {
        
        int retVal = getDescriptorSize_0(nativeObj);
        
        return retVal;
    }

    //javadoc: AKAZE::setDescriptorSize(dsize)
    public  void setDescriptorSize(int dsize)
    {
        
        setDescriptorSize_0(nativeObj, dsize);
        
        return;
    }

    // C++:  void cv::AKAZE::setDescriptorSize(int dsize)
    private static native void setDescriptorSize_0(long nativeObj, int dsize);

    // C++:  int cv::AKAZE::getDescriptorSize()
    private static native int getDescriptorSize_0(long nativeObj);

    //javadoc: AKAZE::getDescriptorType()
    public  int getDescriptorType()
    {
        
        int retVal = getDescriptorType_0(nativeObj);
        
        return retVal;
    }

    //javadoc: AKAZE::setDescriptorType(dtype)
    public  void setDescriptorType(int dtype)
    {
        
        setDescriptorType_0(nativeObj, dtype);
        
        return;
    }

    // C++:  void cv::AKAZE::setDescriptorType(int dtype)
    private static native void setDescriptorType_0(long nativeObj, int dtype);

    // C++:  int cv::AKAZE::getDescriptorType()
    private static native int getDescriptorType_0(long nativeObj);

    //javadoc: AKAZE::getDiffusivity()
    public  int getDiffusivity()
    {
        
        int retVal = getDiffusivity_0(nativeObj);
        
        return retVal;
    }

    //javadoc: AKAZE::setDiffusivity(diff)
    public  void setDiffusivity(int diff)
    {
        
        setDiffusivity_0(nativeObj, diff);
        
        return;
    }

    // C++:  void cv::AKAZE::setDiffusivity(int diff)
    private static native void setDiffusivity_0(long nativeObj, int diff);

    // C++:  int cv::AKAZE::getDiffusivity()
    private static native int getDiffusivity_0(long nativeObj);

    //javadoc: AKAZE::getNOctaveLayers()
    public  int getNOctaveLayers()
    {
        
        int retVal = getNOctaveLayers_0(nativeObj);
        
        return retVal;
    }

    //javadoc: AKAZE::setNOctaveLayers(octaveLayers)
    public  void setNOctaveLayers(int octaveLayers)
    {
        
        setNOctaveLayers_0(nativeObj, octaveLayers);
        
        return;
    }

    // C++:  void cv::AKAZE::setNOctaveLayers(int octaveLayers)
    private static native void setNOctaveLayers_0(long nativeObj, int octaveLayers);

    // C++:  int cv::AKAZE::getNOctaveLayers()
    private static native int getNOctaveLayers_0(long nativeObj);

    //javadoc: AKAZE::getNOctaves()
    public  int getNOctaves()
    {
        
        int retVal = getNOctaves_0(nativeObj);
        
        return retVal;
    }

    //javadoc: AKAZE::setNOctaves(octaves)
    public  void setNOctaves(int octaves)
    {
        
        setNOctaves_0(nativeObj, octaves);
        
        return;
    }

    // C++:  void cv::AKAZE::setNOctaves(int octaves)
    private static native void setNOctaves_0(long nativeObj, int octaves);

    // C++:  int cv::AKAZE::getNOctaves()
    private static native int getNOctaves_0(long nativeObj);

}
