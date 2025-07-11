package com.feature.category.data.di

import com.core.network.RemoteDataSource
import com.feature.category.data.repository.CategoryRepositoryImpl
import com.feature.category.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides

@Module
object CategoryDataModule {

    @Provides
    fun provideCategoryRepository(
        remoteDataSource: RemoteDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(remoteDataSource = remoteDataSource)
    }
}