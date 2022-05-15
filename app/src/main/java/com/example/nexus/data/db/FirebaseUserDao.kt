package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.dataClasses.getUserId
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
    private val friendId = mutableStateOf("")

    private val userRef = mutableStateOf(database.getReference("user/${getUserId(auth.currentUser)}"))

    private val friendRef = mutableStateOf(database.getReference("user/${friendId.value}"))

    private val newUser = mutableStateOf(User("",
        "NewUser",
        "",
        "",))

    private val newFriend = mutableStateOf(User( "", "", "", ""))

    private val eventListener =
        object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
            if (snapshot.children.count() >= 4  && snapshot.child("email").value != null) {
                newUser.value.email = snapshot.child("email").value as String
                newUser.value.username = snapshot.child("username").value as String
                newUser.value.profilePicture = snapshot.child("profilePicture").value as String
                newUser.value.profileBackground = snapshot.child("profileBackground").value as String
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }


    private val eventListenerFriend =
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
                if (snapshot.children.count() >= 4 && snapshot.child("email").value != null ) {
                    newFriend.value.email = snapshot.child("email").value as String
                    newFriend.value.username = snapshot.child("username").value as String
                    newFriend.value.profilePicture = snapshot.child("profilePicture").value as String
                    newFriend.value.profileBackground = snapshot.child("profileBackground").value as String
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }


    private val realtimeEntries = mutableStateOf(userRef.value.addValueEventListener(eventListener))

    fun setFriendId(id: String) {
        friendId.value = id
        updateFriend()
    }

    fun updateUser(){
        userRef.value = database.getReference("user/${getUserId(auth.currentUser)}")
        realtimeEntries.value = userRef.value.addValueEventListener(eventListener)
    }

    fun updateFriend(){
        friendRef.value = database.getReference("user/${friendId.value}")
        friendRef.value.addValueEventListener(eventListenerFriend)
    }

    private val TAG = "UserRepository"

    fun getUser() : User{
        return newUser.value
    }


    fun getFriend() : User {
        return newFriend.value
    }

    fun storeNewUser(user : User)  {
        updateUser()
        userRef.value.child("email").setValue(user.email)
        userRef.value.child("username").setValue(user.username)
        userRef.value.child("profilePicture").setValue(user.profilePicture)
        userRef.value.child("profileBackground").setValue(user.profileBackground)
    }

    fun changeUsername(username: String) {
        userRef.value.child("username").setValue(username)
    }

    fun changeProfilePic(url: String){
        userRef.value.child("profilePicture").setValue(url)
    }

    fun changeBackground(url: String){
        userRef.value.child("profileBackground").setValue(url)
    }

    fun getProfilePicture(): String {
        return newUser.value.profilePicture
    }

    fun getBackground(): String {
        return newUser.value.profileBackground
    }
}