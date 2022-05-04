package com.example.nexus.ui.routes.search

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.SearchBarComponent
import com.example.nexus.ui.components.SearchResultComponent
import com.example.nexus.ui.theme.NexusBlue
import com.example.nexus.ui.theme.NexusGray
import com.example.nexus.ui.theme.NexusLightGray
import com.example.nexus.viewmodels.games.NexusSearchViewModel
import proto.Game


@ExperimentalComposeUiApi
@Composable
fun NexusSearchRoute(
    vM: NexusSearchViewModel,
    navController: NavHostController,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false) },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus()})}
    ) {
        Column (){
            Row{
                SearchBarComponent(vM)
            }

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                vM.getGameList().forEach { game ->
                    SearchResultComponent(vM = vM, game = game, onClick = onOpenGameDetails, focusManager)
                }
            }
        }
    }


}