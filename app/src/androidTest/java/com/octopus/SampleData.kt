package com.octopus

import androidx.paging.PagingData
import com.octopus.domain.models.Follower
import com.octopus.domain.models.Following
import com.octopus.domain.models.User

object SampleData {
    val user = User(
        "https://avatars.githubusercontent.com/u/17925960?v=4",
        "Senior Android Engineer",
        "@safaricom",
        113,
        54,
        17925960,
        "Nairobi",
        "Korir Amos",
        2, 5,
        "https://api.github.com/users/AmosKorir"
    )

    val followers = ArrayList<Follower>()
    val following = ArrayList<Following>()
    lateinit var pagingDataFollower: PagingData<Follower>
    lateinit var pagingDataFollowing: PagingData<Following>

    init {
        for (i in 1..20) {
            followers.add(Follower(i, "username_$i", "parent","organization_$i"))
            following.add(Following(i, "username_$i", "parent","organization_$i"))
        }
        pagingDataFollower = PagingData.from(followers)
        pagingDataFollowing = PagingData.from(following)
    }
}