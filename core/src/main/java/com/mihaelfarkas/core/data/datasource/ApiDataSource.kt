package com.mihaelfarkas.core.data.datasource

import com.mihaelfarkas.core.data.model.RepositoryPageModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDataSource {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 0
    ): Call<RepositoryPageModel>
}
