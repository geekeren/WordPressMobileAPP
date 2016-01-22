//
// Created by BrainWang on 2016/1/9.
//

#include "cn_wangbaiyuan_byblog_jni_BYJNI.h"
const char* HOMEURL="http://wangbaiyuan.cn/";
const char* APIURL ="http://wangbaiyuan.cn/app/api.php";
const char* APPLINK="wbyblog://wangbaiyuan.cn/";
const char* BAIDU_PUSH_API_KEY="xxxxxxxxxxxxxxxxxxxxxxx";
jstring JNICALL Java_cn_wangbaiyuan_byblog_jni_BYJNI_getHOMEURL
        (JNIEnv* Env, jobject job){
    return  Env->NewStringUTF(HOMEURL);
}

/*
 * Class:     cn_wangbaiyuan_byblog_jni_BYJNI
 * Method:    getAPPLINK
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_wangbaiyuan_byblog_jni_BYJNI_getAPPLINK
        (JNIEnv* Env, jobject job){
    return  Env->NewStringUTF(APPLINK);
}

/*
 * Class:     cn_wangbaiyuan_byblog_jni_BYJNI
 * Method:    getAPIURL
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_wangbaiyuan_byblog_jni_BYJNI_getAPIURL
        (JNIEnv* Env, jobject job){
    return  Env->NewStringUTF(APIURL);
}

/*
 * Class:     cn_wangbaiyuan_byblog_jni_BYJNI
 * Method:    getBAIDU_PUSH_API_KEY
 * Signature: ()Ljava/lang/String;
 */





jstring Java_cn_wangbaiyuan_byblog_jni_BYJNI_getBAIDU_1PUSH_1API_1KEY(JNIEnv *env,
                                                                      jobject jobject1) {
    return env->NewStringUTF(BAIDU_PUSH_API_KEY);
}
