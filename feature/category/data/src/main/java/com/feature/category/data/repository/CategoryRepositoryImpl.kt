package com.feature.category.data.repository

import com.core.data.models.toDataModel
import com.core.data.models.toDomainModel
import com.core.domain.models.CategoryDomainModel
import com.core.network.RemoteDataSource
import com.feature.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CategoryRepository {
    override suspend fun getCategories(): Result<List<CategoryDomainModel>> {
        try {
            val result = remoteDataSource.getCategories()
            val dataResult = result.map { it.toDataModel() }
            val domainResult = dataResult.map { it.toDomainModel() }
            return Result.success(domainResult)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}