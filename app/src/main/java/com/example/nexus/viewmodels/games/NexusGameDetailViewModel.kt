package com.example.nexus.viewmodels.games

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.data.repositories.gameData.GameDetailRepository
import com.example.nexus.ui.routes.list.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import proto.Game
import proto.Platform
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
    private val isRefreshing = mutableStateOf(false)

    private var gameFormOpen = mutableStateOf(false)
    private var showErrorPopup = mutableStateOf(false)
    private val currentListEntry = mutableStateOf(ListEntry(0, "", 0,
    0, "", "", false))
    private val minutes = mutableStateOf("0")
    private val hours = mutableStateOf("0")
    private val editOrAddGames = mutableStateOf(GameFormButton.ADD.value)
    private val showDeleteWarning = mutableStateOf(false)
    private val icon = mutableStateOf(Icons.Outlined.StarBorder)
    private val favoriteToggled = mutableStateOf(false)


    fun onGetGameEvent(){

        if(gameList.value.value.isNotEmpty() && gameList.value.value[0].id != gameId){
            gameList.value.value = emptyList()
        }
        viewModelScope.launch {
            try{
                isRefreshing.value = true
                repo.getGameById(gameId)
                isRefreshing.value = false
            } catch(e: Exception){

            }
        }
    }

    fun isRefreshing(): Boolean{
        return isRefreshing.value
    }

    fun getIcon(): ImageVector {
        return icon.value
    }

    fun setIcon(iv: ImageVector){
        icon.value = iv
    }

    fun toggleIcon(){
        if (icon.value == Icons.Outlined.StarBorder){
            icon.value = Icons.Outlined.Star
            favoriteToggled.value = true
        } else {
            icon.value = Icons.Outlined.StarBorder
            favoriteToggled.value = false
        }
    }

    fun getFavoriteToggled(): Boolean {
        return favoriteToggled.value
    }

    fun getEditOrAddGames(): String {
        return editOrAddGames.value
    }

    fun setEditOrAddGames(text: String){
        editOrAddGames.value = text
    }

    fun getGameList(): List<Game> {
        return gameList.value.value
    }

    fun setListEntry(entry: ListEntry){
        currentListEntry.value = entry
    }

    fun getListEntry(): ListEntry {
        return currentListEntry.value
    }

    fun setGameScore(score: Int){
        currentListEntry.value.score = score
    }

    fun getGameScore(): Int {
        return currentListEntry.value.score
    }

    fun setGameStatus(status: String){
        currentListEntry.value.status = status
    }

    fun getGameStatus(): String {
        return currentListEntry.value.status
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

    fun setFavorite(boolean: Boolean){
        currentListEntry.value.favorited = boolean
    }

    fun setCurrentListEntryMinutes(minutes: Int){
        currentListEntry.value.minutesPlayed = minutes
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

    fun onShowDeleteWarningChanged(boolean: Boolean){
        showDeleteWarning.value = boolean
    }

    fun getShowDeleteWarning(): Boolean {
        return showDeleteWarning.value
    }


//    fun getCoverWithId(id: Long) = repo.getCoverWithId(id)
//    fun getPlatforms(ids: MutableList<Platform>) = repo.getPlatforms(ids)

    fun storeListEntry(entry: ListEntry) = viewModelScope.launch { listRepository.storeListEntry(entry) }

    fun deleteListEntry(entry: ListEntry) = viewModelScope.launch { listRepository.deleteListEntry(entry) }

    val allGames: StateFlow<List<ListEntry>> by lazy {
        listRepository.allGames.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    enum class GameFormButton(val value: String){
        ADD("Add game"),
        EDIT("Edit game")
    }
}