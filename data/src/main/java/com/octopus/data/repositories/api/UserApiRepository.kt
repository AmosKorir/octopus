package com.octopus.data.repositories.api

import com.octopus.data.api.UserApi
import com.octopus.data.mappers.UserMapper
import com.octopus.domain.models.Follower
import com.octopus.domain.models.User
import com.octopus.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class UserApiRepository(private val userApi: UserApi) :
    UserRepository {
    override fun searchUser(username: String): Single<User> {
        return userApi.getUsers(username)
            .map { UserMapper.transform(it) }
    }

    override fun getFollowers(username: String,page:Int): Single<List<Follower>> {
        return userApi.getFollowers(username,page)
            .flatMapPublisher { Flowable.fromIterable(it) }
            .map { UserMapper.transform(it) }
            .toList()
    }



}