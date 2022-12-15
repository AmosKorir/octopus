package com.octopus.data.sql.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.octopus.data.models.db.FollowerDBModel
import com.octopus.data.models.db.FollowingDBModel
import com.octopus.data.models.db.UserDbModel
import io.reactivex.rxjava3.core.Single
@Dao
interface FollowingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollowing(followingDBModel: FollowingDBModel): Long

    @Delete
    fun deleteFollowings(followings: List<FollowingDBModel>): Int

    @Query("SELECT * FROM followings")
    fun getAllFollowings(): Single<List<FollowingDBModel>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFollowings(followingDBModel: FollowingDBModel): Int

    @Query("SELECT * FROM followings")
    fun getPaginateFollowing(): PagingSource<Int, FollowingDBModel>
}