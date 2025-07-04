package com.feature.category.domain.repository

import com.core.domain.models.CategoryDomainModel

interface CategoryRepository {

    suspend fun getCategories(): Result<List<CategoryDomainModel>>

}