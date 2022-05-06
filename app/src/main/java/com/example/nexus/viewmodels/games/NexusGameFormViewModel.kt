package com.example.nexus.viewmodels.games

import androidx.compose.runtime.mutableStateOf
import com.example.nexus.ui.routes.ListCategory

class NexusGameFormViewModel {
    private val gameScore = mutableStateOf(0)
    private val gameStatus = mutableStateOf(ListCategory.PLAYING.value)

    fun setGameScore(score: Int){
        gameScore.value = score
    }

    fun getGameScore(): Int {
        return gameScore.value
    }

    fun setGameStatus(status: String){
        gameStatus.value = status
    }

    fun getGameStatus(): String {
        return gameStatus.value
    }
}