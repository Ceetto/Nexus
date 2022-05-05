package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

//TODO
//alert toevoegen bij delete
//setEditOrAddGames werkt niet correct

@Composable
fun GameDeleteButton(
    vM: NexusGameDetailViewModel,
    focusManager: FocusManager
){
    if(vM.getEditOrAddGames() == NexusGameDetailViewModel.GameFormButton.EDIT.value){
        Button(modifier = Modifier.padding(10.dp),
            onClick = {
                vM.deleteListEntry(vM.getListEntry())
                vM.setEditOrAddGames(NexusGameDetailViewModel.GameFormButton.ADD.value)
                vM.onGameFormOpenChanged(false)
                focusManager.clearFocus()}) {
            Text(text = "delete")
        }
    }
}