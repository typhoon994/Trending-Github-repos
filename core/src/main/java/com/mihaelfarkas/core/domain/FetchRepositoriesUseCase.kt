package com.mihaelfarkas.core.domain

import com.mihaelfarkas.core.data.repository.Repository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FetchRepositoriesUseCase @Inject constructor(private val mutex: Mutex, private val repository: Repository) {

    suspend operator fun invoke() {
        // Use mutex to avoid fetching multiple times the same page
        return mutex.withLock { repository.fetchRepositories(DEFAULT_QUERY) }
    }

    companion object {
        private const val DEFAULT_QUERY = "created:>2023-01-01"
    }
}
