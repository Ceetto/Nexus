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
            if(vM.getTotalGamesCount() >= vM.getOldTotalGameCount()){
                vM.setTotalGamesCount(games.size)
                vM.setOldTotalGameCount(games.size)
            }

            if(!vM.getGameFormOpen() && !vM.ageVerifOpen()){
                vM.prefillGame(game, games)
                GameDetailComponent(game, {vM.isPrefilledGame()}, onOpenGameDetails, focusManager,
                        vM.isRefreshing(), {vM.onGetGameEvent()}, {b: Boolean -> vM.onGameFormOpenChanged(b)},
                        {vM.getEditOrAddGames()},
                        onFavourite = {
                            vM.toggleIcon()
                            vM.setFavorite(vM.getFavoriteToggled())
                            vM.storeListEntry(vM.getListEntry())
                        },
                        vM.getIcon(),
                        { s : String -> vM.getLinkIcon(s)}
                    )
            } else {
                if(vM.getGameFormOpen()){
                    vM.prefillGame(game, games)
                    GameFormComponent(
                        onGameFormOpenChanged = {b -> vM.onGameFormOpenChanged(b)},
                        getListEntry = {vM.getListEntry()},
                        storeListEntry = {e -> vM.storeListEntry(e)},
                        deleteListEntry = {e-> vM.deleteListEntry(e)},
                        getGameScore = {vM.getGameScore()},
                        setGameScore = {s -> vM.setGameScore(s)},
                        getGameStatus = {vM.getGameStatus()},
                        setGameStatus = {s -> vM.setGameStatus(s)},
                        getHours = {vM.getHours()},
                        setHours = {h -> vM.setHours(h)},
                        getMinutes = {vM.getMinutes()},
                        setMinutes = {m -> vM.setMinutes(m)},
                        onShowErrorPopupChanged = {b -> vM.onShowErrorPopupChanged(b)},
                        onShowDeleteWarningChanged = {b -> vM.onShowDeleteWarningChanged(b)},
                        setCurrentListEntryMinutes = {m -> vM.setCurrentListEntryMinutes(m)},
                        getEditOrAddGames = {vM.getEditOrAddGames()},
                        getShowErrorPopup = {vM.getShowErrorPopup()},
                        getShowDeleteWarning = {vM.getShowDeleteWarning()},
                        setEditOrAddGames = {e -> vM.setEditOrAddGames(e)},
                        focusManager = focusManager,
                        setPrefilledGame = {b: Boolean -> vM.setPrefilledGame(b)},
                        setJustDeletedGame = {vM.setJustDeletedGame()},
                        incrementTotalGamesCount = {vM.incrementTotalGamesCount()})
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