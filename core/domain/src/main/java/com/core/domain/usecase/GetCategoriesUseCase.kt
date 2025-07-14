package com.core.domain.usecase

import com.core.domain.models.CategoryDomainModel
import com.core.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(): Result<List<CategoryDomainModel>> {
        try {
            val categories = repository.getCategories()
            return Result.success(categories)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}