package com.feature.expenses.ui.screens.expenses_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.usecase.CreateTransactionUseCase
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class AddExpenseViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase
) : ViewModel() {

    init {
//        viewModelScope.launch {
//            createTransactionUseCase.invoke(
//                CreateTransactionDomainModel(
//                    accountId = 211,
//                    amount = "500.00",
//                    categoryId = 7,
//                    comment = "",
//                    transactionDate = "2025-07-12T12:00:00.000Z",
//                )
//            )
//        }
    }
}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class AddExpenseViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<AddExpenseViewModel>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}