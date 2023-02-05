package com.mihaelfarkas.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubUser(
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
)
