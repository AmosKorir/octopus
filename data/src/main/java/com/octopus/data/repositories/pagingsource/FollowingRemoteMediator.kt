package com.octopus.data.repositories.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.octopus.data.api.UserApi
import com.octopus.data.mappers.UserMapper
import com.octopus.data.models.db.FollowerDBModel
import com.octopus.data.models.db.FollowingDBModel
import com.octopus.data.models.db.RemoteKeyDbModel
import com.octopus.data.sql.dao.FollowerDao
import com.octopus.data.sql.dao.FollowingDao
import com.octopus.data.sql.dao.RemoteKeysDao
import com.octopus.domain.models.Follower
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class FollowingRemoteMediator(
    private val userApi: UserApi,
    private val remoteKeysDao: RemoteKeysDao,
    private val followingDao: FollowingDao,
    private val username: String
) : RxRemoteMediator<Int, FollowingDBModel>() {
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, FollowingDBModel>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextPage?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevPage ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextPage ?: INVALID_PAGE
                    }
                }
            }.flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    userApi.getFollowing(username, page)
                        .subscribeOn(Schedulers.io())
                        .flatMapPublisher {Flowable.fromIterable(it) }
                        .map { UserMapper.transform(username,it) }
                        .toList()
                        .map { followers ->
                            insertToDb(followers, loadType, page)
                        }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = false) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
    }

    private fun insertToDb(followers: List<Follower>, loadType: LoadType, page: Int): List<Follower> {

        if (loadType==LoadType.REFRESH){
            // clear the load for that username
           // octopusDatabase.remoteKeysDao().deleteAllRemoteKeys()
        }

        val prevKey = if (page == 1) null else page - 1
        val nextKey =  page + 1
        val keys = followers.map {
            RemoteKeyDbModel(it.id,prevKey,nextKey)
        }

        for (key in keys){
           remoteKeysDao.insertKey(key)
        }

        for (fw in followers){
          followingDao.insertFollowing(FollowingDBModel(fw.id,fw.login,username,fw.avatar_url))
        }

        return followers
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int,FollowingDBModel>): RemoteKeyDbModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
           remoteKeysDao.getByIDRemoteKeys(repo.id).blockingGet()[0]
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, FollowingDBModel>): RemoteKeyDbModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { follower ->
             remoteKeysDao.getByIDRemoteKeys(follower.id).blockingGet()[0]
            }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FollowingDBModel>): RemoteKeyDbModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
               remoteKeysDao.getByIDRemoteKeys(id).blockingGet()[0]
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }

}