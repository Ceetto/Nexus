package com.example.nexus.ui.routes.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusSettingsUsername(vM: NexusProfileViewModel, navController: NavHostController){
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        Column(){
            Row(){
                Text(text = "New Username: ")
                //TextField(value = {  }, onValueChange = "set variabele")
            }

            Row(){
                Text(text = "Confirm Username: ")
                //TextField(value = {  }, onValueChange = "set variabele")

            }

            Row(horizontalArrangement = Arrangement.Center){
                Button(onClick = {}){
                    Text(text = "Save")
                }
            }
        }
    }

}