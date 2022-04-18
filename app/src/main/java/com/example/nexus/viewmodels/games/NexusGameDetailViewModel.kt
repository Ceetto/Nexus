package com.example.nexus.viewmodels.games

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.repositories.gameData.GameDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import proto.Game
import proto.Platform
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NexusGameDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: GameDetailRepository
) : ViewModel(){
    var gameList = repo.gameList
    var platformList = repo.gamePlatforms
    val gameId: Long = savedStateHandle["gameId"]!!

    fun onGetGameEvent(){
        viewModelScope.launch {
            try{
                repo.getGameById(gameId)
                repo.getCovers()
                repo.getPlatforms()
                repo.getPlatforms(gameList.value[0].platformsList)
            } catch(e: Exception){

            }
        }
    }
    fun getCoverWithId(id: Long) = repo.getCoverWithId(id)
    fun getPlatforms(ids: MutableList<Platform>) = repo.getPlatforms(ids)
}