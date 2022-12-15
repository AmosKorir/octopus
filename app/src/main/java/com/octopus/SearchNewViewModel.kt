package com.octopus

import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.data.repositories.db.UserDbRepository
import com.octopus.data.repositories.pagingsource.Paginater
import com.octopus.domain.OctConstants
import com.octopus.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SearchNewViewModel @Inject constructor(
    private val userApiRepository: UserRepository,
    @Named("DB")
    private val userDbRepository: UserDbRepository,
    private val paginater: Paginater
) :
    BaseViewModel() {

    fun searchUser(username: String) {
        userApiRepository.searchUser(username)
            .flatMap { userDbRepository.insertUser(it) }
            .background()
            .resultSubscribe()
    }

    fun getFollowing(username: String) {
        paginater.fetchFollowingData(username)
            .map { PageDataType(OctConstants.FOLLOWING, it) }
            .background()
            .resultSubscribe()
    }

    fun getFollowers(username: String) {
        paginater.fetchFollowerData(username)
            .map { PageDataType(OctConstants.FOLLOWER, it) }
            .background()
            .resultSubscribe()
    }
}