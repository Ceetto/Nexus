package com.example.nexus.data.db

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

abstract class Database {
    companion object{
        fun getInstance(): FirebaseDatabase {
            val db =  Firebase.database("https://nexus-f7d65-default-rtdb.europe-west1.firebasedatabase.app")
            db.setPersistenceEnabled(true)
            return db
        }
    }
}