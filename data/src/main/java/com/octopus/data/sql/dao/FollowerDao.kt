package com.octopus.data.sql.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.octopus.data.models.db.FollowerDBModel
import io.reactivex.rxjava3.core.Single

@Dao
interface FollowerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollower(followerDBModel: FollowerDBModel): Long

    @Delete
    fun deleteFollowers(followers: List<FollowerDBModel>): Int

    @Query("SELECT * FROM followers")
    fun getAllFollowers(): Single<List<FollowerDBModel>>

    @Query("SELECT * FROM followers where following=:username")
    fun getPaginateFollowers(username:String): PagingSource<Int, FollowerDBModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFollowers(followerDBModel: FollowerDBModel): Int
}