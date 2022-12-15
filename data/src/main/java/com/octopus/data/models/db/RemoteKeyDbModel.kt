package com.octopus.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "remote_keys")
data class RemoteKeyDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage:Int ?,
    val nextPage:Int ?

)