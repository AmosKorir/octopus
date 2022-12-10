package com.octopus.data.api

import com.octopus.data.models.api.FollowerApiResponse
import com.octopus.data.models.api.UserApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("users/{username}")
    fun getUsers(@Path("username") username: String): Single<UserApiResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String,@Query("page") page:Int): Single<List<FollowerApiResponse>>
}