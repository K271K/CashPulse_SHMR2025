package com.feature.account.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.account.domain.usecase.GetAccountUseCase
import com.feature.account.domain.usecase.UpdateAccountUseCase
import com.feature.account.ui.models.AccountUiModel
import com.feature.account.ui.models.toUiModel
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
    private val _accountScreenState = MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    init {
        loadAccountData()
    }

    private fun loadAccountData() {
        viewModelScope.launch {
            _accountScreenState.value = AccountScreenState.Loading
            try {
                val loadedAccount : AccountUiModel = getAccountUseCase().toUiModel()
                Log.d("AccountViewModel","$loadedAccount")
                _accountScreenState.value = AccountScreenState.Loaded(
                    AccountScreenData(
                        name = loadedAccount.name,
                        currency = loadedAccount.currency,
                        balance = loadedAccount.balance
                    )
                )
            } catch (e: Exception) {
                _accountScreenState.value = AccountScreenState.Error(
                    message = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun updateAccountName(newName: String) {
        viewModelScope.launch {
            try {
                val currentState = _accountScreenState.value as? AccountScreenState.Loaded
                if (currentState != null) {
                    val updatedDomainModel = currentState.data.copy(name = newName).toDomainAccount()
                    updateAccountUseCase(updatedDomainModel)

                    // Обновляем UI состояние
                    _accountScreenState.value = AccountScreenState.Loaded(
                        currentState.data.copy(name = newName)
                    )
                }
            } catch (e: Exception) {
                _accountScreenState.value = AccountScreenState.Error(
                    message = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun updateAccountCurrency(newCurrency: String) {
        viewModelScope.launch {
            try {
                val currentState = _accountScreenState.value as? AccountScreenState.Loaded
                if (currentState != null) {
                    val updatedDomainModel = currentState.data.copy(currency = newCurrency).toDomainAccount()
                    updateAccountUseCase(updatedDomainModel)

                    // Обновляем UI состояние
                    _accountScreenState.value = AccountScreenState.Loaded(
                        currentState.data.copy(currency = newCurrency)
                    )
                }
            } catch (e: Exception) {
                _accountScreenState.value = AccountScreenState.Error(
                    message = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun updateAccountBalance(newBalance: String) {
        viewModelScope.launch {
            try {
                val currentState = _accountScreenState.value as? AccountScreenState.Loaded
                if (currentState != null) {
                    val updatedDomainModel = currentState.data.copy(balance = newBalance).toDomainAccount()
                    updateAccountUseCase(updatedDomainModel)

                    // Обновляем UI состояние
                    _accountScreenState.value = AccountScreenState.Loaded(
                        currentState.data.copy(balance = newBalance)
                    )
                }
            } catch (e: Exception) {
                _accountScreenState.value = AccountScreenState.Error(
                    message = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }
}