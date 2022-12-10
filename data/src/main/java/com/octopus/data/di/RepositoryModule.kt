package com.octopus.data.di

import com.octopus.data.api.UserApi
import com.octopus.data.repositories.api.UserApiRepository
import com.octopus.data.repositories.pagingsource.FollowPagingSource
import com.octopus.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserApiRepository(userApi: UserApi):UserRepository{
        return UserApiRepository(userApi)
    }



}