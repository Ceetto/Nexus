package com.example.nexus.data.dataClasses

data class Notification(val userId: String = "",
                        val gameId: Long = 0,
                        val releaseDate: Long = 0,
                        val notificationTime: Long = 0,
                        val read: Boolean = false,
                        val pfp: String? = "",
                        val gameCover: String? = "",
                        val username: String = "",
                        val gameName: String = "",
                        val notificationType: String = NotificationType.FRIEND_REQUEST.value,
)

enum class NotificationType(val value: String){
    RELEASE_DATE("Release date"),
    FRIEND_REQUEST("Friend request")
}