package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nexus.data.ListRepository
import com.example.nexus.ui.routes.list.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusListViewModel  @Inject constructor(private val repo: ListRepository) : ViewModel(){

    val selectedCategory: MutableState<ListCategory> = mutableStateOf(ListCategory.ALL)


    fun onSelectedCategoryChanged(category: ListCategory){
        selectedCategory.value = category
    }

//    private fun setSelectedCategory(category: ListCategory){
//        selectedCategory.value = category
//    }
}