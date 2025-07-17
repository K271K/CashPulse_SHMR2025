package com.core.domain.di

import com.core.domain.repository.AccountRepository
import com.core.domain.repository.CategoryRepository
import com.core.domain.repository.CurrencyRepository
import com.core.domain.repository.TransactionRepository
import com.core.domain.usecase.DeleteTransactionUseCase
import com.core.domain.usecase.GetAccountUseCase
import com.core.domain.usecase.GetCategoriesUseCase
import com.core.domain.usecase.GetCurrencyUseCase
import com.core.domain.usecase.GetExpenseCategoriesUseCase
import com.core.domain.usecase.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides

@Module
object CoreDomainModule {

    @Provides
    fun provideGetCurrencyUseCase(repository: CurrencyRepository): GetCurrencyUseCase {
        return GetCurrencyUseCase(repository = repository)
    }

    @Provides
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase =
        GetCategoriesUseCase(repository = repository)

    @Provides
    fun provideGetExpenseCategoriesUseCase(repository: CategoryRepository): GetExpenseCategoriesUseCase =
        GetExpenseCategoriesUseCase(repository = repository)

    @Provides
    fun provideGetAccountUseCase(repository: AccountRepository): GetAccountUseCase {
        return GetAccountUseCase(repository = repository)
    }

    @Provides
    fun provideUpdateTransactionUseCase(repository: TransactionRepository): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(repository = repository)
    }

    @Provides
    fun provideDeleteTransactionUseCase(repository: TransactionRepository): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(repository = repository)
    }
}