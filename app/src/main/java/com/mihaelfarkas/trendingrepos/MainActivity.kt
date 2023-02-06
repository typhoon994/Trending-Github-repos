package com.mihaelfarkas.trendingrepos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mihaelfarkas.trendingrepos.ui.RepositoryListScreen
import com.mihaelfarkas.trendingrepos.ui.theme.TrendingGithubReposTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendingGithubReposTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RepositoryListScreen()
                }
            }
        }
    }
}
