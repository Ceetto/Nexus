package com.example.nexus.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.api.igdb.request.IGDBWrapper
import com.example.nexus.ApplicationSwitcher
import com.example.nexus.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.java.name
    companion object {
        val TAG : String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "dkpkznvjkcsgjgeauw8fz2dpgj7wvk")

        setContent {
            MyApplicationTheme {
                ApplicationSwitcher(vM = hiltViewModel())
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
