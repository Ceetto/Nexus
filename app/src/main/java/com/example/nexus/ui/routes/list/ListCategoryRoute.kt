package com.example.nexus.ui.routes.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus.data.db.ListEntity
import com.example.nexus.viewmodels.NexusListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Playing

@Composable
fun ListCategoryRoute(
    category: ListCategory,
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
//    Text(
//        text = category.value
//    )
    //val games by vM.getCategory(category.value).collectAsState()
    //val games by vM.allGames.collectAsState()
    //ListCategoryScreen(games)
    vM.writeTest()
    when(category){
        ListCategory.ALL -> AllScreen(vM, onOpenGameDetails)
        ListCategory.PLAYING -> PlayingScreen(vM, onOpenGameDetails)
        ListCategory.COMPLETED -> CompletedScreen(vM, onOpenGameDetails)
        ListCategory.PLANNED -> PlannedScreen(vM, onOpenGameDetails)
        ListCategory.DROPPED -> DroppedScreen(vM, onOpenGameDetails)
    }
}

@Composable
fun AllScreen(
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
){
    val games by vM.allGames.collectAsState()
    ListColumn(games, onOpenGameDetails)
}

@Composable
fun PlayingScreen(
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
){
    val games by vM.playing.collectAsState()
    ListColumn(games, onOpenGameDetails)
}

@Composable
fun CompletedScreen(
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
){
    val games by vM.completed.collectAsState()
    ListColumn(games, onOpenGameDetails)
}

@Composable
fun PlannedScreen(
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
){
    val games by vM.planned.collectAsState()
    ListColumn(games, onOpenGameDetails)
}

@Composable
fun DroppedScreen(
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
){
    val games by vM.dropped.collectAsState()
    ListColumn(games, onOpenGameDetails)
}


//@Composable
//fun ListCategoryScreen(
//    games: List<ListEntity>
//){
//    ListColumn(games)
//}

@Composable
fun ListColumn(
    games: List<ListEntity>,
    onOpenGameDetails : (gameId: Long) -> Unit
){
    LazyColumn(
        //modifier = Modifier.verticalScroll(rememberScrollState()).height(100.dp)
    ){
        items(games) {game ->
            ListItem(game = game, onOpenGameDetails)
        }
    }
}

@Composable
fun ListItem(
    game: ListEntity,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
    Surface(
        modifier = Modifier.padding(vertical = 4.dp).pointerInput(Unit){
            detectTapGestures(onTap = { onOpenGameDetails(game.gameId) })
        }
    ) {
        Row(){
            Image(painter = rememberAsyncImagePainter(game.coverUrl), contentDescription = "cover",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(120.dp))

            Column() {
                Text(text = game.title,
                    fontSize = 25.sp)

                Row() {
                    Surface(
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ){
                        var timePlayed = ""
                        timePlayed = if (game.status == ListCategory.PLANNED.value){
                            ListCategory.PLANNED.value
                        } else {
                            val minutes = game.minutesPlayed.mod(60)
                            val hours = (game.minutesPlayed - minutes)/60
                            "${hours}h ${minutes}m"
                        }
                        ListCategoryColors[game.status]?.let {
                            Text(text = timePlayed,
                                Modifier.padding(end = 30.dp),
                                color = it
                            )
                        }
                    }
                    if (game.score != 0){
                        Icon(imageVector = Icons.Default.Star, contentDescription = "starIcon")
                        Text(text = game.score.toString())
                    } else {
                        Text(text = "No score")
                    }

                }
            }
        }

    }
}

val ListCategoryColors = mapOf(ListCategory.PLAYING.value to Playing,
                                ListCategory.COMPLETED.value to Completed,
                                ListCategory.DROPPED.value to Dropped,
                                ListCategory.PLANNED.value to Planned)

