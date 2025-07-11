package com.feature.category.domain.di

import com.feature.category.domain.repository.CategoryRepository
import com.feature.category.domain.usecases.GetCategoriesUseCase
import dagger.Module
import dagger.Provides

@Module
object CategoryDomainModule {

    @Provides
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase = GetCategoriesUseCase(repository = repository)
}