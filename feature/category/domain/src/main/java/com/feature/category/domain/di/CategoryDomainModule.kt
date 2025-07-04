package com.feature.category.domain.di

import com.feature.category.domain.repository.CategoryRepository
import com.feature.category.domain.usecases.GetCategoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object CategoryDomainModule {

    @Provides
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase = GetCategoriesUseCase(repository = repository)
}