package com.feature.account.ui.screens.accounts_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.constants.CoreDomainConstants.ACCOUNT_ID
import com.feature.account.domain.usecase.GetAccountUseCase
import com.feature.account.domain.usecase.GetAccountsUseCase
import com.feature.account.domain.usecase.UpdateAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {
    private val _accountScreenState =
        MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    init {
        loadAccountData()
    }

    private fun loadAccountData() {
        viewModelScope.launch {
            _accountScreenState.value = AccountScreenState.Loading
            getAccountsUseCase()
                .onSuccess { listOfAccounts ->
                    val accountItems = listOfAccounts.map {
                        AccountScreenItem(
                            id = it.id,
                            name = it.name,
                            currency = it.currency,
                            balance = it.balance,
                            isSelected = it.id == ACCOUNT_ID
                        )
                    }
                    _accountScreenState.value = AccountScreenState.Loaded(
                        AccountScreenData(accounts = accountItems)
                    )
                }
                .onFailure {
                    _accountScreenState.value =
                        AccountScreenState.Error(it.message ?: "Unknown error")
                }
        }
    }


}

class AccountViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<AccountsViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}