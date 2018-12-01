//
// Created by naihe on 2018/10/17.
//
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>

extern "C"
jstring Java_com_example_myceshi_JNITest(JNIEnv *env, jobject obj, jstring str) {
    return env->NewStringUTF(":这里是来自c的string");
}


