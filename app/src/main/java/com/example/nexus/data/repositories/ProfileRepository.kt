package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseListDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val firebaseListDao: FirebaseListDao
) {

}