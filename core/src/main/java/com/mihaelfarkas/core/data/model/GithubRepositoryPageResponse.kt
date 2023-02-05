package com.mihaelfarkas.core.data.model

import com.squareup.moshi.Json

data class GithubRepositoryPageResponse(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,

    val items: List<GithubRepository>
)
