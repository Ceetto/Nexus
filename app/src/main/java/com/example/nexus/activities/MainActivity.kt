package com.example.nexus.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.nexus.Home
import com.example.nexus.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = MainActivity::class.java.name

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Home()
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart function")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume function")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause function")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop function")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy function")
    }

}
