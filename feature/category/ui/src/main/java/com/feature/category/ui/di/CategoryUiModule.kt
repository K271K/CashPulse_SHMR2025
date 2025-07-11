package com.feature.category.ui.di

import com.core.data.di.CoreDataModule
import com.feature.category.data.di.CategoryDataModule
import com.feature.category.domain.di.CategoryDomainModule
import com.feature.category.ui.navigation.CategoryNavigation
import com.feature.category.ui.navigation.CategoryNavigationImpl
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CategoryDataModule::class,
        CategoryDomainModule::class,
        CoreDataModule::class
    ]
)
object CategoryUiModule {
    @Provides
    fun provideIncomesNavigation(): CategoryNavigation{
        return CategoryNavigationImpl()
    }
}