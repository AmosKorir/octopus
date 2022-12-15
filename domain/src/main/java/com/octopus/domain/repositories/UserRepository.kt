package com.octopus.domain.repositories

import com.octopus.domain.models.User
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun searchUser(username: String): Single<User>

    fun insertUser(user: User): Single<User>

}