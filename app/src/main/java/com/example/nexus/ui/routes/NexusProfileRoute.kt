package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.R;
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.theme.Playing
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.viewmodels.NexusProfileViewModel
import kotlin.math.roundToLong

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusProfileRoute(
    vM: NexusProfileViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit
){
   InitProfile(vM = vM, navController = navController, onOpenGameDetails = onOpenGameDetails)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InitProfile(vM: NexusProfileViewModel,
                    navController: NavHostController,
                    onOpenGameDetails: (gameId: Long) -> Unit){

    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        ProfileScreen(
            vM, onOpenGameDetails
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(vM: NexusProfileViewModel, onOpenGameDetails: (gameId: Long) -> Unit){
    Scaffold( ) {
        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(
                //TODO dit veranderen voor profile background uit user data
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RectangleShape)
                    .background(Color.Red)
            ) {
            }
            ProfilePicture(vM, onOpenGameDetails)

        }
    }

}

@Composable
fun ProfileStats(vM: NexusProfileViewModel) {
    val total by vM.getCategoryByName(ListCategory.ALL.value).collectAsState();
    val playing by vM.getCategoryByName(ListCategory.PLAYING.value).collectAsState();
    val completed by vM.getCategoryByName(ListCategory.COMPLETED.value).collectAsState()
    val dropped by vM.getCategoryByName(ListCategory.DROPPED.value).collectAsState()
    val planned by vM.getCategoryByName(ListCategory.PLANNED.value).collectAsState()
    var minutes = 0.0
    var tempScore = 0;
    println(total.size)
    total.forEach {minutes += it.minutesPlayed.toDouble()}

    val totalWithScore : List<ListEntry> = total.filter { it.score != 0 }
    totalWithScore.forEach {tempScore += it.score}

    println(tempScore)
    println(totalWithScore.size)
    var meanScore : Double = 0.0;
    if (totalWithScore.isNotEmpty()) {
        meanScore = ((tempScore / totalWithScore.size) * 10.0).roundToLong() / 10.0
    }

    val hours : Double = ((minutes / 60) * 10.0).roundToLong() /10.0
    val days : Double = ((hours / 24) * 10.0).roundToLong() /10.0
    //TODO add mean score
    Column(modifier = Modifier
        .padding(25.dp)
        .fillMaxWidth()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ){
            Text(text = "Days Played: ", modifier = Modifier
                .weight(3f, true)
                .padding(10.dp))
            Text(text = "$days", modifier = Modifier
                .weight(1f, true)
                .padding(10.dp))
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ){
            Text(text = "Mean Score: ", modifier = Modifier
                .weight(3f, true)
                .padding(10.dp))
            Text(text = "$meanScore", modifier = Modifier
                .weight(1f, true)
                .padding(10.dp))
        }


        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .width(1.dp)
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)) {
            Text(text = "Total Games: ", modifier = Modifier
                .weight(3f, true)
                .padding(10.dp))
            Text(text = "${total.size}", modifier = Modifier
                .weight(1f, true)
                .padding(10.dp))

        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)) {

            Box(modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Completed)
                .align(Alignment.CenterVertically))
            Text(text = "Completed: ", modifier = Modifier
                .padding(10.dp)
                .weight(3f, true))

            Text(
                text = "${completed.size}", modifier = Modifier
                    .padding(10.dp)
                    .weight(1f, true)
            )

        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)) {
            Box(modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Playing)
                .align(Alignment.CenterVertically))

            Text(text = "Playing:", modifier = Modifier
                .weight(3f, true)
                .padding(10.dp))

            Text(text = "${playing.size}",
                modifier = Modifier
                    .weight(1f, true)
                    .padding(10.dp))

        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)) {
            Box(modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Planned)
                .align(Alignment.CenterVertically))

            Text(text = "Planned:", modifier = Modifier
                .weight(3f, true)
                .padding(10.dp))
            Text(text = "${planned.size}",
                modifier = Modifier
                    .weight(1f, true)
                    .padding(10.dp))

        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)) {
            Box(modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Dropped)
                .align(Alignment.CenterVertically))

            Text(text = "Dropped:", modifier = Modifier
                .weight(3f, true)
                .padding(10.dp))
            Text(text = "${dropped.size}",
                modifier = Modifier
                    .weight(1f, true)
                    .padding(10.dp))

        }
    }
}

@Composable
fun ProfilePicture(vM: NexusProfileViewModel, onOpenGameDetails: (gameId: Long) -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        //TODO image halen uit user data
            Image(
                painter = rememberAsyncImagePainter(R.drawable.test),
                contentDescription = "profile_picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Text(text = "TestName", modifier = Modifier.padding(10.dp))

            ProfileStats(vM)
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
            horizontalAlignment = Alignment.Start) {
            FavoriteList(vM, onOpenGameDetails)
        }
    }
}

@Composable
fun FavoriteList(vM: NexusProfileViewModel, onOpenGameDetails: (gameId: Long) -> Unit){
    val favorites by vM.favorites.collectAsState();
    Column(
        Modifier.padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
    ){
        Text("Favorites:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(Modifier.horizontalScroll(rememberScrollState())){
                favorites.forEach { entry -> Row{
                    FavoriteListComponent(entry, onOpenGameDetails, LocalFocusManager.current)
                    }
                }
            }
    }
}

@Composable
fun FavoriteListComponent(entry: ListEntry, onOpenGameDetails: (gameId: Long) -> Unit, focusManager: FocusManager) {
    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                onOpenGameDetails(entry.gameId)
                focusManager.clearFocus()
            })
        }
        .width(127.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(entry.coverUrl),
            contentDescription = "cover image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(127.dp, 150.dp)
                .padding(end = 5.dp)
        )
        Text(entry.title)
    }
}

