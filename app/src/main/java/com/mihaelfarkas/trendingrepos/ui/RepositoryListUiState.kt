package com.mihaelfarkas.trendingrepos.ui

import androidx.compose.runtime.Immutable
import com.mihaelfarkas.core.domain.datamodel.RepositoryDataModel

@Immutable // Needed because all the lists are considered as mutable structures in Compose
data class RepositoryListUiState(
    val isLoading: Boolean = false,
    val items: List<RepositoryDataModel> = emptyList(),
    val isError: Boolean = false
)
