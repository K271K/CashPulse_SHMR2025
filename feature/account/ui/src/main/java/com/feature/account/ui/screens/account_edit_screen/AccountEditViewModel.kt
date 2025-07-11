package com.feature.account.ui.screens.account_edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.models.AccountDomainModel
import com.feature.account.domain.usecase.GetAccountUseCase
import com.feature.account.domain.usecase.UpdateAccountUseCase
import com.feature.account.ui.screens.accounts_screen.AccountsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

data class EditAccountUiState(
    val id: Int = 0,                  // id аккаунта
    val name: String = "",            // новое имя
    val balance: String = "",         // новый баланс (стринг, чтобы не париться с текстфилдом)
    val currency: String = "USD",     // новое значение валюты
    val isLoading: Boolean = false,   // Progress bar, если нужен
    val errorMessage: String? = null  // Сообщение об ошибке (опционально)
)

class AccountEditViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditAccountUiState())
    val uiState: StateFlow<EditAccountUiState> = _uiState.asStateFlow()

    fun loadAccount(accountId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val account = getAccountUseCase(accountId)
                _uiState.update {
                    it.copy(
                        id = account.id,
                        name = account.name,
                        balance = account.balance,
                        currency = account.currency,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun updateAccountName(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun updateAccountCurrency(newCurrency: String) {
        _uiState.update { it.copy(currency = newCurrency) }
    }

    fun updateAccountBalance(newBalance: String) {
        _uiState.update { it.copy(balance = newBalance) }
    }

    fun updateAccountData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val current = _uiState.value
            val account = AccountDomainModel(
                id = current.id,
                name = current.name,
                balance = current.balance,
                currency = current.currency,
                userId = null,
                createdAt = null,
                updatedAt = null,
            )
            updateAccountUseCase(account)
                .onSuccess { updatedAccount ->
                    _uiState.update { it.copy(
                            isLoading = false,
                            errorMessage = null,
                            id = updatedAccount.id,
                            name = updatedAccount.name,
                            balance = updatedAccount.balance,
                            currency = updatedAccount.currency
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
        }
    }
}

class AccountEditViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<AccountEditViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}