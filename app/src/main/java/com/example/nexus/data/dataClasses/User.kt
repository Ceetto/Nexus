package com.example.nexus.data.dataClasses


data class User(val email: String,
                     val username: String,
                     var friends: List<String>,
                     var friendRequests: List<String>,
                     var profilePicture: String,
                     var profileBackground :String,
                     var releaseNotification : Long
                     )
