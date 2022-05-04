package com.example.nexus.viewmodels.games

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.data.repositories.gameData.GameDetailRepository
import com.example.nexus.ui.routes.list.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import proto.Game
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NexusGameDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: GameDetailRepository,
    private val listRepository: ListRepository
) : ViewModel(){
    private var gameList = repo.gameList
    private val gameId: Long = savedStateHandle["gameId"]!!

    private var gameFormOpen = mutableStateOf(false)
    private var showErrorPopup = mutableStateOf(false)
    private val gameScore = mutableStateOf(0)
    private val gameStatus = mutableStateOf(ListCategory.PLAYING.value)
    private val hours = mutableStateOf("0")
    private val minutes = mutableStateOf("0")

    fun onGetGameEvent(){
        if(gameList.value.isNotEmpty() && gameList.value[0].id != gameId){
            gameList.value = emptyList()
        }
        viewModelScope.launch {
            try{
                repo.getGameById(gameId)
            } catch(e: Exception){

            }
        }
    }

    fun getGameList(): List<Game> {
        return gameList.value
    }

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

    fun setHours(hours: String){
        this.hours.value = hours
    }

    fun getHours(): String {
        return hours.value
    }

    fun setMinutes(minutes: String){
        this.minutes.value = minutes
    }

    fun getMinutes(): String {
        return minutes.value
    }

    fun getGameFormOpen(): Boolean {
        return gameFormOpen.value
    }

    fun getShowErrorPopup(): Boolean {
        return showErrorPopup.value
    }

    fun onGameFormOpenChanged(boolean: Boolean){
        gameFormOpen.value = boolean
    }

    fun onShowErrorPopupChanged(boolean: Boolean){
        showErrorPopup.value = boolean
    }


//    fun getCoverWithId(id: Long) = repo.getCoverWithId(id)
//    fun getPlatforms(ids: MutableList<Platform>) = repo.getPlatforms(ids)

    fun storeListEntry(entry: ListEntry) = viewModelScope.launch { listRepository.storeListEntry(entry) }

    //fun deleteListEntry(entity: ListEntity) = viewModelScope.launch { listRepository.deleteListEntry(entity) }
}