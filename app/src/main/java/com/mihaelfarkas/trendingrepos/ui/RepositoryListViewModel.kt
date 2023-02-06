package com.mihaelfarkas.trendingrepos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mihaelfarkas.core.domain.datamodel.DataResult
import com.mihaelfarkas.core.domain.usecase.FetchRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase
) : ViewModel() {

    private val _repositoryFlow = MutableStateFlow(RepositoryListUiState(isLoading = true))
    val repositoryFlow: StateFlow<RepositoryListUiState> = _repositoryFlow

    init {
        // Fetch initial data to display
        fetchNextRepositoriesPage()
    }

    private fun fetchNextRepositoriesPage() {
        viewModelScope.launch {
            fetchRepositoriesUseCase().onEach { dataResult ->
                _repositoryFlow.update {
                    val updatedItems = if (dataResult is DataResult.Success) {
                        LinkedHashSet(it.items + dataResult.data)
                    } else it.items

                    it.copy(
                        isLoading = dataResult is DataResult.Loading,
                        isError = dataResult is DataResult.Error,
                        items = updatedItems
                    )
                }
            }.collect()
        }
    }

    fun onRetryButtonClick() = fetchNextRepositoriesPage()

    fun onPageEndReached() = fetchNextRepositoriesPage()
}
