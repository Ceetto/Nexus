package com.example.nexus.ui.routes;

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nexus.viewmodels.NexusProfileViewModel

@Composable
fun NexusSettingsRoute(
    vM: NexusProfileViewModel) {

    Scaffold(){
        Column(){
            Text("Settings", fontSize = dpToSp(40.dp), modifier = Modifier.padding(15.dp))
            Divider(color = Color.White, thickness = 1.dp)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 3.dp)) {
                Button(onClick = {  },modifier = Modifier.fillMaxWidth().height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Username", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 3.dp)) {
                Button(onClick = {},modifier = Modifier.fillMaxWidth().height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Password", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 3.dp)) {
                Button(onClick = {},modifier = Modifier.fillMaxWidth().height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Profile Background", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 3.dp)) {
                Button(onClick = {},modifier = Modifier.fillMaxWidth().height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Profile Picture", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

        }

    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

