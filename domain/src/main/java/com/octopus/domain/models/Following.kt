package com.octopus.domain.models

data class Following(
    val id:Int,
    val login:String,
    val parent:String,
    val avatar_url: String
)