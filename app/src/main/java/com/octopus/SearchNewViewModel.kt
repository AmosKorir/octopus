package com.octopus

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.data.repositories.pagingsource.FollowPagingSource
import com.octopus.domain.OctConstants
import com.octopus.domain.models.Follower
import com.octopus.domain.models.User
import com.octopus.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class SearchNewViewModel @Inject constructor(
    private val userApiRepository: UserRepository
) :
    BaseViewModel() {


    fun searchUser(username: String) {
        userApiRepository.searchUser(username)
            .background()
            .resultSubscribe()
    }

    fun getFollowers(username: String) {
        userApiRepository.getFollowers(username,1)
            .background()
            .resultSubscribe()
    }


    fun getPaginatedFollowers(username: String) {
        val pagingSource = FollowPagingSource(userApiRepository,username)
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 2,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { pagingSource }

        )
        pager.observable
            .background()
            .resultSubscribe()
    }

    fun paginatedFollowers() {

    }
}