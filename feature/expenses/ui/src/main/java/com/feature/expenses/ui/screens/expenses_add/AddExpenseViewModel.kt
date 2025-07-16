package com.feature.expenses.ui.screens.expenses_add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.constants.CoreDomainConstants.ACCOUNT_ID
import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.usecase.CreateTransactionUseCase
import com.core.domain.usecase.GetExpenseCategoriesUseCase
import com.core.domain.utils.formatDateToISO8061
import com.core.ui.models.CategoryPickerUiModel
import com.feature.expenses.ui.screens.common.EditExpenseScreenUiState
import com.feature.expenses.ui.screens.common.TransactionCreationState
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

class AddExpenseViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditExpenseScreenUiState(isLoading = true))
    val uiState: StateFlow<EditExpenseScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getExpenseCategoriesUseCase()
                .onSuccess { categoriesList ->
                    val mappedCategoriesList = categoriesList.map {
                        CategoryPickerUiModel(
                            name = it.name,
                            emoji = it.emoji,
                            id = it.id
                        )
                    }
                    _uiState.update {
                        it.copy(
                            categories = mappedCategoriesList,
                            isLoading = false
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
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
        val formattedTime = String.format("%02d:%02d", time.hour, time.minute)
        _uiState.update { it.copy(time = formattedTime) }
    }

    fun setComment(comment: String) {
        _uiState.update { it.copy(comment = comment) }
    }

    fun validateAndCreateTransaction(
        onSuccess: () -> Unit,
        onValidationError: (String) -> Unit
    ) {
        viewModelScope.launch {
            // Валидация формы
            val validationErrors = _uiState.value.getValidationErrors()
            if (validationErrors.isNotEmpty()) {
                val errorMessage = validationErrors.values.first()
                onValidationError(errorMessage)
                return@launch
            }
            // Создание транзакции
            createTransaction(onSuccess)
        }
    }

    private fun createTransaction(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(transactionCreationState = TransactionCreationState.LOADING) }

            val formattedDate = formatDateToISO8061(
                date = _uiState.value.date,
                time = _uiState.value.time
            )
            createTransactionUseCase(
                CreateTransactionDomainModel(
                    accountId = ACCOUNT_ID,
                    categoryId = _uiState.value.selectedCategory?.id
                        ?: throw Exception("Category is not selected"),
                    amount = _uiState.value.amount,
                    transactionDate = formattedDate,
                    comment = _uiState.value.comment
                )
            )
                .onSuccess {
                    _uiState.update { it.copy(transactionCreationState = TransactionCreationState.SUCCESS) }
                    delay(1000)
                    onSuccess()
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            transactionCreationState = TransactionCreationState.ERROR,
                            error = e.message
                        )
                    }
                }
        }
    }

    fun retryTransaction(onSuccess: () -> Unit) {
        _uiState.update {
            it.copy(
                transactionCreationState = TransactionCreationState.LOADING,
                error = null
            )
        }
        validateAndCreateTransaction(onSuccess = onSuccess, onValidationError = {})
    }
}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class AddExpenseViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<AddExpenseViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}