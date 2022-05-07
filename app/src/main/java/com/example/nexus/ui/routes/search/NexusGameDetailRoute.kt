package com.example.nexus.ui.routes.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.components.AgeConfirmComponent
import com.example.nexus.ui.components.gameForm.GameFormComponent
import com.example.nexus.ui.components.HorizontalGamesListingComponent
import com.example.nexus.ui.components.LinkComponent
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.gameDetails.GameDetailComponent
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import proto.Game
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusGameDetailRoute(
    vM: NexusGameDetailViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current
){
    LaunchedEffect(Unit){
        vM.onGetGameEvent()
    }
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true) }
    ) {
        if(vM.getGameList().isNotEmpty()) {
            val game = vM.getGameList()[0]
            //checks if the game is already in your list
            val games by vM.allGames.collectAsState()
            var i = 0
            var found = false
            while (i < games.size && !found){
                if(games[i].gameId == game.id){
                    vM.setListEntry(games[i])
                    vM.setMinutes(vM.getListEntry().minutesPlayed.mod(60).toString())
                    vM.setHours(((vM.getListEntry().minutesPlayed - vM.getMinutes().toInt())/60).toString())
                    vM.setEditOrAddGames(NexusGameDetailViewModel.GameFormButton.EDIT.value)
                    if(games[i].favorited){
                        vM.setIcon(Icons.Outlined.Star)
                    } else {
                        vM.setIcon(Icons.Outlined.StarBorder)
                    }
                    found = true
                }
                i++
            }

            //fills in the current ListEntry in case the game was not found in your list
            if (!found){
                vM.setListEntry(ListEntry(game.id, game.name, 0, 0, ListCategory.PLAYING.value,
                game.cover?.let {
                    imageBuilder(
                        it.imageId,
                        ImageSize.COVER_BIG,
                        ImageType.JPEG
                    )}, false, game.firstReleaseDate.seconds))
                vM.setEditOrAddGames(NexusGameDetailViewModel.GameFormButton.ADD.value)
            }

            if(!vM.getGameFormOpen() && !vM.ageVerifOpen()){
                GameDetailComponent(vM, game, found, onOpenGameDetails, focusManager)
            } else {
                if(vM.getGameFormOpen()){
                    GameFormComponent(vM)
                } else{
                    AgeConfirmComponent(vM, navController)
                }

            }
        } else {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator()
            }
        }
    }
}