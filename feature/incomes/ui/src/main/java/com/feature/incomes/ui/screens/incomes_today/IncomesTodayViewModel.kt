package com.feature.incomes.ui.screens.incomes_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.usecase.GetCurrencyUseCase
import com.feature.incomes.domain.usecase.GetTodayIncomesUseCase
import com.feature.incomes.ui.models.IncomesUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * Тут лежит сама ViewModel
 */
class IncomesTodayViewModel @Inject constructor(
    private val getTodayIncomesUseCase: GetTodayIncomesUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel() {

    private var _incomesTodayScreenState: MutableStateFlow<IncomesTodayScreenState> =
        MutableStateFlow(value = IncomesTodayScreenState.Loading)
    val incomesTodayScreenState = _incomesTodayScreenState.asStateFlow()

    init {
        fetchTodayIncomes(date = null)
    }

    fun fetchTodayIncomes(
        date: String?
    ) {
        viewModelScope.launch {
            _incomesTodayScreenState.value = IncomesTodayScreenState.Loading
            try {
                val currency = getCurrencyUseCase().getOrThrow()
                val listOfIncomes = getTodayIncomesUseCase().getOrThrow()
                val totalAmount = listOfIncomes.sumOf { income ->
                    income.amount.toDoubleOrNull() ?: 0.0
                }.toString()
                _incomesTodayScreenState.value = IncomesTodayScreenState.Loaded(
                    data = IncomesTodayScreenData(
                        incomes = listOfIncomes.map { domainIncomeModel ->
                            IncomesUiModel(
                                id = domainIncomeModel.id,
                                categoryName = domainIncomeModel.category.name,
                                amount = domainIncomeModel.amount,
                                currency = currency
                            )
                        },
                        totalAmount = totalAmount,
                        currency = currency
                    )
                )
            } catch (e: Exception) {
                _incomesTodayScreenState.value = IncomesTodayScreenState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }
}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class IncomesTodayViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<IncomesTodayViewModel>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}