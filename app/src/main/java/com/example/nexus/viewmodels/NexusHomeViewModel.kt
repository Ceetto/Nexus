package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusHomeViewModel  @Inject constructor(private val repo: HomeRepository) : ViewModel(){
}