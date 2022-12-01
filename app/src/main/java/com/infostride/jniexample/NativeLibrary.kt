package com.infostride.jniexample

class NativeLibrary constructor(private val callbackInterface: JniCallbackInterface) {

    external fun passDataToJni(int: Int,string:String): String
    external fun passObjectToJNI(dataModel: DataModel): String
    external fun getObjectFromJNI(): DataModel
    external fun invokeCallbackMethod()

    fun callBack(code:Int,data: String) {
        callbackInterface.callbackEvent(code,data)
    }
}