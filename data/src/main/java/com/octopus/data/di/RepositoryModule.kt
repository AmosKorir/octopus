package com.octopus.data.di

import com.octopus.data.api.UserApi
import com.octopus.data.repositories.api.UserApiRepository
import com.octopus.data.repositories.db.UserDbRepository
import com.octopus.data.sql.dao.FollowerDao
import com.octopus.data.sql.dao.FollowingDao
import com.octopus.data.sql.dao.RemoteKeysDao
import com.octopus.data.sql.dao.UserDao
import com.octopus.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserApiRepository(userApi: UserApi):UserRepository{
        return UserApiRepository(userApi)
    }

    @Provides
    @Named("DB")
    fun provideUserDBRepository(userDao: UserDao):UserDbRepository{
        return  UserDbRepository(userDao)
    }


}