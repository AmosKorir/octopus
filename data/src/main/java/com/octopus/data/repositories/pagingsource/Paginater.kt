package com.octopus.data.repositories.pagingsource

import androidx.paging.*
import androidx.paging.rxjava3.observable
import com.octopus.data.api.UserApi
import com.octopus.data.mappers.UserMapper
import com.octopus.data.sql.dao.FollowerDao
import com.octopus.data.sql.dao.FollowingDao
import com.octopus.data.sql.dao.RemoteKeysDao
import com.octopus.domain.models.Follower
import com.octopus.domain.models.Following
import io.reactivex.rxjava3.core.Observable

class Paginater(
    private val userApi: UserApi,
    private val remoteKeysDao: RemoteKeysDao,
    private val followerDao: FollowerDao,
    private val followingDao: FollowingDao
) {

    @OptIn(ExperimentalPagingApi::class)
    fun fetchFollowerData(username: String): Observable<PagingData<Follower>> {
        val followRemoteMediator =
            FollowerRemoteMediator(userApi, remoteKeysDao, followerDao, username)
        val pager2 = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 10
            ),
            remoteMediator = followRemoteMediator,
            pagingSourceFactory = {
                followerDao.getPaginateFollowers(username)
            }
        )
        return pager2.observable.map {
            it.map {follower->
                UserMapper.transform(follower)
            }
        }
    }


    @OptIn(ExperimentalPagingApi::class)
    fun fetchFollowingData(username: String): Observable<PagingData<Following>> {
        val followRemoteMediator =
            FollowingRemoteMediator(userApi, remoteKeysDao, followingDao, username)
        val pager2 = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 10
            ),
            remoteMediator = followRemoteMediator,
            pagingSourceFactory = {
                followingDao.getPaginateFollowing()
            }
        )
        return pager2.observable.map {
            it.map {follower->
                UserMapper.transform(follower)
            }
        }
    }
}