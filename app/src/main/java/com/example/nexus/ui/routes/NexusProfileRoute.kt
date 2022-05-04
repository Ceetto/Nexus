package com.example.nexus.ui.routes

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.R;
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusListViewModel

import com.example.nexus.viewmodels.NexusProfileViewModel
import kotlin.math.roundToLong

@Composable
fun NexusProfileRoute(
    vM: NexusProfileViewModel,
    navController: NavHostController,
    vMList: NexusListViewModel
){
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true) }
    ) {
        ProfileScreen(
            vMList
        )
    }

}


@Composable
fun ProfileScreen(vMList: NexusListViewModel){
    Scaffold( ) {
        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RectangleShape)
                    .background(Color.Red)
            ) {
            }
            ProfilePicture(vMList)
        }
    }

}

@Composable
fun ProfileStats(vMList: NexusListViewModel) {
//    val total by vMList.allGames.collectAsState()
//    val playing by vMList.playing.collectAsState()
//    val completed by vMList.completed.collectAsState()
//
//    var minutes = 0.0
//    total.forEach {minutes += it.minutesPlayed.toDouble()}
//
//    val hours : Double = ((minutes / 60) * 10.0).roundToLong() /10.0
//    val days : Double = ((hours / 24) * 10.0).roundToLong() /10.0
//
//
//    println("minutes played : $minutes")
//    println("hours played : $hours")
//    println("days played : $days")
//    Column(modifier = Modifier
//        .padding(40.dp)
//        .fillMaxWidth()) {
//
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min)) {
//            Text(text = "Total Games: ${total.size}", modifier = Modifier
//                .weight(2f, true)
//                .padding(10.dp))
//            Divider(
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//            )
//            Text(text = "Days Played: $days", modifier = Modifier
//                .weight(2f, true)
//                .padding(10.dp))
//
//        }
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min)) {
//
//            Text(text = "Completed: ${completed.size}", modifier = Modifier
//                .weight(2f, true)
//                .padding(10.dp))
//            Divider(
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//            )
//            Text(text = "Hours Played: $hours", modifier = Modifier
//                .weight(2f, true)
//                .padding(10.dp))
//        }
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min)) {
//            Text(text = "Playing: ${playing.size}", modifier = Modifier
//                .weight(2f, true)
//                .padding(10.dp))
//            Divider(
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//            )
//            Text(text = "", modifier = Modifier.weight(2f, true))
//
//        }
//    }
}

@Composable
fun ProfilePicture(vMList: NexusListViewModel){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberAsyncImagePainter(R.drawable.test),
                contentDescription = "profile_picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Text(text = "TestName", modifier = Modifier.padding(10.dp))

            ProfileStats(vMList)
    }
}