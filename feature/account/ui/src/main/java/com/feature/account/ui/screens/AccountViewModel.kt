package com.feature.account.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.models.AccountDomainModel
import com.feature.account.domain.usecase.GetAccountUseCase
import com.feature.account.domain.usecase.UpdateAccountUseCase
import com.feature.account.ui.models.AccountUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        // Автоматическая загрузка счёта при создании ViewModel
        viewModelScope.launch {
            try {
                val account = getAccountUseCase.invoke() // Получаем первый счёт
                _uiState.value = AccountScreenState.Loaded(
                    data = AccountUiMapper.toUiModel(account)
                )
            } catch (e: Exception) {
                _uiState.value = AccountScreenState.Error(
                    message = "Ошибка загрузки счёта: ${e.message}"
                )
            }
        }
    }

    fun loadAccount(account: AccountDomainModel) {
        _uiState.value = AccountScreenState.Loaded(
            data = AccountUiMapper.toUiModel(account)
        )
    }

    fun updateAccount(name: String, currency: String) {
        viewModelScope.launch {
            try {
                _uiState.value = AccountScreenState.Loading
                val currentState = (_uiState.value as? AccountScreenState.Loaded)?.data
                    ?: AccountScreenData("", "RUB", "0.0")
                val updatedAccount = currentState.toDomainAccount()
                    .copy(name = name, currency = currency)
                val result = updateAccountUseCase(updatedAccount)
                _uiState.value = AccountScreenState.Loaded(
                    data = AccountUiMapper.toUiModel(result)
                )
            } catch (e: Exception) {
                _uiState.value = AccountScreenState.Error(
                    message = "Ошибка обновления: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        if (_uiState.value is AccountScreenState.Error) {
            val currentData = (_uiState.value as? AccountScreenState.Loaded)?.data
                ?: AccountScreenData("", "RUB", "0.0")
            _uiState.value = AccountScreenState.Loaded(data = currentData)
        }
    }
}