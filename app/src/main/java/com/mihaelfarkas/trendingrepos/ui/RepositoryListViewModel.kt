package com.mihaelfarkas.trendingrepos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mihaelfarkas.core.domain.FetchRepositoriesUseCase
import com.mihaelfarkas.core.domain.GetRepositoryFlowUseCase
import com.mihaelfarkas.core.domain.datamodel.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    getRepositoryFlowUseCase: GetRepositoryFlowUseCase,
    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase
) : ViewModel() {

    val repositoryFlow = getRepositoryFlowUseCase().map {
        RepositoryListUiState(
            isLoading = it is DataResult.Loading,
            isError = it is DataResult.Error,
            items = it.data
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        initialValue = RepositoryListUiState(isLoading = true)
    )

    init {
        // Fetch initial data to display
        fetchNextRepositoriesPage()
    }

    private fun fetchNextRepositoriesPage() {
        viewModelScope.launch {
            fetchRepositoriesUseCase.invoke()
        }
    }

    fun onRetryButtonClick() = fetchNextRepositoriesPage()

    fun onPageEndReached() = fetchNextRepositoriesPage()

    companion object {
        private const val STOP_TIMEOUT = 5000L
    }
}
