package com.mihaelfarkas.trendingrepos.ui

import androidx.compose.runtime.Immutable
import com.mihaelfarkas.core.domain.datamodel.RepositoryDataModel

@Immutable // Needed because all the lists/sets are considered as mutable structures in Compose
data class RepositoryListUiState(
    val isLoading: Boolean = false,
    val items: LinkedHashSet<RepositoryDataModel> = LinkedHashSet(INITIAL_CAPACITY), // Use Set to avoid duplicates, use LinkedHashSet to keep sorted order
    val isError: Boolean = false
) {
    companion object {
        private const val INITIAL_CAPACITY = 30
    }
}
