package com.octopus.data.mappers

import com.octopus.data.models.api.FollowerApiResponse
import com.octopus.data.models.api.FollowingApiResponse
import com.octopus.data.models.api.UserApiResponse
import com.octopus.data.models.db.FollowerDBModel
import com.octopus.data.models.db.FollowingDBModel
import com.octopus.data.models.db.UserDbModel
import com.octopus.domain.OctConstants
import com.octopus.domain.models.Follower
import com.octopus.domain.models.Following
import com.octopus.domain.models.User

object UserMapper {
    fun transform(userApiResponse: UserApiResponse): User {
        with(userApiResponse) {
            return User(
                avatarUrl,
                bio ?: OctConstants.EMPTY_STRING,
                company ?: OctConstants.EMPTY_STRING,
                followers,
                following,
                id,
                location,
                name ?: userApiResponse.login,
                publicGists,
                publicRepos,
                followersUrl
            )
        }
    }

    fun transform(username: String, followerApiResponse: FollowerApiResponse): Follower {
        with(followerApiResponse) {
            return Follower(
                id,
                login,
                username,
                avatarUrl,
            )
        }
    }

    fun transform(username: String, followingApiResponse: FollowingApiResponse): Follower {
        with(followingApiResponse) {
            return Follower(
                id,
                login,
                username,
                avatarUrl,
            )
        }
    }

    fun transform(followerDBModel: FollowerDBModel): Follower {
        with(followerDBModel) {
            return Follower(this.id, this.name, this.following, this.avatar_url)
        }

    }

    fun transform(followingDBModel: FollowingDBModel): Following {
        with(followingDBModel) {
            return Following(this.id, this.name, this.followBy, this.avatar_url)
        }

    }

    fun transform(userDbModel: UserDbModel): User {
        with(userDbModel) {
            return User(
                avatar_url,
                bio,
                company,
                followers,
                following,
                id,
                location,
                name,
                public_gists,
                public_repos,
                url
            )
        }
    }

    fun transform(user: User):UserDbModel {
        with(user) {
            return UserDbModel(
                id,
                avatar_url,
                bio,
                company,
                followers,
                following,
                location,
                name,
                public_gists,
                public_repos,
                url
            )
        }
    }
}