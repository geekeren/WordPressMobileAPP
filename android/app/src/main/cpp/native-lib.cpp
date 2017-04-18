#include <jni.h>
#include <string>
#include "BlogConfig.h"
extern "C"
JNIEXPORT jstring JNICALL
Java_cn_wangbaiyuan_blog_NdkInterface_getHomeUrl(JNIEnv *env, jclass type) {
    BlogConfig config;
    std::string hello = config.homeUrl;
    return env->NewStringUTF(hello.c_str());
}



