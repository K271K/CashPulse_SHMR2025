package com.core.domain.di

import com.core.domain.repository.AccountRepository
import com.core.domain.repository.CategoryRepository
import com.core.domain.repository.CurrencyRepository
import com.core.domain.usecase.GetAccountUseCase
import com.core.domain.usecase.GetCategoriesUseCase
import com.core.domain.usecase.GetCurrencyUseCase
import com.core.domain.usecase.GetExpenseCategoriesUseCase
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
}