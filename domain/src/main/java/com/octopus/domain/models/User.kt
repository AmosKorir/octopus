package com.octopus.domain.models

data class User(
    val avatar_url: String,
    val bio: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String,
    val name: String,
    val public_gists: Int,
    val public_repos: Int,
    val url: String
)