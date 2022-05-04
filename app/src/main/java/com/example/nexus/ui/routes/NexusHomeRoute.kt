 package com.example.nexus.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusHomeViewModel


 @ExperimentalComposeUiApi
@Composable
fun NexusHomeRoute(
    vM: NexusHomeViewModel,
    navController: NavHostController
) {
     Scaffold(
         topBar = { NexusTopBar(navController = navController, canPop = false) }
     ) {
//         Text(text="home")
         Column {
             vM.getBestGames().forEach { game -> Row{
                 Text(game.name)
                 Text(game.rating.toString())
             } }
         }
     }


}
