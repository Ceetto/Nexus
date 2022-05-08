package com.example.nexus.ui.routes.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.components.AgeConfirmComponent
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.gameDetails.GameDetailComponent
import com.example.nexus.ui.components.gameForm.GameFormComponent
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

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
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
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
                GameDetailComponent(game, found, onOpenGameDetails, focusManager,
                        vM.isRefreshing(), {vM.onGetGameEvent()}, {b: Boolean -> vM.onGameFormOpenChanged(b)},
                        vM.getEditOrAddGames(),
                        onFavourite = {
                            vM.toggleIcon()
                            vM.setFavorite(vM.getFavoriteToggled())
                            vM.storeListEntry(vM.getListEntry())
                        },
                        vM.getIcon()
                    )
            } else {
                if(vM.getGameFormOpen()){
                    GameFormComponent(vM)
                } else{
                    AgeConfirmComponent({b : Boolean -> vM.onAgeVerifOpenChange(b)}, navController)
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