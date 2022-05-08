package com.example.nexus.viewmodels

import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class NexusLoginViewModel  @Inject constructor(
    private val repo: LoginRepository,
    private val profileRepo : ProfileRepository) : ViewModel() {
    private var isBusy = mutableStateOf(false)
    private var email = mutableStateOf("")
    private var password = mutableStateOf("")
    private var isEmailValidTest = mutableStateOf(true)
    private var isPasswordValidTest = mutableStateOf(true)
    private val isEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
    private val isPasswordValid by derivedStateOf {
        password.value.length > 7
    }
    private var isPasswordVisible = mutableStateOf(false)

    fun checkEmail() {
        isEmailValidTest.value = Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun checkPassword() {
        isPasswordValidTest.value = password.value.length > 7
    }

    fun getIsEmailValidTest(): Boolean {
        return isEmailValidTest.value
    }

    fun getIsPasswordValidTest(): Boolean {
        return isPasswordValidTest.value
    }

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

    fun getIsLoggedIn() = repo.getIsLoggedIn()

//    fun createAccount() {
//        repo.createAccount(email.value, password.value)
//
//
//        if (repo.getIsLoggedIn()){
//            println("in if in create account")
//            profileRepo.storeNewUser(User(email.value, "NewUser", emptyList(), emptyList(), "", "", 0L))
//        }
//    }

    fun createAccount() = repo.createAccount(email.value, password.value)

    fun signIn() = repo.signIn(email.value, password.value)

    fun getUserAlreadyExists() = repo.getUserAlreadyExists()

    fun getUserDoesNotExists() = repo.getUserDoesNotExists()
}

val UserState = compositionLocalOf<NexusLoginViewModel> { error("User State Context Not Found!") }
