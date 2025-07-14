package com.core.data.repository

import com.core.data.models.mappers.toDataModel
import com.core.data.models.mappers.toDomainModel
import com.core.domain.models.CategoryDomainModel
import com.core.domain.repository.CategoryRepository
import com.core.network.RemoteDataSource
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryDomainModel> {
        val result = remoteDataSource.getCategories()
        val dataResult = result.map { it.toDataModel() }
        val domainResult = dataResult.map { it.toDomainModel() }
        return domainResult
    }
}