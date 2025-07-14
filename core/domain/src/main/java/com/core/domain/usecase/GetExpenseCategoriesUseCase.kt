package com.core.domain.usecase

import com.core.domain.models.CategoryDomainModel
import com.core.domain.repository.CategoryRepository
import javax.inject.Inject

class GetExpenseCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(): Result<List<CategoryDomainModel>> {
        try {
            val categories = repository.getCategories()
            val filteredCategories = categories.filter { category->
                !category.isIncome
            }
            return Result.success(filteredCategories)
        } catch (e: Exception) {
            return Result.failure(e)
        }

    }
}