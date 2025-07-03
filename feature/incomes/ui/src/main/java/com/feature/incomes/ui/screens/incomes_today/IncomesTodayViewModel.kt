package com.feature.incomes.ui.screens.incomes_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.incomes.domain.usecase.GetTodayIncomesUseCase
import com.feature.incomes.ui.models.IncomesUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomesTodayViewModel @Inject constructor(
    private val getTodayIncomesUseCase: GetTodayIncomesUseCase
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
            getTodayIncomesUseCase.invoke(date = date)
                .onSuccess { listOfIncomes ->
                    val totalAmount = listOfIncomes.sumOf { income ->
                        income.amount.toDoubleOrNull() ?: 0.0
                    }.toString()
                    _incomesTodayScreenState.value = IncomesTodayScreenState.Loaded(
                        data = IncomesTodayScreenData(
                            incomes = listOfIncomes.map { domainIncomeModel ->
                                IncomesUiModel(
                                    id = domainIncomeModel.id,
                                    categoryName = domainIncomeModel.category.name,
                                    amount = domainIncomeModel.amount
                                )
                            },
                            totalAmount = "$totalAmount R"
                        )
                    )
                }
                .onFailure { error->
                    _incomesTodayScreenState.value = IncomesTodayScreenState.Error(
                        message = error.message ?: "Unknown error"
                    )
                }
        }

    }
}