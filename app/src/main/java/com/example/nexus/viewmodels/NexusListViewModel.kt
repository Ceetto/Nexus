package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.ListRepository
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.web.ListEntry
import com.example.nexus.ui.routes.list.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NexusListViewModel  @Inject constructor(private val repo: ListRepository) : ViewModel(){

    val selectedCategory: MutableState<ListCategory> = mutableStateOf(ListCategory.ALL)


    fun onSelectedCategoryChanged(category: ListCategory){
        selectedCategory.value = category
    }

    fun storeBackendGamesInDb(){
        repo.getBackendGames().forEach { game ->
            storeListEntry(game)
        }
    }

    val allGames: StateFlow<List<ListEntity>> by lazy {
        repo.allGames.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun storeListEntry(entry: ListEntry) = viewModelScope.launch { repo.storeListEntry(entry) }

    fun deleteListEntry(entity: ListEntity) = viewModelScope.launch { repo.deleteQuoter(entity) }

    private val _backendGames = MutableStateFlow(repo.getBackendGames())
    val backendGames: StateFlow<List<ListEntry>> = _backendGames

//    private fun setSelectedCategory(category: ListCategory){
//        selectedCategory.value = category
//    }
}