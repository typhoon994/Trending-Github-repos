package com.mihaelfarkas.core.domain.datamodel

data class RepositoryDataModel(
    val id: Int,
    val name: String,
    val description: String,
    val starCount: Int,
    val ownerUsername: String,
    val ownerAvatarUrl: String
)
