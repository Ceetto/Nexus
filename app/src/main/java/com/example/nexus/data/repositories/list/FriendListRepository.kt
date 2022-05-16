package com.example.nexus.data.repositories.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.SortOptions
import com.example.nexus.data.db.list.FirebaseFriendListDao
import com.example.nexus.ui.routes.lists.ListCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FriendListRepository @Inject constructor(
    private val firebaseFriendListDao: FirebaseFriendListDao
) {
    private val descendingOrAscendingIcon = mutableStateOf(Icons.Default.ArrowDropUp)
    private val selectedCategory = mutableStateOf(ListCategory.ALL)

    private val descending = mutableStateOf(false)

    private val sortOption = mutableStateOf(SortOptions.STATUS.value)


    fun onSelectedCategoryChanged(category: ListCategory){
        selectedCategory.value = category
    }

    fun getSelectedCategory(): ListCategory {
        return selectedCategory.value
    }

    fun toggleDescendingOrAscendingIcon(){
        if(isDescending()){
            descending.value = false
            descendingOrAscendingIcon.value = Icons.Default.ArrowDropUp
        } else {
            descending.value = true
            descendingOrAscendingIcon.value = Icons.Default.ArrowDropDown
        }
    }

    fun getDescendingOrAscendingIcon(): ImageVector {
        return descendingOrAscendingIcon.value
    }

    fun getAllGames(): Flow<List<ListEntry>> {
        return sortGames(firebaseFriendListDao.getAll())
    }

    fun getPlaying(): Flow<List<ListEntry>> {
        return removeStatusSortOption(firebaseFriendListDao.getPlaying())
    }

    fun getCompleted(): Flow<List<ListEntry>> {
        return removeStatusSortOption(firebaseFriendListDao.getCompleted())
    }

    fun getPlanned(): Flow<List<ListEntry>> {
        return removeStatusSortOption(firebaseFriendListDao.getPlanned())
    }

    fun getDropped(): Flow<List<ListEntry>> {
        return removeStatusSortOption(firebaseFriendListDao.getDropped())
    }

    val favorites = firebaseFriendListDao.getFavorites()

    fun setSortOption(option : String){
        sortOption.value = option
    }

    fun getSortOption(): String {
        return sortOption.value
    }

    private fun isDescending(): Boolean {
        return descending.value
    }

    private fun setDescAsc(entries: Flow<List<ListEntry>>): Flow<List<ListEntry>> {
        return if(descending.value){
            entries.map { it.reversed() }
        } else {
            entries
        }
    }

    private fun sortGames(entries: Flow<List<ListEntry>>): Flow<List<ListEntry>> {
        val sortedGames = when(sortOption.value){
            SortOptions.ALPHABETICALLY.value -> setDescAsc(entries.map { it.sortedBy { game -> game.title } })
            SortOptions.SCORE.value -> setDescAsc(entries.map { it.sortedBy { game -> game.score } })
            SortOptions.TIME_PLAYED.value -> setDescAsc(entries.map { it.sortedBy { game -> game.minutesPlayed } })
            SortOptions.RELEASE_DATE.value -> setDescAsc(entries.map { it.sortedBy { game -> game.releaseDate } })
            else -> setDescAsc(firebaseFriendListDao.getAll()) // STATUS
        }
        return sortedGames
    }

    private fun removeStatusSortOption(entries: Flow<List<ListEntry>>): Flow<List<ListEntry>> {
        if(sortOption.value == SortOptions.STATUS.value){
            sortOption.value = SortOptions.ALPHABETICALLY.value
        }
        return sortGames(entries)
    }

    fun getCategoryByName(category: String): Flow<List<ListEntry>> {
        val games: Flow<List<ListEntry>> =
            when(category){
                ListCategory.PLAYING.value -> firebaseFriendListDao.getPlaying()
                ListCategory.COMPLETED.value -> firebaseFriendListDao.getCompleted()
                ListCategory.PLANNED.value -> firebaseFriendListDao.getPlanned()
                ListCategory.DROPPED.value -> firebaseFriendListDao.getDropped()
                else -> {firebaseFriendListDao.getAll()}
            }
        return games
    }


    fun setFriendId(s: String){
        firebaseFriendListDao.setFriendId(s)
    }
}
