package com.example.nexus.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class Authentication {
    companion object{
        fun getAuth(): FirebaseAuth {
            return Firebase.auth
        }
    }
}