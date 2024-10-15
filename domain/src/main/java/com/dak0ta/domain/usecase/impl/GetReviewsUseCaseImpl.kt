package com.dak0ta.domain.usecase.impl

import com.dak0ta.domain.entity.Review
import com.dak0ta.domain.repository.AppRepository
import com.dak0ta.domain.usecase.GetReviewsUseCase
import javax.inject.Inject

class GetReviewsUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : GetReviewsUseCase {
    override suspend operator fun invoke(filmId: Int): Result<List<Review>> {
        return try {
            val response = repository.getReviews(filmId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
