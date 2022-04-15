package com.example.nexus.ui.routes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus.viewmodels.NexusProfileViewModel

@Composable
fun NexusProfileRoute(vM: NexusProfileViewModel){
    NexusProfileScreen(

    )
}


@Composable
fun NexusProfileScreen(){
    Scaffold( topBar = {
        TopAppBar(
            title = { Text("Profile") },
            )
        Column (
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ){
            IconButton(
                onClick = {/* TODO */}
                , modifier = Modifier.
                then(Modifier.size(58.dp))
            ) {
                Icon(Icons.Default.Settings, "Settings")
            }

        }
    }) {

    }
}

