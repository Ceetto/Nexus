package com.example.nexus.data.repositories


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.activities.MainActivity
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.dataClasses.getUserId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private var profileRepo: ProfileRepository
) {

    private var auth: FirebaseAuth = Firebase.auth
    private var isLoggedIn = mutableStateOf(auth.currentUser != null)
    private var userAlreadyExists = mutableStateOf(false)
    private var userDoesNotExists = mutableStateOf(false)

    fun getIsLoggedIn(): Boolean {
        return isLoggedIn.value
    }

    fun getUserAlreadyExists(): Boolean {
        return userAlreadyExists.value
    }

    fun getUserDoesNotExists(): Boolean {
        return userDoesNotExists.value
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isLoggedIn.value = true
                    profileRepo.storeNewUser(User(email, "NewUser", emptyList(), emptyList(), "", "", 0L))
                    userAlreadyExists.value = false
                    Log.d(MainActivity.TAG, "The user registered a new account and logged in")
                } else {
                    userAlreadyExists.value = true
                    Log.w(MainActivity.TAG, "The user has FAILED to make a new account and log in", it.exception)
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isLoggedIn.value = true
                    userDoesNotExists.value = false
                    Log.d(MainActivity.TAG, "The user has successfully logged in")
                } else {
                    userDoesNotExists.value = true
                    Log.w(MainActivity.TAG, "The user has FAILED to log in", it.exception)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        isLoggedIn.value = false
    }
}