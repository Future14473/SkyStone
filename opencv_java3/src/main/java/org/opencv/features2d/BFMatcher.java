//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.features2d;

// C++: class BFMatcher
//javadoc: BFMatcher

public class BFMatcher extends DescriptorMatcher {

    protected BFMatcher(long addr) { super(addr); }

    //javadoc: BFMatcher::BFMatcher(normType, crossCheck)
    public   BFMatcher(int normType, boolean crossCheck)
    {
        
        super( BFMatcher_0(normType, crossCheck) );
        
        return;
    }

    //
    // C++:   cv::BFMatcher::BFMatcher(int normType = NORM_L2, bool crossCheck = false)
    //

    // C++:   cv::BFMatcher::BFMatcher(int normType = NORM_L2, bool crossCheck = false)
    private static native long BFMatcher_0(int normType, boolean crossCheck);

    //javadoc: BFMatcher::BFMatcher(normType)
    public   BFMatcher(int normType)
    {
        
        super( BFMatcher_1(normType) );
        
        return;
    }

    private static native long BFMatcher_1(int normType);


    //
    // C++: static Ptr_BFMatcher cv::BFMatcher::create(int normType = NORM_L2, bool crossCheck = false)
    //

    //javadoc: BFMatcher::BFMatcher()
    public   BFMatcher()
    {
        
        super( BFMatcher_2() );
        
        return;
    }

    private static native long BFMatcher_2();

    //javadoc: BFMatcher::create(normType, crossCheck)
    public static BFMatcher create(int normType, boolean crossCheck)
    {
        
        BFMatcher retVal = BFMatcher.__fromPtr__(create_0(normType, crossCheck));
        
        return retVal;
    }

    // internal usage only
    public static BFMatcher __fromPtr__(long addr) { return new BFMatcher(addr); }

    // C++: static Ptr_BFMatcher cv::BFMatcher::create(int normType = NORM_L2, bool crossCheck = false)
    private static native long create_0(int normType, boolean crossCheck);

    //javadoc: BFMatcher::create(normType)
    public static BFMatcher create(int normType)
    {
        
        BFMatcher retVal = BFMatcher.__fromPtr__(create_1(normType));
        
        return retVal;
    }

    private static native long create_1(int normType);

    //javadoc: BFMatcher::create()
    public static BFMatcher create()
    {
        
        BFMatcher retVal = BFMatcher.__fromPtr__(create_2());
        
        return retVal;
    }

    private static native long create_2();

    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
