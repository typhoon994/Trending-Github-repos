package com.mihaelfarkas.trendingrepos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mihaelfarkas.core.data.model.GithubRepository
import com.mihaelfarkas.trendingrepos.R
import com.mihaelfarkas.trendingrepos.ui.theme.PaddingExtraSmall
import com.mihaelfarkas.trendingrepos.ui.theme.PaddingNormal
import com.mihaelfarkas.trendingrepos.ui.theme.PaddingSmall
import com.mihaelfarkas.trendingrepos.ui.theme.SizeLarge
import com.mihaelfarkas.trendingrepos.ui.theme.SizeNormal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListScreen(viewModel: RepositoryListViewModel = hiltViewModel()) {
    val uiState by viewModel.repositoryFlow.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(stringResource(id = R.string.app_name))
        })
    }) { paddingValues ->
        RepositoryList(modifier = Modifier.padding(paddingValues), uiState = uiState)
    }
}

@Composable
private fun RepositoryList(modifier: Modifier, uiState: RepositoryListUiState) {
    LazyColumn(modifier = modifier) {
        items(uiState.items, key = { it.id }) {
            RepositoryItem(item = it)
        }
        if (uiState.isLoading) item {
            ListLoader()
        }
        if (uiState.isError) item {
            RetryButton()
        }
    }
}

@Composable
private fun RepositoryItem(item: GithubRepository) {
    Card(
        modifier = Modifier
            .padding(horizontal = PaddingNormal, vertical = PaddingSmall)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(PaddingSmall)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleSmall
            )
            if (!item.description.isNullOrBlank()) {
                Text(
                    text = item.description ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(modifier = Modifier.padding(top = PaddingSmall)) {
                Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = PaddingExtraSmall),
                    text = item.stargazersCount.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = PaddingSmall)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(SizeLarge)
                        .clip(CircleShape),
                    model = item.owner.avatarUrl,
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.padding(horizontal = PaddingExtraSmall),
                    text = item.owner.login,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun ListLoader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingSmall),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(SizeNormal))
    }
}

@Composable
private fun RetryButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingSmall),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { /*TODO*/ }) {
//            Icon()
        }
    }
}
