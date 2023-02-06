package com.mihaelfarkas.core.domain.usecase

import com.mihaelfarkas.core.data.repository.Repository
import com.mihaelfarkas.core.domain.datamodel.DataResult
import com.mihaelfarkas.core.domain.datamodel.RepositoryDataModel
import com.mihaelfarkas.core.domain.mapper.RepositoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchRepositoriesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): Flow<DataResult<RepositoryDataModel>> {
        return repository.fetchRepositories(DEFAULT_QUERY).map {
            RepositoryMapper.fromApiResult(it)
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "created:>2023-01-01"
    }
}
