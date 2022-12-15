package com.octopus.domain.models


data class Follower(
    val id:Int,
    val login:String,
    val parent:String,
    val avatar_url: String
)