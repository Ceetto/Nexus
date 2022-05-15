package com.example.nexus.viewmodels


import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusLoginViewModel  @Inject constructor(
    private val repo: LoginRepository,
    private val profileRepo : ProfileRepository
    ) : ViewModel() {
    private var isPasswordVisible = mutableStateOf(false)
    private var isEmailValidTest = mutableStateOf(true)
    private var isPasswordValidTest = mutableStateOf(true)
    private var isUsernameValidTest = mutableStateOf(true)
    private val isPasswordValid = mutableStateOf(repo.getPassword().length > 7)
    private val isEmailValid = mutableStateOf(Patterns.EMAIL_ADDRESS.matcher(repo.getEmail()).matches())
    private val isUsernameValid = mutableStateOf(repo.getUsername().length > 1)

    fun getIsPasswordValid(): Boolean {
        return isPasswordValid.value
    }

    fun getIsUsernameValidTest(): Boolean {
        return isUsernameValidTest.value
    }

    fun getIsEmailValid(): Boolean {
        return isEmailValid.value
    }

    fun getIsUsernameValid(): Boolean {
        return isUsernameValid.value
    }

    fun checkPassword() {
        isPasswordValidTest.value = repo.getPassword().length > 7
    }

    fun checkUsername() {
        isUsernameValidTest.value = repo.getUsername().length >= 2
    }

    fun getIsPasswordValidTest(): Boolean {
        return isPasswordValidTest.value
    }

    fun getUsername() = repo.getUsername()

    fun setUsername(username: String) {
        repo.setUsername(username)
        isUsernameValid.value = repo.getUsername().length >= 2
    }

    fun checkEmail() {
        isEmailValidTest.value = Patterns.EMAIL_ADDRESS.matcher(repo.getEmail()).matches()
    }

    fun getIsEmailValidTest(): Boolean {
        return isEmailValidTest.value
    }

    fun getEmail() = repo.getEmail()

    fun setEmail(email: String) {
        repo.setEmail(email)
        isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(repo.getEmail()).matches()
    }

    fun getPassword() = repo.getPassword()

    fun setPassword(password: String) {
        repo.setPassword(password)
        isPasswordValid.value = repo.getPassword().length > 7
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

    fun createAccount() = repo.createAccount()

    fun signIn() = repo.signIn()

    fun getUserAlreadyExists() = repo.getUserAlreadyExists()

    fun getUserDoesNotExists() = repo.getUserDoesNotExists()
}

//val UserState = compositionLocalOf<NexusLoginViewModel> { error("User State Context Not Found!") }
