//
// Created by D on 2017/12/19 0019.
//
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <string>
#include "ThreeDES.h"

using namespace std;
/* Header for class com_qingfeng_mytest_jni_JniMethod */

#ifndef _Included_com_qingfeng_mytest_jni_JniMethod
#define _Included_com_qingfeng_mytest_jni_JniMethod
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_qingfeng_mytest_jni_JniMethod
 * Method:    getHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_qingfeng_mytest_jni_JniMethod_getHello
        (JNIEnv *env, jclass jobj) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

#ifdef __cplusplus
}
#endif
#endif
extern "C"
JNIEXPORT jstring JNICALL
Java_com_qingfeng_mytest_jni_JniMethod_encodeData(JNIEnv *env, jclass type, jstring text_,
                                                  jint length) {
    const char *text = env->GetStringUTFChars(text_, 0);

    string msgC;
    msgC.assign(text);

    string returnValue = msgC;

    env->ReleaseStringUTFChars(text_, text);

    return env->NewStringUTF(returnValue.c_str());
}