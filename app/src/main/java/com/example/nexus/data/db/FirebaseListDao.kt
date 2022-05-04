package com.example.nexus.data.db

import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseListDao @Inject constructor(
    private val database: FirebaseDatabase
){
    private val listEntryRef = database.getReference("Users")
    data class User(val username: String? = null, val email: String? = null) {}
    fun writeTest(){
        val user = User("name2", "email")
        listEntryRef.child("addam").setValue(user)
    }
}