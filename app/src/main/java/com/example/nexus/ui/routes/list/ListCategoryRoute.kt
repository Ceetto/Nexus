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
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Playing

@Composable
fun ListCategoryRoute(
    category: ListCategory,
    vM: NexusListViewModel
) {
//    Text(
//        text = category.value
//    )
    //val games by vM.getCategory(category.value).collectAsState()
    //val games by vM.allGames.collectAsState()
    //ListCategoryScreen(games)
    when(category){
        ListCategory.ALL -> AllScreen(vM)
        ListCategory.PLAYING -> PlayingScreen(vM)
        ListCategory.COMPLETED -> CompletedScreen(vM)
        ListCategory.PLANNED -> PlannedScreen(vM)
        ListCategory.DROPPED -> DroppedScreen(vM)
    }
}

@Composable
fun AllScreen(
    vM: NexusListViewModel
){
    val games by vM.allGames.collectAsState()
    ListColumn(games)
}

@Composable
fun PlayingScreen(
    vM: NexusListViewModel
){
    val games by vM.playing.collectAsState()
    ListColumn(games)
}

@Composable
fun CompletedScreen(
    vM: NexusListViewModel
){
    val games by vM.completed.collectAsState()
    ListColumn(games)
}

@Composable
fun PlannedScreen(
    vM: NexusListViewModel
){
    val games by vM.planned.collectAsState()
    ListColumn(games)
}

@Composable
fun DroppedScreen(
    vM: NexusListViewModel
){
    val games by vM.dropped.collectAsState()
    ListColumn(games)
}


@Composable
fun ListCategoryScreen(
    games: List<ListEntity>
){
    ListColumn(games)
}

@Composable
fun ListColumn(
    games: List<ListEntity>
){
    LazyColumn(
//        modifier = Modifier
//            .padding(16.dp)
    ){
        items(games) {game ->
            ListItem(game = game)
        }
    }
}

@Composable
fun ListItem(
    game: ListEntity
) {
    Surface(
        modifier = Modifier.padding(vertical = 4.dp)
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

                    Icon(imageVector = Icons.Default.Star, contentDescription = "starIcon")
                    Text(text = game.score.toString())
                }
            }
        }

    }
}

val ListCategoryColors = mapOf(ListCategory.PLAYING.value to Playing,
                                ListCategory.COMPLETED.value to Completed,
                                ListCategory.DROPPED.value to Dropped,
                                ListCategory.PLANNED.value to Planned)

