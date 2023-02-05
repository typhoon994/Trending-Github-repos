package com.mihaelfarkas.core.data.repository

import android.util.Log
import com.mihaelfarkas.core.data.datasource.ApiDataSource
import com.mihaelfarkas.core.data.model.ApiResult
import com.mihaelfarkas.core.data.model.RepositoryModel
import com.mihaelfarkas.core.data.model.RepositoryPageModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(private val remoteApiDataSource: ApiDataSource) {
    private var page: Int = INITIAL_PAGE_NUMBER
    private var query: String = ""
    private val _repositoryFlow = MutableStateFlow<ApiResult<RepositoryModel>>(ApiResult.Success(emptyList()))

    val repositoryFlow: Flow<ApiResult<RepositoryModel>> = _repositoryFlow

    suspend fun fetchRepositories(query: String) {
        if (this.query != query) {
            // Reset flags if query changed
            this.query = query
            page = INITIAL_PAGE_NUMBER
        } else {
            // Just increment page if query is the same
            ++page
        }

        withContext(Dispatchers.IO) {
            _repositoryFlow.update { ApiResult.Loading(it.data) }

            remoteApiDataSource.searchRepositories(query = query, page = page.toString()).enqueue(object : Callback<RepositoryPageModel> {
                override fun onResponse(call: Call<RepositoryPageModel>, response: Response<RepositoryPageModel>) {
                    Log.d("TAG", "On response, success: ${response.isSuccessful}")
                    if (response.isSuccessful) {
                        val newData = response.body()?.items ?: emptyList()
                        _repositoryFlow.update {
                            ApiResult.Success(it.data + newData)
                        }
                    } else {
                        _repositoryFlow.update {
                            ApiResult.Error(Exception(ERROR_FORMAT.format(response.code())), it.data)
                        }
                    }
                }

                override fun onFailure(call: Call<RepositoryPageModel>, t: Throwable) {
                    Log.d("TAG", "On response, success: onFailure")
                    _repositoryFlow.update { ApiResult.Error(t, it.data) }
                }
            })
        }
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        private const val ERROR_FORMAT = "Response code: %s"
    }
}
