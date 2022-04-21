package com.example.nexus.ui.routes;

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.viewmodels.NexusProfileViewModel

@Composable
fun NexusSettingsRoute(vM: NexusProfileViewModel) {
    SettingsTabs()
}

//https://stackoverflow.com/questions/67918698/why-is-there-only-sp-in-fontsize-of-text-composable-and-not-dp-in-jetp
@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun SettingsTabs() {
    Scaffold(){
        Column(){
            Text("Settings", fontSize = dpToSp(40.dp), modifier = Modifier.padding(15.dp))
            Divider(color = Color.White, thickness = 1.dp)

            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)) {
                Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0)) {
                    Text("Change Username", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)) {
                Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0)) {
                    Text("Change Password", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)) {
                Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0)) {
                    Text("Change Profile Background", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)) {
                Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0)) {
                    Text("Change Profile Picture", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)) {
                Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0)) {
                    Text("Link With Steam", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }
        }

    }
}


