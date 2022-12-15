package com.octopus.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "users")
data class UserDbModel(
    @PrimaryKey val id: Int,
    val avatar_url: String,
    val bio: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val location: String,
    val name: String,
    val public_gists: Int,
    val public_repos: Int,
    val url: String
)
