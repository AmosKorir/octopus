package com.octopus.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "followers")
data class FollowerDBModel(
    @PrimaryKey val id: Int,
    val name: String,
    val following: String,
    val avatar_url: String,


)