package com.octopus.data.sql.dao

import androidx.room.*
import com.octopus.data.models.db.UserDbModel
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userDbModel: UserDbModel): Single<Long>

    @Delete
    fun deleteUsers(userDbModels: List<UserDbModel>): Int

    @Query("SELECT * FROM users")
    fun getAllUsers(): Single<List<UserDbModel>>

    @Query("SELECT * FROM users where name=:username")
    fun getAllUser(username:String): Single<List<UserDbModel>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(userDbModel: UserDbModel?): Int
}