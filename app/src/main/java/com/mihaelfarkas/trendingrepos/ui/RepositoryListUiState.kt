package com.mihaelfarkas.trendingrepos.ui

import androidx.compose.runtime.Immutable
import com.mihaelfarkas.core.data.model.GithubRepository

@Immutable // Needed because all the lists are considered as mutable structures in Compose
data class RepositoryListUiState(
    val isLoading: Boolean = false,
    val items: List<GithubRepository> = emptyList(),
    val isError: Boolean = false
)