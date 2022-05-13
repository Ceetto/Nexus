package com.example.nexus.data.dataClasses

import com.google.firebase.auth.FirebaseUser


data class User(var email: String,
                     var username: String,
                     var profilePicture: String,
                     var profileBackground :String,
                     )

fun getUserId(currentUser: FirebaseUser?): String {
    return currentUser?.uid ?: ""
}
