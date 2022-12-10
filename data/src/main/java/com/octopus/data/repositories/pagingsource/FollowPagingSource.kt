package com.octopus.data.repositories.pagingsource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.octopus.domain.models.Follower
import com.octopus.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FollowPagingSource(private val userRepository: UserRepository, private val username: String) :
    RxPagingSource<Int, Follower>() {

    override fun getRefreshKey(state: PagingState<Int, Follower>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Follower>> {
        val position = params.key ?: 1
        return userRepository.getFollowers(username, position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: List<Follower>, position: Int): LoadResult<Int, Follower> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.size) null else position + 1
        )
    }

}