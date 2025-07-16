package com.feature.expenses.ui.screens.expenses_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.constants.CoreDomainConstants.ACCOUNT_ID
import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.usecase.CreateTransactionUseCase
import com.core.domain.usecase.GetAccountUseCase
import com.core.domain.usecase.GetExpenseCategoriesUseCase
import com.core.domain.utils.formatCurrencyFromTextToSymbol
import com.core.domain.utils.formatDateFromLongToHuman
import com.core.domain.utils.formatDateToISO8061
import com.core.ui.models.CategoryPickerUiModel
import com.feature.expenses.ui.screens.common.EditExpenseScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModel и фабрика для неё находятся в одном файле.
 * Мне удобно так ориентироваться по коду.
 * Не вижу смысла разделять их по разным файлам.
 */

class AddExpenseScreenViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditExpenseScreenUiState(isLoading = false))
    val uiState: StateFlow<EditExpenseScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getExpenseCategoriesUseCase()
                .onSuccess { categories ->
                    val mappedCategories = categories.map {
                        CategoryPickerUiModel(
                            name = it.name,
                            id = it.id,
                            emoji = it.emoji
                        )
                    }
                    _uiState.update {
                        it.copy(categories = mappedCategories)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message)
                        return@launch
                    }
                }

            getAccountUseCase(ACCOUNT_ID)
                .onSuccess { account ->
                    _uiState.update {
                        it.copy(
                            accountName = account.name,
                            currency = formatCurrencyFromTextToSymbol(account.currency)
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message)
                        return@launch
                    }
                }
        }
    }

    fun updateAmount(amount: String) {
        _uiState.update {
            it.copy(amount = amount)
        }
    }

    fun updateDate(dateInMillis: Long) {
        val formattedDate = formatDateFromLongToHuman(date = dateInMillis)
        _uiState.update {
            it.copy(expenseDate = formattedDate)
        }
    }

    fun updateCategory(category: CategoryPickerUiModel) {
        _uiState.update {
            it.copy(selectedCategory = category, categoryName = category.name)
        }
    }

    fun updateTime(hour: Int, minute: Int) {
        val formattedTime = String.format("%02d:%02d", hour, minute)
        _uiState.update {
            it.copy(expenseTime = formattedTime)
        }
    }

    fun updateComment(comment: String) {
        _uiState.update {
            it.copy(comment = comment)
        }
    }

    fun validateAndCreateTransaction(
        onValidationError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val validationErrors = _uiState.value.getValidationErrors()
            if (validationErrors.isNotEmpty()) {
                val errorMessage = validationErrors.values.first()
                onValidationError(errorMessage)
                return@launch
            }
            _uiState.update {
                it.copy(isLoading = true)
            }
            val dateISOFormatted = formatDateToISO8061(
                date = _uiState.value.expenseDate,
                time = _uiState.value.expenseTime
            )
            val domainModelTransaction = CreateTransactionDomainModel(
                accountId = ACCOUNT_ID,
                categoryId = _uiState.value.selectedCategory!!.id,
                amount = _uiState.value.amount,
                transactionDate = dateISOFormatted,
                comment = _uiState.value.comment
            )
            createTransactionUseCase(
                transaction = domainModelTransaction
            )
                .onSuccess {
                    _uiState.update {
                        it.copy(success = true, isLoading = false)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(error = e.message, isLoading = false)
                    }
                }
        }
    }
}

class AddExpenseViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<AddExpenseScreenViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}