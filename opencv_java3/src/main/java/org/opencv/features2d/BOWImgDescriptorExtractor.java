//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

// C++: class BOWImgDescriptorExtractor
//javadoc: BOWImgDescriptorExtractor

public class BOWImgDescriptorExtractor {

    protected final long nativeObj;
    protected BOWImgDescriptorExtractor(long addr) { nativeObj = addr; }

    // internal usage only
    public static BOWImgDescriptorExtractor __fromPtr__(long addr) { return new BOWImgDescriptorExtractor(addr); }

    public long getNativeObjAddr() { return nativeObj; }

    //
    // C++:   cv::BOWImgDescriptorExtractor::BOWImgDescriptorExtractor(Ptr_DescriptorExtractor dextractor, Ptr_DescriptorMatcher dmatcher)
    //

    // Unknown type 'Ptr_DescriptorExtractor' (I), skipping the function


    //
    // C++:  Mat cv::BOWImgDescriptorExtractor::getVocabulary()
    //

    //javadoc: BOWImgDescriptorExtractor::getVocabulary()
    public  Mat getVocabulary()
    {
        
        Mat retVal = new Mat(getVocabulary_0(nativeObj));
        
        return retVal;
    }


    //
    // C++:  int cv::BOWImgDescriptorExtractor::descriptorSize()
    //

    //javadoc: BOWImgDescriptorExtractor::setVocabulary(vocabulary)
    public  void setVocabulary(Mat vocabulary)
    {
        
        setVocabulary_0(nativeObj, vocabulary.nativeObj);
        
        return;
    }


    //
    // C++:  int cv::BOWImgDescriptorExtractor::descriptorType()
    //

    // C++:  void cv::BOWImgDescriptorExtractor::setVocabulary(Mat vocabulary)
    private static native void setVocabulary_0(long nativeObj, long vocabulary_nativeObj);


    //
    // C++:  void cv::BOWImgDescriptorExtractor::compute2(Mat image, vector_KeyPoint keypoints, Mat& imgDescriptor)
    //

    // C++:  Mat cv::BOWImgDescriptorExtractor::getVocabulary()
    private static native long getVocabulary_0(long nativeObj);


    //
    // C++:  void cv::BOWImgDescriptorExtractor::setVocabulary(Mat vocabulary)
    //

    //javadoc: BOWImgDescriptorExtractor::descriptorSize()
    public  int descriptorSize()
    {
        
        int retVal = descriptorSize_0(nativeObj);
        
        return retVal;
    }

    // C++:  int cv::BOWImgDescriptorExtractor::descriptorSize()
    private static native int descriptorSize_0(long nativeObj);

    //javadoc: BOWImgDescriptorExtractor::descriptorType()
    public  int descriptorType()
    {
        
        int retVal = descriptorType_0(nativeObj);
        
        return retVal;
    }

    // C++:  int cv::BOWImgDescriptorExtractor::descriptorType()
    private static native int descriptorType_0(long nativeObj);

    //javadoc: BOWImgDescriptorExtractor::compute(image, keypoints, imgDescriptor)
    public  void compute(Mat image, MatOfKeyPoint keypoints, Mat imgDescriptor)
    {
        Mat keypoints_mat = keypoints;
        compute_0(nativeObj, image.nativeObj, keypoints_mat.nativeObj, imgDescriptor.nativeObj);
        
        return;
    }

    // C++:  void cv::BOWImgDescriptorExtractor::compute2(Mat image, vector_KeyPoint keypoints, Mat& imgDescriptor)
    private static native void compute_0(long nativeObj, long image_nativeObj, long keypoints_mat_nativeObj, long imgDescriptor_nativeObj);

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
