package com.octopus.data.mappers

import com.octopus.data.models.api.FollowerApiResponse
import com.octopus.data.models.api.UserApiResponse
import com.octopus.domain.models.Follower
import com.octopus.domain.models.User

object UserMapper {
    fun transform(userApiResponse: UserApiResponse): User {
        with(userApiResponse) {
            return User(
                avatarUrl,
                bio,
                company,
                followers,
                following,
                id,
                location,
                name,
                publicGists,
                publicRepos,
                followersUrl
            )
        }
    }

    fun transform(followerApiResponse: FollowerApiResponse): Follower {
        with(followerApiResponse) {
            return Follower(
                login,
                avatarUrl,
            )
        }
    }
}