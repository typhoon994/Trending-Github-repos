package com.mihaelfarkas.core.domain.usecase

import com.mihaelfarkas.core.data.repository.Repository
import com.mihaelfarkas.core.domain.datamodel.DataResult
import com.mihaelfarkas.core.domain.datamodel.RepositoryDataModel
import com.mihaelfarkas.core.domain.mapper.RepositoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FetchRepositoriesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): Flow<DataResult<RepositoryDataModel>> {
        calendar.apply {
            time = Date() // Reset calendar to current time
            add(Calendar.DAY_OF_MONTH, DATE_OFFSET)
        }
        val timestamp =  dateFormat.format(calendar.time)
        val query = QUERY_FORMAT.format(timestamp)
        return repository.fetchRepositories(query).map {
            RepositoryMapper.fromApiResult(it)
        }
    }

    companion object {
        private const val QUERY_FORMAT = "created:>%s"
        private const val DATE_OFFSET = -30 // Get repos that were created in the last 30 days.

        private val calendar = Calendar.getInstance()
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    }
}
