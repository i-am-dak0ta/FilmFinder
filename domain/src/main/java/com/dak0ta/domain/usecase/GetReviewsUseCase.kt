package com.dak0ta.domain.usecase

import com.dak0ta.domain.entity.Review

interface GetReviewsUseCase {
    suspend operator fun invoke(filmId: Int): Result<List<Review>>
}