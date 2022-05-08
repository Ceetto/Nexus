package com.example.nexus.data.repositories


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.activities.MainActivity
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.db.FirebaseUserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
) {

    private val userId = mutableStateOf("")
    private var auth: FirebaseAuth = Firebase.auth
    private var isLoggedIn = mutableStateOf(false)


    fun getUserId(): String {
        return userId.value
    }

    fun getIsLoggedIn(): Boolean {
        return isLoggedIn.value
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isLoggedIn.value = true
                    Log.d(MainActivity.TAG, "The user registered a new account and logged in")
                } else {
                    Log.w(MainActivity.TAG, "The user has FAILED to make a new account and log in", it.exception)
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isLoggedIn.value = true
                    Log.d(MainActivity.TAG, "The user has successfully logged in")
                } else {
                    Log.w(MainActivity.TAG, "The user has FAILED to log in", it.exception)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        isLoggedIn.value = false
    }

    fun signedIn(): Boolean {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            println(currentUser.email)
        }
        return currentUser != null
    }
}