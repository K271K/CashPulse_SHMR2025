 package com.feature.category.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemWithLeadIcon
import com.core.ui.components.MyLoadingIndicator
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun CategoryScreen(
    viewModelFactory: CategoryViewModelFactory
) {
    val viewModel: CategoryViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.categoriesScreenState.collectAsStateWithLifecycle()
    val searchQuery = ""
    CategoryScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = {}
    )
}

@Composable
fun CategoryScreenContent(
    uiState: CategoriesScreenState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мои статьи",
        )
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = { Text("Найти статью") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )
        when (uiState) {
            is CategoriesScreenState.Loading -> {
                MyLoadingIndicator()
            }

            is CategoriesScreenState.Error -> {
                MyErrorBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = uiState.message
                )
            }

            is CategoriesScreenState.Loaded -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    items(uiState.data.filteredCategories, key = { it.id }) {
                        MyListItemWithLeadIcon(
                            modifier = Modifier
                                .height(70.dp),
                            icon = it.icon,
                            iconBg = GreenLight,
                            content = {
                                Text(text = it.name)
                            },
                            trailContent = {

                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}