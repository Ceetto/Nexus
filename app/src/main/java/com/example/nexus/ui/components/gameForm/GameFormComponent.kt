package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

@Composable
fun GameFormComponent(
    onGameFormOpenChanged: (Boolean) -> Unit,
    getListEntry: () -> ListEntry,
    storeListEntry: (ListEntry) -> Unit,
    deleteListEntry: (ListEntry) -> Unit,
    getGameScore: () -> Int,
    setGameScore: (Int) -> Unit,
    getGameStatus: () -> String,
    setGameStatus: (String) -> Unit,
    getHours: () -> String,
    setHours: (String) -> Unit,
    getMinutes: () -> String,
    setMinutes: (String) -> Unit,
    onShowErrorPopupChanged: (Boolean) -> Unit,
    onShowDeleteWarningChanged: (Boolean) -> Unit,
    setCurrentListEntryMinutes: (Int) -> Unit,
    getEditOrAddGames: () -> String,
    getShowErrorPopup: () -> Boolean,
    getShowDeleteWarning: () -> Boolean,
    setEditOrAddGames: (String) -> Unit,
    focusManager: FocusManager,
    setPrefilledGame: (Boolean) -> Unit,
    setJustDeletedGame: () -> Unit,
    incrementTotalGamesCount: () -> Unit,
){
    val weight1 = 1.5f
    val weight2 = 3f

    Column(verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .fillMaxSize()) {
        Row(){
            IconButton(onClick = { onGameFormOpenChanged(false)
                                    focusManager.clearFocus()}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close game form",
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
            Text(text = getListEntry().title, fontSize = 20.sp)
        }

        //score input
        ScoreInput(getGameScore = getGameScore, setGameScore = setGameScore, weight1, weight2)

        //status input
        StatusInput(getGameStatus, setGameStatus, weight1, weight2)

        //hours input
        TimeInput(focusManager = focusManager, text = "Hours played: ", getTime = { getHours() },
            setTime = {hours -> setHours(hours)}, weight1, weight2)

        //minutes input
        TimeInput(focusManager = focusManager, text = "Minutes played: ", getTime = { getMinutes() },
            setTime = {minutes -> setMinutes(minutes)}, weight1, weight2)


        Row(modifier = Modifier.padding(5.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            GameSaveButton(focusManager = focusManager, getHours(), getMinutes(),
                {b:Boolean -> onShowErrorPopupChanged(b)},
                {m:Int -> setCurrentListEntryMinutes(m)},
                {l:ListEntry -> storeListEntry(l)},
                getListEntry,
                {b:Boolean -> onGameFormOpenChanged(b)},
                {incrementTotalGamesCount()},
                {getEditOrAddGames()}
            )

            GameDeleteButton(getEditOrAddGames()) { b: Boolean -> onShowDeleteWarningChanged(b) }
        }

        if(getShowErrorPopup()){
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = {onShowErrorPopupChanged(false)})
                    { Text(text = "OK", color=MaterialTheme.colors.onBackground) }
                },
                title = { Text(text = "wrong time") },
                text = { Text(text = "Please provide a valid time (integers only)") }
            )
        }

        if(getShowDeleteWarning()){
            AlertDialog(onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    deleteListEntry(getListEntry())
                    setJustDeletedGame()
                    setEditOrAddGames(NexusGameDetailViewModel.GameFormButton.ADD.value)
                    focusManager.clearFocus()
                    setPrefilledGame(false)
                    onShowDeleteWarningChanged(false)
                    onGameFormOpenChanged(false)
                }) {
                    Text(text = "Confirm", color=MaterialTheme.colors.onBackground)
                }
            },
                dismissButton = {
                    TextButton(onClick = {
                    onShowDeleteWarningChanged(false) }) {
                    Text(text = "Cancel", color=MaterialTheme.colors.onBackground)
                }},
            text = { Text(text = "Are you sure you want to delete this game from your list?")}
            )
        }
    }
}
