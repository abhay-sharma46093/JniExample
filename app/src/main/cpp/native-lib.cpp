#include <jni.h>
#include <string>
#include "StoreValue.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_infostride_jniexample_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_infostride_jniexample_NativeLibrary_passDataToJni(JNIEnv *env, jobject thiz,
                                                           jint i,jstring string) {
    int result = -1;
    if (i == 100) {
        result = 0;
    }
    const char *nativeString = env->GetStringUTFChars(string,0);
    env->ReleaseStringUTFChars(string,nativeString);
    if (strlen(nativeString)!=5){
        result = -1;
    }

    return env->NewStringUTF(nativeString);
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_infostride_jniexample_NativeLibrary_getObjectFromJNI(JNIEnv *env, jobject thiz) {
    jclass objClass = env->FindClass("com/infostride/jniexample/DataModel");

    jmethodID methodId = env->GetMethodID(objClass,"<init>", "()V");
    jobject sampleObj = env->NewObject(objClass,methodId);

    const char *successString = "SUCCESS!";

    jfieldID sampleStringField = env->GetFieldID(objClass,"name","Ljava/lang/String;");
    env->SetObjectField(sampleObj,sampleStringField,env->NewStringUTF(successString));


    int ageData = 15;
     jfieldID sampleIntField = env->GetFieldID(objClass,"age","I");
    env->SetIntField(sampleObj,sampleIntField,ageData);


    return sampleObj;
}




extern "C" JNIEXPORT jstring JNICALL
Java_com_infostride_jniexample_NativeLibrary_passObjectToJNI(JNIEnv *env,
                              jobject nativeLib,
                              jobject obj) {

    std::string result = "Success!";

    jclass dataClass = env->GetObjectClass(obj);


    jfieldID ageId = env->GetFieldID(dataClass,"age", "I");
    jfieldID nameId = env->GetFieldID(dataClass,"name", "Ljava/lang/String;");

    jint phone = env->GetIntField(obj, ageId);

    if (phone == 0) {
        result = "fail!";
    }
    return env->NewStringUTF(result.c_str());
}


/*
typedef struct {
    char* mKey;
    StoreType mType;
    StoreValue mValue;
} StoreEntry;

typedef union {
    float mFloat;
    int32_t mInteger;
    char* mString;
    jobject mObject;
    jboolean mBoolean;
    jshort mShort;
    jlong mLong;
    jdouble mDouble;
    jbyte mByte;
} StoreValue;

typedef enum {
    StoreType_Float,
    StoreType_String,
    StoreType_Integer,
    StoreType_Object,
    StoreType_Boolean,
    StoreType_Short,
    StoreType_Long,
    StoreType_Double,
    StoreType_Byte,
} StoreType;*/

extern "C"
JNIEXPORT void JNICALL
Java_com_infostride_jniexample_NativeLibrary_invokeCallbackMethod(JNIEnv *env, jobject nativeLibrary) {
    jclass classInstance = env->GetObjectClass(nativeLibrary);
    jmethodID callbackMethodJava = env->GetMethodID(classInstance,"callBack", "(ILjava/lang/String;)V");
    if (NULL==callbackMethodJava) return;
    const char *successStatus = "Success!";
    int data = 200;
    env->CallVoidMethod(nativeLibrary,callbackMethodJava,data,env->NewStringUTF(successStatus));
}