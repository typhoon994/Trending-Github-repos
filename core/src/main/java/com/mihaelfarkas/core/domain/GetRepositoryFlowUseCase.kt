package com.mihaelfarkas.core.domain

import com.mihaelfarkas.core.data.repository.Repository
import com.mihaelfarkas.core.domain.datamodel.DataResult
import com.mihaelfarkas.core.domain.datamodel.RepositoryDataModel
import com.mihaelfarkas.core.domain.mapper.RepositoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRepositoryFlowUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<DataResult<RepositoryDataModel>> {
        return repository.repositoryFlow.map {
            RepositoryMapper.fromApiResult(it)
        }
    }
}
