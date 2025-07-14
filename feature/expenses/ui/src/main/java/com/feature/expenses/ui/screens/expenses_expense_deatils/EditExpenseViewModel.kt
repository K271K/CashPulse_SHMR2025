package com.feature.expenses.ui.screens.expenses_expense_deatils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.usecase.GetExpenseCategoriesUseCase
import com.core.domain.usecase.GetTransactionByIdUseCase
import com.core.ui.models.CategoryPickerUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Provider

class EditExpenseViewModel @Inject constructor(
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val getExpenseByIdUseCase: GetTransactionByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditExpenseScreenUiState(isLoading = true))
    val uiState: StateFlow<EditExpenseScreenUiState> = _uiState.asStateFlow()

    fun initFromComposable(expenseId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                getExpenseCategoriesUseCase()
                    .onSuccess { categoriesList->
                        val result = getExpenseByIdUseCase.invoke(expenseId)
                        println(result)
                        val selectedCategory = categoriesList.find { it.name == result.category.name } ?: throw Exception("No category found")
                        val mappedCategoriesList = categoriesList.map {
                            CategoryPickerUiModel(
                                name = it.name,
                                emoji = it.emoji,
                                id = it.id
                            )
                        }
                        _uiState.update { it.copy(
                            isLoading = false,
                            selectedCategory = CategoryPickerUiModel(
                                name = selectedCategory.name,
                                id = selectedCategory.id,
                                emoji = selectedCategory.emoji
                            ),
                            categories = mappedCategoriesList,
                            amount = result.amount,
                            date = result.transactionDate,
                            time = result.transactionDate,
                            comment = result.comment
                        ) }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun selectCategory(category: CategoryPickerUiModel) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setAmount(amount: String) {
        _uiState.update { it.copy(amount = amount) }
    }

    fun setDate(selectedDate: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val formattedSelectedDate = sdf.format(Date(selectedDate))

        if (formattedSelectedDate.isNotEmpty()) {
            _uiState.update { it.copy(date = formattedSelectedDate) }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun setTime(time: TimePickerState) {
        val formattedTime = "${time.hour}:${time.minute} "
        _uiState.update { it.copy(time = formattedTime) }
    }

    fun setComment(comment: String) {
        _uiState.update { it.copy(comment = comment) }
    }
}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class EditExpenseViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<EditExpenseViewModel>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }

}