package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserDao @Inject constructor(
    private val database: FirebaseDatabase
){
    private val userRef = database.getReference("user")

    var doneFetching = mutableStateOf(false)


    private val newUser = mutableStateOf(User("", "NewUser", emptyList(), emptyList(), "", "", 0L))

    private val realtimeEntries = userRef.addValueEventListener(
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.

                newUser.value.email = snapshot.child("email").value as String
                newUser.value.username = snapshot.child("username").value  as String
                newUser.value.friends = snapshot.child("friends").value as List<String>
                newUser.value.friendRequests = snapshot.child("friendRequests").value as List<String>
                newUser.value.profilePicture = snapshot.child("profilePicture").value as String
                newUser.value.profileBackground = snapshot.child("profileBackground").value as String
                newUser.value.releaseNotification = snapshot.child("releaseNotification").value as Long

                doneFetching.value = true
                }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }


        }
    )

    private val TAG = "UserRepository"

    fun getUser() : User{
        return newUser.value
    }
}