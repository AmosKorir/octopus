package com.octopus.domain.repositories

import com.octopus.domain.models.Follower
import com.octopus.domain.models.User
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun searchUser(username: String): Single<User>
    fun getFollowers(username: String, page:Int): Single<List<Follower>>
}