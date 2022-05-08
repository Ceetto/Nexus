package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

@Composable
fun GameDeleteButton(
//    vM: NexusGameDetailViewModel,
    getEditOrAddGames : String,
    onShowDeleteWarningChanged : (b : Boolean) -> Unit
){
    if(getEditOrAddGames == NexusGameDetailViewModel.GameFormButton.EDIT.value){
        Button(modifier = Modifier.padding(10.dp),
            onClick = {
                onShowDeleteWarningChanged(true)}) {
            Text(text = "delete")
        }
    }
}