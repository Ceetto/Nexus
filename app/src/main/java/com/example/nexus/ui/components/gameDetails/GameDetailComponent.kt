package com.example.nexus.ui.components.gameDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.ui.components.HorizontalGamesListingComponent
import com.example.nexus.ui.components.LinkComponent
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import proto.Game
import java.util.*
import kotlin.math.roundToInt

@Composable
fun GameDetailComponent(
    vM: NexusGameDetailViewModel,
    game: Game,
    found: Boolean,
    onOpenGameDetails: (gameId: Long) -> Unit,
    focusManager: FocusManager
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = vM.isRefreshing()), onRefresh = { vM.onGetGameEvent() }) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Row() {
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
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
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
                    var region = date.region.toString().lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                        .replace("_", " ")
                    val e = region.indexOf(" ")+1
                    if(e > 0){
                        region = region.replaceRange(e, e+1,
                            region[e].toString().replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                            })
                    }
                    Text(
                        date.platform.abbreviation + ": " + date.human + ", " + region
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

            Column(Modifier.padding(5.dp)){
                var devs = ""
                var pubs = ""
                for (comp in game.involvedCompaniesList){
                    if(comp.developer)
                        devs += comp.company.name + ", "
                    if(comp.publisher)
                        pubs += comp.company.name + ", "
                }
                devs = devs.dropLast(2)
                pubs = pubs.dropLast(2)
                Row{
                    Text("Developer(s): ", fontWeight = FontWeight.Bold)
                    Text(devs)
                }
                Row{
                    Text("Publisher(s): ", fontWeight = FontWeight.Bold)
                    Text(pubs)
                }
            }

            Column(Modifier.padding(5.dp)) {
                Text(
                    "Links:",
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
                HorizontalGamesListingComponent(
                    list = relatedGames,
                    onOpenGameDetails = onOpenGameDetails,
                    focusManager = focusManager,
                    title = "Related Games:",
                    isFetching = false
                )
            }

            if (game.similarGamesCount > 0) {
                HorizontalGamesListingComponent(
                    list = game.similarGamesList,
                    onOpenGameDetails = onOpenGameDetails,
                    focusManager = focusManager,
                    title = "Recommendations:",
                    isFetching = false
                )
            }
        }
    }
}