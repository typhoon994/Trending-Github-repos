package com.mihaelfarkas.core.domain

import android.util.Log
import com.mihaelfarkas.core.data.datasource.ApiResult
import com.mihaelfarkas.core.data.model.GithubRepository
import com.mihaelfarkas.core.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRepositoryFlowUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<ApiResult<GithubRepository>> {
        return repository.repositoryFlow.map {
            // TODO Add mapping to domain layer
            Log.d("TAG", "Data flow size: ${it.data.size}")
            it
        }
    }
}
