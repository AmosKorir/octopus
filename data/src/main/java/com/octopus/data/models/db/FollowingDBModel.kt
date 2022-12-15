package com.octopus.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "followings")
data class FollowingDBModel(
    @PrimaryKey val id: Int,
    val name: String,
    val followBy: String,
    val avatar_url: String,
)