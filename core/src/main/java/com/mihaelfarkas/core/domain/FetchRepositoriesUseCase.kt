package com.mihaelfarkas.core.domain

import com.mihaelfarkas.core.data.repository.Repository
import javax.inject.Inject

class FetchRepositoriesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke() {
        return repository.fetchRepositories(DEFAULT_QUERY)
    }

    companion object {
        private const val DEFAULT_QUERY = "created:>2023-01-01"
    }
}
