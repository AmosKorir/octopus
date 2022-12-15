package com.octopus.data.repositories.db

import com.octopus.data.mappers.UserMapper
import com.octopus.data.sql.dao.UserDao
import com.octopus.domain.models.User
import com.octopus.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Single

class UserDbRepository(
    private val userDao: UserDao,
) :
    UserRepository {
    override fun searchUser(username: String): Single<User> {
       return userDao.getAllUser(username)
           .map {it.firstOrNull()  }
            .flatMap {
                if (it==null){
                    Single.error(Throwable(""))
                }else{
                    Single.just(UserMapper.transform(it))
                }
            }
           }

    override fun insertUser(user: User): Single<User> {
        return  Single.just(user)
            .map { UserMapper.transform(user) }
            .flatMap { userDao.insertUser(it) }
            .map {user  }



    }
}


