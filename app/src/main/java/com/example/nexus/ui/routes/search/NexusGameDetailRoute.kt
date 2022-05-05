package com.example.nexus.ui.routes.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.example.nexus.ui.components.GameFormComponent
import com.example.nexus.ui.components.HomePageCategoryComponent
import com.example.nexus.ui.components.LinkComponent
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import proto.Game
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
                    )}, false))
                vM.setEditOrAddGames(NexusGameDetailViewModel.GameFormButton.ADD.value)
            }

            if(!vM.getGameFormOpen()){
                SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = vM.isRefreshing()), onRefresh = { vM.onGetGameEvent() }) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Row(Modifier.height(200.dp)) {
                            Column{
                                if(game.cover.imageId.isEmpty()){
                                    Text("no image")
                                }
                                Image(
                                    painter = rememberAsyncImagePainter(game.cover
                                        ?.let {
                                            imageBuilder(
                                                it.imageId,
                                                ImageSize.COVER_BIG,
                                                ImageType.JPEG
                                            )
                                        }),
                                    contentDescription = "cover image",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(169.dp, 200.dp)
                                        .padding(5.dp)
                                )
                            }


                            Column {
                                Text(game.name, fontSize = 20.sp)
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "star icon"
                                    )
                                    if (game.ratingCount > 0) {
                                        Text(
                                            text = " " + ((game.rating * 10).roundToInt()
                                                .toDouble() / 100).toString()
                                        )
                                    } else {
                                        Text(text = " N/A")
                                    }
                                }
                                Text(
                                    text = game.ratingCount.toString() + " votes",
                                    fontSize = 10.sp
                                )

                                var platforms = ""
                                for (platform in game.platformsList) {
                                    platforms += platform.abbreviation + ", "
                                }
                                platforms = platforms.dropLast(2)
                                Row {
                                    Text("platforms: $platforms")
                                }

                                var genres = ""
                                for (genre in game.genresList) {
                                    genres += genre.name + ", "
                                }
                                genres = genres.dropLast(2)
                                Row {
                                    Text("genres: $genres")
                                }

                                //edit/add game button + favorite toggle
                                Row(modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.Center){
                                    Button(onClick = { vM.onGameFormOpenChanged(true) }) {
                                        Text(text = vM.getEditOrAddGames())
                                    }
                                    if (found){
                                        IconButton(onClick = {
                                            vM.toggleIcon()
                                            vM.setFavorite(vM.getFavoriteToggled())
                                            vM.storeListEntry(vM.getListEntry())
                                            }, modifier = Modifier.padding(start = 5.dp)) {
                                            Icon(vM.getIcon() , contentDescription = "favorite",
                                                tint = Color.Yellow, modifier = Modifier.size(40.dp))
                                        }
                                    }
                                }
                            }
                        }

                        Column(Modifier.padding(5.dp)) {
                            Text(
                                "Summary:",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            Text(game.summary, Modifier.padding(bottom = 10.dp))
                            if (game.summary.isEmpty()) {
                                Text("No summary available")
                            }
                        }

                        Text(
                            "Media:",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        if (game.screenshotsCount == 0) {
                            Text("no media available", Modifier.padding(bottom = 10.dp, start = 5.dp))
                        } else {
                            Row(
                                Modifier
                                    .horizontalScroll(rememberScrollState())
                                    .height(300.dp)
                                    .padding(bottom = 10.dp)
                            ) {
                                for (screenshot in game.screenshotsList) {
                                    Image(
                                        painter = rememberAsyncImagePainter(screenshot?.let {
                                            imageBuilder(
                                                it.imageId, ImageSize.SCREENSHOT_BIG,
                                                ImageType.JPEG
                                            )
                                        }),
                                        contentDescription = "cover image",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(
                                                (screenshot.width / screenshot.height * 500).dp,
                                                500.dp
                                            )
                                            .padding(horizontal = 5.dp)
                                    )
                                }
                            }
                        }


                        Column(Modifier.padding(bottom = 10.dp, start = 5.dp, end = 5.dp)) {
                            Text(
                                "Release dates:",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            for (date in game.releaseDatesList) {
                                Text(
                                    date.platform.abbreviation + ": " + date.human + ", " + date.region.toString()
                                        .lowercase()
                                )
                            }
                        }

                        Column(Modifier.padding(5.dp)) {
                            Text(
                                "Storyline:",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            if (game.storyline.isEmpty()) {
                                Text("No storyline available", Modifier.padding(bottom = 10.dp))
                            } else {
                                Text(game.storyline, Modifier.padding(bottom = 10.dp))
                            }
                        }

                        Column(Modifier.padding(5.dp)) {
                            Text(
                                "Websites:",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            for (website in game.websitesList) {
                                LinkComponent(website)
                            }
                        }
//                    arrayListOf(game.parentGame) +
                        val relatedGames = emptyList<Game>().toMutableList()
                        if (game.parentGame.name.isNotEmpty()) {
                            relatedGames += game.parentGame
                        }
                        for (franchise in game.franchisesList) {
                            relatedGames += franchise.gamesList
                        }
                        relatedGames += game.franchise.gamesList + game.remakesList + game.remastersList +
                                game.dlcsList + game.expandedGamesList + game.expansionsList
                        if (relatedGames.isNotEmpty()) {
                            HomePageCategoryComponent(
                                list = relatedGames,
                                onOpenGameDetails = onOpenGameDetails,
                                focusManager = focusManager,
                                title = "Related Games:",
                                isFetching = false
                            )
                        }

                        if (game.similarGamesCount > 0) {
                            HomePageCategoryComponent(
                                list = game.similarGamesList,
                                onOpenGameDetails = onOpenGameDetails,
                                focusManager = focusManager,
                                title = "Recommendations:",
                                isFetching = false
                            )
                        }
                    }
                }
            } else {
                GameFormComponent(vM)
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