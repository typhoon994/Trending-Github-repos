package com.mihaelfarkas.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepository(
    val id: Int,
    val name: String,
    val description: String?,
    @Json(name = "stargazers_count")
    val stargazersCount: Int,
    val owner: GithubUser
)
