package com.example.nexus.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import com.example.nexus.ApplicationSwitcher
import com.example.nexus.ui.theme.MyApplicationTheme
import com.example.nexus.viewmodels.UserState
import com.example.nexus.viewmodels.UserStateViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = MainActivity::class.java.name
    private val userState by viewModels<UserStateViewModel>()
    companion object {
        val TAG : String = MainActivity::class.java.simpleName
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(UserState provides userState) {
                    ApplicationSwitcher()
                }
                // A surface container using the 'background' color from the theme
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
