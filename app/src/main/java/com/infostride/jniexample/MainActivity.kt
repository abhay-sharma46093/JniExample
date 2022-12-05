package com.infostride.jniexample

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), JniCallbackInterface {
    companion object {
        const val TAG = "MainActivity"

        init {
            CoroutineScope(Dispatchers.Main).launch {
                System.loadLibrary("jniexample")
            }
        }
    }

    private val nativeLibInstance by lazy { NativeLibrary(this) }
    private lateinit var firstText: MutableLiveData<String>
    private lateinit var secondText: MutableLiveData<String>
    private lateinit var thirdText: MutableLiveData<String>
    private lateinit var fourthText: MutableLiveData<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            initData()
            HomeScreenContent()
        }


    }



    @Preview
    @Composable
    fun HomeScreenContent() {

        Column {
            HeadingText()
            Row {
                SendDataToJniContent()
            }
        }


    }

    private fun initData() {
        firstText = MutableLiveData()
        secondText = MutableLiveData()
        thirdText = MutableLiveData()
        fourthText = MutableLiveData()
    }

    @Composable
    private fun SendDataToJniContent() {

        var textFirst by remember { mutableStateOf("") }
        var textSecond by remember { mutableStateOf("") }
        var textThird by remember { mutableStateOf("") }
        var textFourth by remember { mutableStateOf("") }

        Column {

            Row {

                OutlinedButton(onClick = {
                    firstText.value = nativeLibInstance.passDataToJni(100, "Success")
                    /*"Result from JNI : " + if (nativeLibInstance.passDataToJni(100,"Abhay") == 0) "Success!"
                    else "Failed!"*/
                    textFirst = firstText.value.toString()

                }) {
                    Text(
                        text = stringResource(R.string.send_data_to_jni),
                        textAlign = TextAlign.Center,
                    )

                }
                Text(
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    text = textFirst,
                    textAlign = TextAlign.Center
                )
            }

            Row {
                OutlinedButton(onClick = {
                    val modelM = DataModel().apply {
                        age = 22
                        name = "Abhay"
                    }
                    secondText.value = "Result from JNI : " + nativeLibInstance.passObjectToJNI(
                        modelM
                    )
                    textSecond = secondText.value.toString()

                }) {
                    Text(
                        text = stringResource(R.string.send_data_model_to_jni),
                        textAlign = TextAlign.Center
                    )

                }
                Text(
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    text = textSecond,
                    textAlign = TextAlign.Center
                )
            }

            Row {
                OutlinedButton(onClick = {

                    val result = nativeLibInstance.getObjectFromJNI()
                    thirdText.value =
                        "Result from JNI :" + "\n Status :" + result.name + " \n Age : " + result.age

                    textThird = thirdText.value.toString()

                }) {
                    Text(
                        text = stringResource(R.string.get_object_from_jni),
                        textAlign = TextAlign.Center
                    )

                }
                Text(
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    text = textThird,
                    textAlign = TextAlign.Center
                )
            }

            Row {
                OutlinedButton(onClick = {
                    nativeLibInstance.invokeCallbackMethod()
                    textFourth = fourthText.value.toString()

                }) {
                    Text(
                        text = "Callback From JNI", textAlign = TextAlign.Center
                    )

                }
                Text(
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    text = textFourth, textAlign = TextAlign.Center
                )

            }
        }
    }

    @Composable
    private fun HeadingText() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Jni Data Passing Example",
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }

    override fun callbackEvent(code: Int, data: String) {
        fourthText.value = "Status : $data"
        Log.d(TAG, "callbackEvent: | Code : $code | Data = $data")
    }


}