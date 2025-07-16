package com.feature.expenses.ui.screens.expenses_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.usecase.DeleteTransactionUseCase
import com.core.domain.usecase.GetExpenseCategoriesUseCase
import com.core.domain.usecase.GetTransactionByIdUseCase
import com.core.domain.usecase.UpdateTransactionUseCase
import com.core.domain.utils.formatCurrencyFromTextToSymbol
import com.core.domain.utils.formatISO8601ToDate
import com.core.domain.utils.formatISO8601ToTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModel и фабрика для неё находятся в одном файле.
 * Мне удобно так ориентироваться по коду.
 * Не вижу смысла разделять их по разным файлам.
 */

class EditExpenseViewModel @Inject constructor(
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val getExpenseByIdUseCase: GetTransactionByIdUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase
) : ViewModel() {

    private val _editExpenseScreenState: MutableStateFlow<EditExpenseScreenUiState> =
        MutableStateFlow(EditExpenseScreenUiState.Loading)
    val editExpenseScreenState = _editExpenseScreenState.asStateFlow()

    fun initWithExpenseId(expenseId: Int) {
        viewModelScope.launch {
            getExpenseByIdUseCase.invoke(
                transactionId = expenseId
            )
                .onSuccess { transaction ->
                    val formattedCurrency = formatCurrencyFromTextToSymbol(transaction.account.currency)
                    val formattedDate = formatISO8601ToDate(transaction.transactionDate)
                    val formattedTime = formatISO8601ToTime(transaction.transactionDate)
                    _editExpenseScreenState.value = EditExpenseScreenUiState.Loaded(
                        data = EditExpenseScreenData(
                            accountName = transaction.account.name,
                            categoryName = transaction.category.name,
                            amount = "${transaction.amount} $formattedCurrency",
                            expenseDate = formattedDate,
                            expenseTime = formattedTime,
                            comment = transaction.comment
                        )
                    )
                }
                .onFailure { e->
                    _editExpenseScreenState.value =
                        EditExpenseScreenUiState.Error(e.message.toString())
                }
        }
    }

    fun updateAmount(newAmount: String) {
        (_editExpenseScreenState.value as? EditExpenseScreenUiState.Loaded)?.let { currentState ->
            val updatedData = currentState.data.copy(amount = newAmount)
            _editExpenseScreenState.value = EditExpenseScreenUiState.Loaded(updatedData)
        }
    }
}

class EditExpenseViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<EditExpenseViewModel>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }

}