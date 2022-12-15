package com.octopus.data.repositories.db

import com.octopus.SampleData
import com.octopus.data.mappers.UserMapper
import com.octopus.data.models.db.UserDbModel
import com.octopus.data.sql.dao.UserDao
import com.octopus.domain.models.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class UserDbRepositoryTest {
    private val userDao = mockk<UserDao>()
    private val userDbRepository = UserDbRepository(userDao)

    @Test
    fun testSearchUser() {

        val mockUserEntityList = mockk<List<UserDbModel>>()
        every { userDao.getAllUser(any()) } returns Single.just(mockUserEntityList)
        val username = "dummy_username"
        userDbRepository.searchUser(username)
        verify { userDao.getAllUser(username) }
    }

    @Test
    fun `insertUser should insert user into database`() {
        val user = SampleData.user
        val userDb = UserMapper.transform(user)
        every { userDao.insertUser(userDb) } returns Single.just(1)
        userDbRepository.insertUser(user)
            .blockingGet()
        verify { userDao.insertUser(userDb) }
    }
}

