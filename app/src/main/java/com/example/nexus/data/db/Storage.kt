package com.example.nexus.data.db

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

abstract class Storage {
    companion object {
        fun getInstance() : FirebaseStorage{
            return Firebase.storage
        }
    }
}