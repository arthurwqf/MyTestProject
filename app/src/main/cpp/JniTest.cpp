//
// Created by D on 2017/12/19 0019.
//
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <string>
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
  (JNIEnv *env, jclass jobj){
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
  }

#ifdef __cplusplus
}
#endif
#endif

