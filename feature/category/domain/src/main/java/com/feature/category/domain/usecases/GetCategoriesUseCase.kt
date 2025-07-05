package com.feature.category.domain.usecases

import com.core.domain.models.CategoryDomainModel
import com.feature.category.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(): Result<List<CategoryDomainModel>> {
        return repository.getCategories()
    }
}