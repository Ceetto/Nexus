package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.dataClasses.getUserId
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.di.AuthenticationModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserDao @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
){
    private val userRef = mutableStateOf(database.getReference("user/${getUserId(auth.currentUser)}"))


    var doneFetching = mutableStateOf(false)


    private val newUser = mutableStateOf(User("",
        "NewUser",
        emptyList(),
        emptyList(),
        "",
        "",
        0L))

    private val eventListener =
        object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
            if (snapshot.children.count() >= 7 && getUserId(auth.currentUser) != "") {
                newUser.value.email = snapshot.child("email").value as String
                newUser.value.username = snapshot.child("username").value as String
                newUser.value.friends = snapshot.child("friends").value as List<String>
                newUser.value.friendRequests = snapshot.child("friendRequests").value as List<String>
                newUser.value.profilePicture = snapshot.child("profilePicture").value as String
                newUser.value.profileBackground = snapshot.child("profileBackground").value as String
                newUser.value.releaseNotification = snapshot.child("releaseNotification").value as Long
            }

            doneFetching.value = true
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }


    }

    private val realtimeEntries = mutableStateOf(userRef.value.addValueEventListener(eventListener))

    fun updateUser(){
        userRef.value = database.getReference("user/${getUserId(auth.currentUser)}")
        realtimeEntries.value = userRef.value.addValueEventListener(eventListener)
    }

    private val TAG = "UserRepository"

    fun getUser() : User{
        return newUser.value
    }

    fun storeNewUser(user : User)  {
        updateUser()
        userRef.value.child("email").setValue(user.email)
        userRef.value.child("username").setValue(user.username)
        userRef.value.child("friends").setValue(user.friends)
        userRef.value.child("friendRequests").setValue(user.friendRequests)
        userRef.value.child("profilePicture").setValue(user.profilePicture)
        userRef.value.child("profileBackground").setValue(user.profileBackground)
        userRef.value.child("releaseNotification").setValue(user.releaseNotification)
    }
}