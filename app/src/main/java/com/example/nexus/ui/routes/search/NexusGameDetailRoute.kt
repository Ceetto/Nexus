package com.example.nexus.ui.routes.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.web.ListEntry
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import kotlin.math.roundToInt

@Composable
fun NexusGameDetailRoute(
    vM: NexusGameDetailViewModel
){
    vM.onGetGameEvent()
    if(vM.gameList.value.isNotEmpty()) {
        val game = vM.gameList.value[0]
        Column {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(vM.getCoverWithId(game.cover.id)
                        ?.let {
                            imageBuilder(
                                it.imageId,
                                ImageSize.COVER_BIG,
                                ImageType.JPEG
                            )
                        }),
                    contentDescription = "cover image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(0.40f).padding(5.dp)
                )
                Column {
                    Text(game.name)
                    Row {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "star icon")
                        if (game.ratingCount > 0) {
                            Text(
                                text = " " + ((game.rating * 10).roundToInt()
                                    .toDouble() / 100).toString()
                            )
                        } else {
                            Text(text = " N/A")
                        }
                    }
                    Text(text = game.ratingCount.toString() + " votes", fontSize = 15.sp)

                }
            }
            Text("platforms: " + vM.platformList.value)
            Text(game.summary)
            val listEntry = ListEntry(game.id, game.name, 10, 42, ListCategory.PLAYING.value,
                vM.getCoverWithId(game.cover.id)
                    ?.let {
                        imageBuilder(
                            it.imageId,
                            ImageSize.COVER_BIG,
                            ImageType.JPEG
                        )
                    })
            Button(onClick = {vM.storeListEntry(listEntry)}){
                Text(text = "Add game")
            }
        }
    } else {
        Text("Loading game...")
    }
}