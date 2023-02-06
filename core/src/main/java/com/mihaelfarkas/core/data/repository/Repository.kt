package com.mihaelfarkas.core.data.repository

import com.mihaelfarkas.core.data.datasource.ApiDataSource
import com.mihaelfarkas.core.data.model.ApiResult
import com.mihaelfarkas.core.data.model.RepositoryModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    private val remoteApiDataSource: ApiDataSource,
    private val mutex: Mutex,
    private val workerContext: CoroutineDispatcher
) {
    // Used to keep track of current page
    private var page: Int = INITIAL_PAGE_NUMBER
    private var query: String = ""

    suspend fun fetchRepositories(query: String): Flow<ApiResult<RepositoryModel>> = flow {
        // Use mutex to avoid fetching multiple times same page
        mutex.lock()
        when {
            this@Repository.query != query -> {
                // Reset flags if query changed
                this@Repository.query = query
                page = INITIAL_PAGE_NUMBER
            }
            page == END_OF_PAGE -> {
                // End of page reached, do nothing
                mutex.unlock()
                return@flow
            }
            else -> {
                // Just increment page if query is the same
                ++page
            }
        }

        emit(ApiResult.Loading())

        try {
            val response = remoteApiDataSource.searchRepositories(query = query, perPage = PER_PAGE_COUNT, page = page).execute()
            if (response.isSuccessful) {
                val newData = response.body()?.items ?: emptyList()
                emit(ApiResult.Success(newData))
                if (newData.size < PER_PAGE_COUNT) page = END_OF_PAGE
            } else {
                emit(ApiResult.Error(Exception(response.message())))
            }
        } catch (ioException: IOException) {
            emit(ApiResult.Error(ioException))
        }
        mutex.unlock()
    }.flowOn(workerContext)

    companion object {
        private const val PER_PAGE_COUNT = 30
        private const val INITIAL_PAGE_NUMBER = 1
        private const val END_OF_PAGE = -1
    }
}
