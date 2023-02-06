package com.mihaelfarkas.core.domain.usecase

import com.mihaelfarkas.core.data.model.ApiResult
import com.mihaelfarkas.core.data.model.RepositoryModel
import com.mihaelfarkas.core.data.repository.Repository
import com.mihaelfarkas.core.domain.datamodel.DataResult
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchRepositoriesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: Repository

    private val fakeFlow: Flow<ApiResult<RepositoryModel>> = flow {
        emit(ApiResult.Loading())
        emit(ApiResult.Success(emptyList()))
    }

    @Before
    fun setup() {
        coEvery { repository.fetchRepositories(validQuery) } returns fakeFlow
    }

    @Test
    fun `Fetch repositories success`() {
        val useCaseUnderTest = FetchRepositoriesUseCase(repository)
        val inputDate = FetchRepositoriesUseCase.queryDateFormat.parse(VALID_DATE) ?: error("Date can't be parsed")

        runTest {
            val resultFlow = useCaseUnderTest.invoke(inputDate)
            advanceUntilIdle()

            val resultValues = resultFlow.take(2).toList()
            assertTrue(resultValues[0] is DataResult.Loading)
            assertTrue(resultValues[1] is DataResult.Success)
        }
    }

    companion object {
        private const val VALID_DATE = "2023-01-31"
        private val validQuery = FetchRepositoriesUseCase.QUERY_FORMAT.format("2023-01-01")
    }
}
