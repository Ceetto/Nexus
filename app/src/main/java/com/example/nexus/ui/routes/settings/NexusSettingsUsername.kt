package com.example.nexus.ui.routes.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
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
        Column(modifier = Modifier.padding(15.dp)){
            Row(modifier = Modifier.padding(5.dp).fillMaxWidth()){
                Text(text = "Current username: ", modifier = Modifier.weight(2f, true))
                Row(modifier = Modifier.weight(3f, true), horizontalArrangement = Arrangement.Center){
                    Text(text = vM.getUsername())
                }

            }

            Row(modifier = Modifier
                .padding(5.dp)
//                .weight(1.5f, true)
            ){
                Text(text = "New username: ", modifier = Modifier.padding(top = 15.dp).weight(2f, true))
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
                    modifier = Modifier.weight(3f, true)
                )
            }

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                Button(onClick = {
                    if (vM.getUsername() != ""){
                        vM.storeUsername(vM.getNewUsername())
                        focusManager.clearFocus()
                        navController.navigateUp()
                    }
                }){
                    Text(text = "Save")
                }
            }
        }
    }

}