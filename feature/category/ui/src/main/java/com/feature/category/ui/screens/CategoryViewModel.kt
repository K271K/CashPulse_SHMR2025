package com.feature.category.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.usecase.GetCategoriesUseCase
import com.feature.category.ui.models.CategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * Тут лежит сама ViewModel
 */
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private var _categoriesScreenState: MutableStateFlow<CategoriesScreenState> =
        MutableStateFlow(CategoriesScreenState.Loading)
    val categoriesScreenState = _categoriesScreenState.asStateFlow()

    private var _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        fetchCategories()
        observeSearchQuery()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categoriesScreenState.value = CategoriesScreenState.Loading
            getCategoriesUseCase.invoke()
                .onSuccess { listOfDomainCategories->
                    val initialData = CategoriesScreenData(
                        categories = listOfDomainCategories.map { domainCategory ->
                            CategoryUiModel(
                                id = domainCategory.id,
                                name = domainCategory.name,
                                icon = domainCategory.emoji
                            )
                        }
                    )
                    _categoriesScreenState.value = CategoriesScreenState.Loaded(
                        data = initialData
                    )
                }
                .onFailure {
                    _categoriesScreenState.value = CategoriesScreenState.Error(
                        message = it.message ?: "Unknown error"
                    )
                }
        }
    }

    @OptIn(kotlinx.coroutines.FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery.debounce(300).collect { query ->
                filterCategories(query)
            }
        }
    }

    private fun filterCategories(query: String) {
        val currentState = (_categoriesScreenState.value as? CategoriesScreenState.Loaded)?.data
        currentState?.let {
            val filtered = if (query.isBlank()) {
                it.categories
            } else {
                it.categories.filter { category ->
                    category.name.contains(query, ignoreCase = true)
                }
            }
            _categoriesScreenState.value = CategoriesScreenState.Loaded(
                data = it.copy(filteredCategories = filtered)
            )
        }
    }

    val onSearchQueryChanged: (String) -> Unit = { query ->
        _searchQuery.value = query
    }

}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class CategoryViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<CategoryViewModel>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}