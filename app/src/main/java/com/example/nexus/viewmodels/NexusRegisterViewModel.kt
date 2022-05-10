package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.data.repositories.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NexusRegisterViewModel @Inject constructor(
    private val registerRepo: RegisterRepository,
    private val loginRepo : LoginRepository
): ViewModel() {
}