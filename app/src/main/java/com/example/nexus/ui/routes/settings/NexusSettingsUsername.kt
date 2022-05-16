package com.example.nexus.ui.routes.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.profile.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusSettingsUsername(vM: NexusProfileViewModel, navController: NavHostController){
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        Column(modifier = Modifier.padding(5.dp)){
            Row(modifier = Modifier.padding(5.dp)){
                Text(text = "Current username: " + vM.getUsername())
            }

            Row(modifier = Modifier.padding(5.dp)){
                Text(text = "New username: ")
                TextField(
                    value = vM.getNewUsername(),
                    onValueChange = { value ->
                        vM.setUsername(value)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                )
            }

            Row(horizontalArrangement = Arrangement.Center){
                Button(onClick = {
                    if (vM.getUsername() != ""){
                        vM.storeUsername(vM.getNewUsername())
                        focusManager.clearFocus()
                        navController.navigateUp()
                    }
                }, modifier = Modifier.padding(5.dp)){
                    Text(text = "Save")
                }
            }
        }
    }

}