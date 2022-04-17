package com.salman.gitsy.domain.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    var userId: Long,
    var name: String = "",
    var username: String = "",
    var avatarUrl: String = "",
    var bio: String = "",
    var company: String = "",
    var createdAt: String = "",
    var email: String = "",
    var followers: Long = 0,
    var following: Long = 0,
    var hireable: Boolean = false,
    var publicGists: Int = 0,
    var publicRepos: Int = 0,
    var twitterUsername: String = ""
)