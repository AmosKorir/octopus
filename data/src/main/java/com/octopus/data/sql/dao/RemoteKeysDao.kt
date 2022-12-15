package com.octopus.data.sql.dao

import androidx.room.*
import com.octopus.data.models.db.FollowerDBModel
import com.octopus.data.models.db.RemoteKeyDbModel
import io.reactivex.rxjava3.core.Single
@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(remoteKeyDbModel: RemoteKeyDbModel): Long

    @Delete
    fun deleteRemoteKeys(followers: List<FollowerDBModel>): Int

    @Query("SELECT * FROM remote_keys")
    fun getAllRemoteKeys(): Single<List<RemoteKeyDbModel>>


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRemoteKey(remoteKeyDbModel: RemoteKeyDbModel): Int

    @Query("SELECT * FROM remote_keys Where id =:login")
    fun getByIDRemoteKeys(login: Int):Single<List<RemoteKeyDbModel>>

//    @Query("DROP TABLE remote_keys")
//    fun deleteAllRemoteKeys():Int
}