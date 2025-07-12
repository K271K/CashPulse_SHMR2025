package com.feature.account.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.account.ui.screens.account_edit_screen.AccountEditScreen
import com.feature.account.ui.screens.account_edit_screen.AccountEditViewModelFactory
import com.feature.account.ui.screens.accounts_screen.AccountScreen
import com.feature.account.ui.screens.accounts_screen.AccountViewModelFactory
import javax.inject.Inject

/**
 * Наследуемся от интерфейса Feature из :core:navigation
 * Интерфейс фичи открытый и используется в :app модуле для регистрации фичи в навигации
 */
interface AccountNavigation : Feature

/**
 * internal имплементация интерфейса фичи, которая непосредственно задаёт граф навигации для фичи
 */
internal class AccountNavigationImpl @Inject constructor(
    private val accountsViewModelFactory: AccountViewModelFactory,
    private val accountEditViewModelFactory: AccountEditViewModelFactory
) : AccountNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Account>(
            startDestination = Dest.AccountMain
        ) {
            composable<Dest.AccountMain> {
                AccountScreen(
                    onEditAccountDataClick = { accountId->
                        navHostController.navigate(Dest.AccountEdit(id = accountId)) {
                            popUpTo(Dest.AccountMain) {
                                inclusive = true
                            }
                        }
                    },
                    viewModelFactory = accountsViewModelFactory
                )
            }
            composable<Dest.AccountEdit> {
                val args = it.toRoute<Dest.AccountEdit>()
                AccountEditScreen(
                    viewModelFactory = accountEditViewModelFactory,
                    accountId = args.id,
                    onDoneClick = {
                        navHostController.navigate(Dest.AccountMain){
                            popUpTo(Dest.AccountMain) {
                                inclusive = true
                            }
                        }
                    },
                    onCancelClick = {
                        navHostController.navigate(Dest.AccountMain){
                            popUpTo(Dest.AccountMain) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}