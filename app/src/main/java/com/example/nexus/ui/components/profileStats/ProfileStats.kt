package com.example.nexus.ui.components.profileStats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.User
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Playing
import com.example.nexus.viewmodels.profile.NexusProfileViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToLong

@Composable
fun ProfileStats(
//    vM: NexusProfileViewModel,
    getCategoryByName : (String) -> StateFlow<List<ListEntry>>,
    onOpenList: (userId: String) -> Unit,
    getUserId: String
) {
    val total by getCategoryByName(ListCategory.ALL.value).collectAsState();
    val playing by getCategoryByName(ListCategory.PLAYING.value).collectAsState();
    val completed by getCategoryByName(ListCategory.COMPLETED.value).collectAsState()
    val dropped by getCategoryByName(ListCategory.DROPPED.value).collectAsState()
    val planned by getCategoryByName(ListCategory.PLANNED.value).collectAsState()
    var minutes = 0.0
    var tempScore = 0;
    total.forEach {minutes += it.minutesPlayed.toDouble()}

    val totalWithScore : List<ListEntry> = total.filter { it.score != 0 }
    totalWithScore.forEach {tempScore += it.score}

    var meanScore = 0.0;
    if (totalWithScore.isNotEmpty()) {
        meanScore = ((tempScore.toDouble() / totalWithScore.size) * 100.0).roundToLong() / 100.0
    }

    val hours : Double = ((minutes / 60) * 10.0).roundToLong() /10.0
    val days : Double = ((hours / 24) * 10.0).roundToLong() /10.0

    Column(modifier = Modifier
        .padding(25.dp)
        .fillMaxWidth()) {
        
        Button(onClick = {onOpenList(getUserId);}){
            Text(text = "List")
        }

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
