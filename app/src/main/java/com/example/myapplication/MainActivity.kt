package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    val TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScaffoldWithNavigationBar()


                }
            }
        }
    }

    @Composable
    fun ScaffoldWithNavigationBar(){
        Scaffold(bottomBar = { BottomNavigationBar() }){
            Box(modifier = Modifier
                .fillMaxSize())
        }
    }

    //TODO: add colors to ui.theme files, also new icon for friend page
    @Composable
    fun BottomNavigationBar(){
        val currentPage = remember {mutableStateOf(0)}
        BottomNavigation(
            elevation = 8.dp, backgroundColor = Color.DarkGray
        ) {
            BottomNavigationItem(
                selected = (currentPage.value == 0),
                onClick = {
                    currentPage.value = 0
                },
                icon = {Icon(imageVector = Icons.Default.Home, "Home")},
            )

            BottomNavigationItem(
                selected = (currentPage.value == 1),
                onClick = {
                    currentPage.value = 1
                },
                icon = {Icon(imageVector = Icons.Default.Notifications, "Notifications")},
            )

            BottomNavigationItem(
                selected = (currentPage.value == 2),
                onClick = {
                    currentPage.value = 2
                },
                icon = {Icon(imageVector = Icons.Default.List, "List")},
            )

            BottomNavigationItem(
                selected = (currentPage.value == 3),
                onClick = {
                    currentPage.value = 3
                },
                icon = {Icon(imageVector = Icons.Default.AccountCircle, "Friends")},
            )

            BottomNavigationItem(
                selected = (currentPage.value == 4),
                onClick = {
                    currentPage.value = 4
                },
                icon = {Icon(imageVector = Icons.Default.Person, "Profile")},
            )
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
