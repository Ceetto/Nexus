package com.example.nexus.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AgeConfirmComponent(
    onAgeVerifOpenChange: (b: Boolean) -> Unit,
    navController: NavHostController,
){

    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()){
            Icon(imageVector = Icons.Default.Warning, contentDescription = "waring", Modifier.size(100.dp), tint = Color.Red)
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()){
            Text("You need to be over 18 to view this game", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()){
            Button(onClick = {
                onAgeVerifOpenChange(false)
            }, Modifier.padding(10.dp)){
                Text("Confirm", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Button(onClick = {
                navController.navigateUp()
            }, Modifier.padding(10.dp)){
                Text("Cancel", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

    }
}
