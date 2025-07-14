package com.core.domain.repository

import com.core.domain.models.CategoryDomainModel

interface CategoryRepository {

    suspend fun getCategories(): List<CategoryDomainModel>

}