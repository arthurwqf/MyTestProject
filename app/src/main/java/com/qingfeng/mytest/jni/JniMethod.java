package com.qingfeng.mytest.jni;

/**
 * Created by WangQF on 2017/12/19 0019.
 */

public class JniMethod {
    static {
        System.loadLibrary("JniTest");
    }

    public static native String getHello();

    public static native String encodeData(String text, int length);

    public static native String decodeData(String text, int length);
}
