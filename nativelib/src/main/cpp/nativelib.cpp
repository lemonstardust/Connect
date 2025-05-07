#include <jni.h>
#include <string>
#include <android/log.h>

class __android_log_print;

#define LOG_D(...)  __android_log_print(ANDROID_LOG_DEBUG, "native-lib", __VA_ARGS__)
#define LOG_E(...)  __android_log_print(ANDROID_LOG_ERROR, "native-lib", __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nativelib_NativeLib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_example_nativelib_NativeLib_auth(JNIEnv *env, jobject thiz, jstring source_str,
                                          jstring dest_str) {
    const char *sourcestr = env->GetStringUTFChars(source_str, JNI_FALSE);
    const char *deststr = env->GetStringUTFChars(dest_str, JNI_FALSE);
    FILE *fp;
    FILE *fd;
    char buf[1024];
    fp = fopen(sourcestr, "rw");
    if (fp == NULL) {
        LOG_D("open source filed");
    }
    fd = fopen(deststr, "w");//当文件不存在时，自动创建该文件
    if (fd == NULL) {
        LOG_E("open des filed");
    }
    while (fgets(buf, sizeof(buf), fp) != NULL) {
        fputs(buf, fd);
    }
    if (fp != NULL) {
        fclose(fp);
    }
    if (fd != NULL) {
        fclose(fd);
    }
    env->ReleaseStringUTFChars(source_str, sourcestr);
    env->ReleaseStringUTFChars(dest_str, deststr);
}
