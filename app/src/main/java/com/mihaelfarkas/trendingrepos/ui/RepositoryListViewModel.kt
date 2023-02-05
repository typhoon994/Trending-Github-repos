package com.mihaelfarkas.trendingrepos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mihaelfarkas.core.domain.FetchRepositoriesUseCase
import com.mihaelfarkas.core.domain.GetRepositoryFlowUseCase
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
        RepositoryListUiState(items = it.data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        initialValue = RepositoryListUiState(isLoading = true)
    )

    init {
        // Fetch initial data to display
        viewModelScope.launch {
            fetchRepositoriesUseCase.invoke()
        }
    }

    companion object {
        private const val STOP_TIMEOUT = 5000L
    }
}
