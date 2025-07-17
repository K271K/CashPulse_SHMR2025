package com.feature.incomes.ui.screens.incomes_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.constants.CoreDomainConstants.ACCOUNT_ID
import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.usecase.DeleteTransactionUseCase
import com.core.domain.usecase.GetIncomeCategoriesUseCase
import com.core.domain.usecase.GetTransactionByIdUseCase
import com.core.domain.usecase.UpdateTransactionUseCase
import com.core.domain.utils.formatDateFromLongToHuman
import com.core.domain.utils.formatDateToISO8061
import com.core.domain.utils.formatISO8601ToDate
import com.core.domain.utils.formatISO8601ToTime
import com.core.ui.models.CategoryPickerUiModel
import com.feature.incomes.ui.screens.common.IncomesEditScreenUiState
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

class IncomesEditScreenViewModel @Inject constructor(
    private val getIncomesCategoriesUseCase: GetIncomeCategoriesUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(IncomesEditScreenUiState(isLoading = true))
    val uiState: StateFlow<IncomesEditScreenUiState> = _uiState.asStateFlow()

    fun initWithId(incomeId: Int) {
        viewModelScope.launch {
            getIncomesCategoriesUseCase()
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

            getTransactionByIdUseCase(transactionId = incomeId)
                .onSuccess { loadedTransaction ->
                    val selectedCategory =
                        _uiState.value.categories.find { it.name == loadedTransaction.category.name }
                    val formattedDate = formatISO8601ToDate(loadedTransaction.transactionDate)
                    val formattedTime = formatISO8601ToTime(loadedTransaction.transactionDate)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            selectedCategory = selectedCategory,
                            currency = loadedTransaction.account.currency,
                            accountName = loadedTransaction.account.name,
                            categoryName = selectedCategory!!.name,
                            amount = loadedTransaction.amount,
                            expenseDate = formattedDate,
                            expenseTime = formattedTime,
                            comment = loadedTransaction.comment,
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

    fun validateAndUpdateTransaction(
        expenseId: Int,
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
            updateTransactionUseCase(
                transactionId = expenseId,
                transaction = domainModelTransaction
            )
                .onSuccess {
                    _uiState.update {
                        it.copy(success = true, isLoading = false, error = null)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(error = e.message, isLoading = false)
                    }
                }
        }
    }

    fun deleteTransaction(expenseId: Int) {
        viewModelScope.launch {
            deleteTransactionUseCase(transactionId = expenseId)
                .onSuccess {
                    _uiState.update {
                        it.copy(success = true, isLoading = false, error = null)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(error = e.message, isLoading = false)
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


}

class IncomesEditScreenViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<IncomesEditScreenViewModel>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }

}