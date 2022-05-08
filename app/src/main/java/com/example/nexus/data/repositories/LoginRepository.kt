package com.example.nexus.data.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor() {

    private val userId = mutableStateOf("")

    fun getUserId(): String {
        return userId.value
    }

    fun setUserId(id: String) {
        userId.value = id
    }
}