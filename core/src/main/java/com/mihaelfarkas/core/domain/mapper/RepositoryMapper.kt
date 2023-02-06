package com.mihaelfarkas.core.domain.mapper

import com.mihaelfarkas.core.data.model.ApiResult
import com.mihaelfarkas.core.data.model.RepositoryModel
import com.mihaelfarkas.core.domain.datamodel.DataResult
import com.mihaelfarkas.core.domain.datamodel.RepositoryDataModel

object RepositoryMapper {

    private fun fromApiModel(model: RepositoryModel): RepositoryDataModel {
        return RepositoryDataModel(
            id = model.id,
            description = model.description ?: "",
            name = model.name,
            starCount = model.stargazersCount,
            ownerUsername = model.owner.login,
            ownerAvatarUrl = model.owner.avatarUrl
        )
    }

    fun fromApiResult(apiResult: ApiResult<RepositoryModel>): DataResult<RepositoryDataModel> {
        return when (apiResult) {
            is ApiResult.Success -> {
                val dataModels = apiResult.data.map { fromApiModel(it) }
                DataResult.Success(dataModels)
            }
            is ApiResult.Error -> DataResult.Error(apiResult.throwable)
            is ApiResult.Loading -> DataResult.Loading()
        }
    }
}
