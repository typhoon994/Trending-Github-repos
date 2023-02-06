package com.mihaelfarkas.core.data.repository

import com.mihaelfarkas.core.data.datasource.ApiDataSource
import com.mihaelfarkas.core.data.model.ApiResult
import com.mihaelfarkas.core.data.model.RepositoryPageModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var testApiDataSource: ApiDataSource

    @MockK
    lateinit var successCall: Call<RepositoryPageModel>

    @MockK
    lateinit var successResponse: Response<RepositoryPageModel>

    @MockK
    lateinit var failureCall: Call<RepositoryPageModel>

    @MockK
    lateinit var failureResponse: Response<RepositoryPageModel>

    lateinit var repositoryUnderTest: Repository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        every { testApiDataSource.searchRepositories(VALID_QUERY, any(), any(), any(), any()) } returns successCall
        every { successCall.execute() } returns successResponse
        every { successResponse.isSuccessful } returns true
        every { successResponse.body() } returns RepositoryPageModel(true, emptyList())

        every { testApiDataSource.searchRepositories(INVALID_QUERY, any(), any(), any(), any()) } returns failureCall
        every { failureCall.execute() } returns failureResponse
        every { failureResponse.isSuccessful } returns false
        every { failureResponse.message() } returns ERROR_MESSAGE

        repositoryUnderTest = Repository(testApiDataSource, Mutex(), testDispatcher)
    }

    @Test
    fun `Fetch repositories successfully`() = runTest(testDispatcher) {
        val resultFlow = repositoryUnderTest.fetchRepositories(VALID_QUERY)
        advanceUntilIdle()

        val resultValues = resultFlow.take(2).toList()
        Assert.assertTrue(resultValues[0] is ApiResult.Loading)
        Assert.assertTrue(resultValues[1] is ApiResult.Success)
    }

    @Test
    fun `Fetch repositories with error`() = runTest(testDispatcher) {
        val resultFlow = repositoryUnderTest.fetchRepositories(INVALID_QUERY)
        advanceUntilIdle()

        val resultValues = resultFlow.take(2).toList()
        Assert.assertTrue(resultValues[0] is ApiResult.Loading)
        Assert.assertTrue(resultValues[1] is ApiResult.Error)
        Assert.assertEquals(ERROR_MESSAGE, (resultValues[1] as ApiResult.Error).throwable.message)
    }

    companion object {
        private const val VALID_QUERY = "validQuery"
        private const val INVALID_QUERY = "invalidQuery"

        private const val ERROR_MESSAGE = "errorMessage"
    }
}
