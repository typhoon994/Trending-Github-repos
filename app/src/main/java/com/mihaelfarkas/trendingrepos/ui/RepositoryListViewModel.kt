package com.mihaelfarkas.trendingrepos.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mihaelfarkas.core.domain.FetchRepositoriesUseCase
import com.mihaelfarkas.core.domain.GetRepositoryFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    getRepositoryFlowUseCase: GetRepositoryFlowUseCase,
    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase
) : ViewModel() {

    val repositoryFlow = getRepositoryFlowUseCase.invoke()

    fun fetchRepos() {
        viewModelScope.launch {
            fetchRepositoriesUseCase.invoke()
        }
    }
}
