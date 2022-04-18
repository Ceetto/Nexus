package com.example.nexus.viewmodels.games

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.repositories.gameData.GameDetailRepository
import com.example.nexus.data.web.ListEntry
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

    var gameFormOpen = mutableStateOf(false)

    fun onGameFormOpenChanged(boolean: Boolean){
        gameFormOpen.value = boolean
    }

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

    fun storeListEntry(entry: ListEntry) = viewModelScope.launch { repo.storeListEntry(entry) }

    fun deleteListEntry(entity: ListEntity) = viewModelScope.launch { repo.deleteListEntry(entity) }
}