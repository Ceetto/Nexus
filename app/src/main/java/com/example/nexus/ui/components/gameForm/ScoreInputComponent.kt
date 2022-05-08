package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoreInput(
    getGameScore: () -> Int,
    setGameScore: (Int) -> Unit,
    weight1: Float,
    weight2: Float
){
    Row(modifier = Modifier.padding(5.dp)) {
        Text(text = "Your score: ", modifier = Modifier.padding(top = 10.dp).weight(weight1, true))
        var expanded by remember { mutableStateOf(false) }
        val scoreText = if (getGameScore() == 0){
            "No score"
        } else {
            getGameScore().toString()
        }
        var text by remember { mutableStateOf(scoreText) }
        OutlinedButton(onClick = { expanded = !expanded }, modifier = Modifier.weight(weight2, true)) {
            Text(text = text, color= MaterialTheme.colors.onBackground)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { expanded = false
                text = "No score"
                setGameScore(0)}) {
                Text(text = "No score")
            }
            listOf(1,2,3,4,5,6,7,8,9,10).forEach { score ->
                DropdownMenuItem(onClick = { expanded = false
                    text = score.toString()
                    setGameScore(score)}) {
                    Text(text = score.toString())
                }
            }
        }
    }
}