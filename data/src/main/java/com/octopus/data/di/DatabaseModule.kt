package com.octopus.data.di

import android.content.Context
import androidx.room.Room
import com.octopus.data.api.UserApi
import com.octopus.data.repositories.pagingsource.Paginater
import com.octopus.data.sql.OctopusDatabase
import com.octopus.data.sql.dao.FollowerDao
import com.octopus.data.sql.dao.FollowingDao
import com.octopus.data.sql.dao.RemoteKeysDao
import com.octopus.data.sql.dao.UserDao
import com.octopus.domain.OctConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): OctopusDatabase {
        return Room.databaseBuilder(context, OctopusDatabase::class.java, OctConstants.OCTAT_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(octopusDatabase: OctopusDatabase): UserDao {
        return octopusDatabase.userDao()
    }

    @Provides
    fun provideFollowerDao(octopusDatabase: OctopusDatabase): FollowerDao {
        return octopusDatabase.followerDao()
    }

    @Provides
    fun provideFollowingDao(octopusDatabase: OctopusDatabase): FollowingDao {
        return octopusDatabase.followingDao()
    }

    @Provides
    fun provideRemoteKeyDao(octopusDatabase: OctopusDatabase): RemoteKeysDao {
        return octopusDatabase.remoteKeysDao()
    }

    @Provides
    fun providePaginator(
        userApi: UserApi,
        remoteKeysDao: RemoteKeysDao,
        followerDao: FollowerDao,
        followingDao: FollowingDao
    ): Paginater {
        return Paginater(userApi,remoteKeysDao,followerDao,followingDao)
    }
}