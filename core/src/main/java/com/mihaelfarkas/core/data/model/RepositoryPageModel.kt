package com.mihaelfarkas.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryPageModel(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    val items: List<RepositoryModel>
)
