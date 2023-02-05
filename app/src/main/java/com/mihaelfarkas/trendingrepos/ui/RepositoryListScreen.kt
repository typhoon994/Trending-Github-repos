package com.mihaelfarkas.trendingrepos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RepositoryListScreen(viewModel: RepositoryListViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.fetchRepos()
    }
}