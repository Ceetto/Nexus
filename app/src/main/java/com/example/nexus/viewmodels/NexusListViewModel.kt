package com.example.nexus.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.data.dataClasses.ListEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NexusListViewModel  @Inject constructor(private val repo: ListRepository) : ViewModel(){

    private val selectedCategory = mutableStateOf(ListCategory.ALL)

    fun onSelectedCategoryChanged(category: ListCategory){
        selectedCategory.value = category
    }

    fun getSelectedCategory(): ListCategory {
        return selectedCategory.value
    }

    fun getCategory(category: String): StateFlow<List<ListEntry>> {
        val games: StateFlow<List<ListEntry>> by lazy {
            when(category){
                ListCategory.PLAYING.value -> repo.playing.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.COMPLETED.value -> repo.completed.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.PLANNED.value -> repo.planned.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.DROPPED.value -> repo.dropped.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                else -> {repo.allGames.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())}
            }
        }
        return games
    }
}