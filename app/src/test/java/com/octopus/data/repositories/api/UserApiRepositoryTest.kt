package com.octopus.data.repositories.api


import com.octopus.data.api.UserApi
import com.octopus.data.models.api.UserApiResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class UserApiRepositoryTest {
    private val userApi = mockk<UserApi>()
    private val userApiRepository = UserApiRepository(userApi)

    @Test
    fun testSearchUser() {

        val mockUserModel = mockk<UserApiResponse>()
        every { userApi.getUsers(any()) } returns Single.just(mockUserModel)
        val username = "dummy_username"
        userApiRepository.searchUser(username)
            .blockingGet()
        verify { userApi.getUsers(username) }
    }
}
