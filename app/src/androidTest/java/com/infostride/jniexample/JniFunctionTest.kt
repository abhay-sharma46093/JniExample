package com.infostride.jniexample

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class JniFunctionTest {
   @get:Rule
   val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkInitialStatus(){
//        composeTestRule.onNodeWithText("").assertExists()
    }



    @Test
    fun clickOn_passDataToJni(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.send_data_to_jni))
            .performClick()
        composeTestRule.onNodeWithText("Success").assertExists()
    }
    @Test
    fun clickOn_sendDataModelToJni(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.send_data_model_to_jni))
            .performClick()
        composeTestRule.onNodeWithText("Result from JNI : Success!").assertExists()
    }
    @Test
    fun clickOn_getDataObjectFromJni(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.send_data_to_jni))
            .performClick()
       // composeTestRule.onNodeWithText("Success").assertExists()
    }

}