package com.example.nexus.ui.routes.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.profile.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusSettingsRoute(
    vM: NexusProfileViewModel,
    navController: NavHostController,
    onUsernameClick: () -> Unit,
    onPictureClick: () -> Unit,
    onBackgroundClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ){
        Column(){
            Text("Settings", fontSize = dpToSp(40.dp), modifier = Modifier.padding(15.dp))
            Divider(color = Color.White, thickness = 1.dp)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 3.dp)) {
                Button(onClick = {onUsernameClick()} ,modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Username", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 3.dp)) {
                Button(onClick = onBackgroundClick, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Profile Background", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 3.dp)) {
                Button(onClick = onPictureClick,modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Change Profile Picture", textAlign = TextAlign.Start)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">")
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 3.dp)) {
                Button(onClick = { vM.setShowLogoutPopup(true) }, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), shape = RoundedCornerShape(0)) {
                    Text("Logout", textAlign = TextAlign.Start, color = Color.Red)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.Logout, contentDescription = "logout icon", tint = Color.Red)
                }
            }
        }

        if (vM.getShowLogoutPopup()){
            AlertDialog(
                onDismissRequest = { vM.setShowLogoutPopup(false)},
                confirmButton = {
                    TextButton(onClick = {
                        vM.setShowLogoutPopup(false)
                        vM.logOut()})
                    { Text(text = "Yes", color=MaterialTheme.colors.onBackground) }
                },
                dismissButton = {
                    TextButton(onClick = {
                        vM.setShowLogoutPopup(false) }) {
                        Text(text = "Cancel", color=MaterialTheme.colors.onBackground)
                    }},
                title = { Text(text = "Logout") },
                text = { Text(text = "Are you sure you want to logout?") }
            )
        }

    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

