package com.example.nexus.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.components.gameForm.DeleteButton
import com.example.nexus.ui.components.gameForm.SaveButton
import com.example.nexus.ui.components.gameForm.TimeInput
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

//TODO updaten en verwijderen

@Composable
fun GameFormComponent(
    vM: NexusGameDetailViewModel
){
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .fillMaxSize()) {
        Row(){
            IconButton(onClick = { vM.onGameFormOpenChanged(false)
                                    focusManager.clearFocus()}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close game form",
                )
            }
        }

        //score input
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Your score: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf(vM.getGameScore().toString())}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text, color=MaterialTheme.colors.onBackground)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { expanded = false
                                            text = "No score"
                                            vM.setGameScore(0)}) {
                    Text(text = "No score")
                }
                listOf(1,2,3,4,5,6,7,8,9,10).forEach { score ->
                    DropdownMenuItem(onClick = { expanded = false
                                                text = score.toString()
                                                vM.setGameScore(score)}) {
                        Text(text = score.toString())
                    }
                }
            }
        }

        //status input
        Row(modifier = Modifier.padding(5.dp)){
            Text(text = "Status: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf(vM.getGameStatus())}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text, color=MaterialTheme.colors.onBackground)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                ListCategory.values().forEach { status ->
                    DropdownMenuItem(onClick = { expanded = false
                                                text = status.value
                                                vM.setGameStatus(status.value)}) {
                        Text(text = status.value, color = Color.White)
                    }
                }
            }
        }

        //hours input
        TimeInput(focusManager = focusManager, text = "Hours played: ", getTime = { vM.getHours() },
            setTime = {hours -> vM.setHours(hours)})

        //minutes input
        TimeInput(focusManager = focusManager, text = "Minutes played: ", getTime = { vM.getMinutes() },
            setTime = {minutes -> vM.setMinutes(minutes)})


        Row(modifier = Modifier.padding(5.dp)){
            SaveButton(vM = vM, focusManager = focusManager)

            DeleteButton(vM = vM, focusManager = focusManager)
        }

        if(vM.getShowErrorPopup()){
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = {vM.onShowErrorPopupChanged(false)})
                    { Text(text = "OK") }
                },
                title = { Text(text = "wrong time") },
                text = { Text(text = "Please provide a valid time (integers only)") }
            )
        }
    }
}
