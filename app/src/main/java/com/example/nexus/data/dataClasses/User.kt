package com.example.nexus.data.dataClasses



data class User(var email: String,
                     var username: String,
                     var friends: List<String>,
                     var friendRequests: List<String>,
                     var profilePicture: String,
                     var profileBackground :String,
                     var releaseNotification : Long
                     )

