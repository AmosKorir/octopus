package com.octopus.data.sql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.octopus.data.models.db.FollowerDBModel
import com.octopus.data.models.db.FollowingDBModel
import com.octopus.data.models.db.RemoteKeyDbModel
import com.octopus.data.models.db.UserDbModel
import com.octopus.data.sql.dao.FollowerDao
import com.octopus.data.sql.dao.FollowingDao
import com.octopus.data.sql.dao.RemoteKeysDao
import com.octopus.data.sql.dao.UserDao

@Database(
    entities = [FollowingDBModel::class, UserDbModel::class, FollowerDBModel::class,RemoteKeyDbModel::class],
    version = 1
)
abstract class OctopusDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun followingDao(): FollowingDao

    abstract fun followerDao(): FollowerDao

    abstract fun remoteKeysDao(): RemoteKeysDao

}