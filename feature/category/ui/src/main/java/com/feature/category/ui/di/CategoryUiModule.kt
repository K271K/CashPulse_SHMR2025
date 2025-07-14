package com.feature.category.ui.di

import com.core.data.di.CoreDataModule
import com.feature.category.data.di.CategoryDataModule
import com.feature.category.domain.di.CategoryDomainModule
import com.feature.category.ui.navigation.CategoryNavigation
import com.feature.category.ui.navigation.CategoryNavigationImpl
import com.feature.category.ui.screens.CategoryViewModel
import com.feature.category.ui.screens.CategoryViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        CategoryDataModule::class,
        CategoryDomainModule::class,
        CoreDataModule::class
    ]
)
object CategoryUiModule {
    @Provides
    @Singleton
    fun provideIncomesNavigation(
        categoryViewModelFactory: CategoryViewModelFactory
    ): CategoryNavigation{
        return CategoryNavigationImpl(
            categoryViewModelFactory = categoryViewModelFactory
        )
    }

    @Provides
    @Singleton
    fun provideCategoryViewModelFactory(
        viewModelProvider: Provider<CategoryViewModel>
    ) : CategoryViewModelFactory {
        return CategoryViewModelFactory(
            viewModelProvider = viewModelProvider
        )
    }
}