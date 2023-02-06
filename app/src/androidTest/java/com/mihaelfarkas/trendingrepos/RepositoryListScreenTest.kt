package com.mihaelfarkas.trendingrepos

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.mihaelfarkas.core.data.datasource.ApiDataSource
import com.mihaelfarkas.core.data.di.DataSourceModule
import com.mihaelfarkas.core.data.model.RepositoryModel
import com.mihaelfarkas.core.data.model.RepositoryPageModel
import com.mihaelfarkas.core.data.model.UserModel
import com.mihaelfarkas.trendingrepos.ui.RepositoryListScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

@UninstallModules(DataSourceModule::class)
@HiltAndroidTest
class RepositoryListScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Module
    @InstallIn(SingletonComponent::class)
    object TestModule {
        private val testDataSource = mockk<ApiDataSource>()
        private val successCall = mockk<Call<RepositoryPageModel>>()
        private val successResponse = mockk<Response<RepositoryPageModel>>()

        @Provides
        fun provideApiDataSource(): ApiDataSource {
            val fakeModel = RepositoryModel(0, REPO_NAME, "Test", 1, UserModel("Test", ""))

            every { testDataSource.searchRepositories(query = any(), sort = any(), order = any(), perPage = any(), page = any()) } returns successCall
            every { successCall.execute() } returns successResponse
            every { successResponse.isSuccessful } returns true
            every { successResponse.body() } returns RepositoryPageModel(true, listOf(fakeModel))

            return testDataSource
        }

        @Provides
        fun provideMutex() = Mutex()

        @Provides
        fun provideCoroutineContext(): CoroutineDispatcher = Dispatchers.IO
    }

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            RepositoryListScreen()
        }
    }

    @Test
    fun displayRepositoryListTest() {
        composeTestRule.waitUntil {
            // Wait until toolbar is displayed
            composeTestRule.onAllNodesWithText(getString(R.string.app_name)).fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithText(REPO_NAME).assertExists("Repo name was not displayed")
    }

    private fun getString(@StringRes stringId: Int) = composeTestRule.activity.getString(stringId)

    companion object {
        private const val REPO_NAME = "Test repo"
    }
}
