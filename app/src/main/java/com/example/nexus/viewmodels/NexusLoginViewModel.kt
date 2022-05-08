package com.example.nexus.viewmodels

import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NexusLoginViewModel  @Inject constructor(private val repo: LoginRepository) : ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
    private var isLoggedIn = mutableStateOf(false)
    private var isBusy = mutableStateOf(false)
    private var email = mutableStateOf("")
    private var password = mutableStateOf("")
    private val isEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
    private val isPasswordValid by derivedStateOf {
        password.value.length > 7
    }
    private var isPasswordVisible = mutableStateOf(false)

    suspend fun signIn(username: String, password: String) {
        isBusy.value = true
        delay(1250)
        isLoggedIn.value = true
        isBusy.value = false
    }

    suspend fun signOut() {
        isBusy.value = true
        delay(1250)
        isLoggedIn.value = false
        isBusy.value = false
    }

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun getUserId() = repo.getUserId()

    fun setUserId(id: String) = repo.setUserId(id)

    fun getEmail(): String {
        return email.value
    }

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun getPassword(): String {
        return password.value
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    fun getIsEmailValid(): Boolean {
        return isEmailValid
    }

    fun getIsPasswordValid(): Boolean {
        return isPasswordValid
    }

    fun getIsBusy(): Boolean {
        return isBusy.value
    }

    fun getIsPasswordVisible(): Boolean {
        return isPasswordVisible.value
    }

    fun setIsPasswordVisible(value: Boolean) {
        isPasswordVisible.value = value
    }

    fun getIsLoggedIn(): Boolean {
        return isLoggedIn.value
    }
}

val UserState = compositionLocalOf<NexusLoginViewModel> { error("User State Context Not Found!") }
