package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.db.ListDao
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.dataClasses.ListEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val dao: ListDao,
    private val firebaseListDao: FirebaseListDao
) {



    suspend fun storeListEntry(entry: ListEntry) = firebaseListDao.storeListEntry(entry)

    suspend fun deleteListEntry(entity: ListEntity) = dao.deleteListEntry(entity)

    fun getCategory(category: String) = dao.getCategory(category)

    suspend fun wipeDatabase() = dao.wipeDatabase()

    val allGames = firebaseListDao.getAll()

    val playing = firebaseListDao.getPlaying()

    val completed = firebaseListDao.getCompleted()

    val planned = firebaseListDao.getPlanned()

    val dropped = firebaseListDao.getDropped()

//    suspend fun getTest(): Flow<ArrayList<ListEntry>> {
//        val firestoreTest = listEntryReference
//            .get()
//            .addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//            .await()
//        val entries = ArrayList<ListEntry>()
//        for (document in firestoreTest.documents){
//            entries.add(ListEntry(
//                document.getLong("gameId")!!, document.getString("title")!!,
//                document.getLong("score")!!.toInt(), document.getLong("minutesPlayed")!!.toInt(),
//                document.getString("status")!!, document.getString("coverUrl")
//            ))
//        }
//        return MutableStateFlow(entries)
//    }

}