package com.example.nexus.data.dataClasses

import com.google.firebase.auth.FirebaseUser


data class User(var email: String,
                     var username: String,
                     var friends: List<String>,
                     var friendRequests: List<String>,
                     var profilePicture: String,
                     var profileBackground :String,
                     var releaseNotification : Long
                     )

fun getUserId(currentUser: FirebaseUser?): String {
    return currentUser?.uid ?: ""
}
