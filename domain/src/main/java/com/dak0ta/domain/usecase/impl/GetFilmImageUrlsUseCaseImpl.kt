package com.dak0ta.domain.usecase.impl

import com.dak0ta.domain.repository.AppRepository
import com.dak0ta.domain.usecase.GetFilmImageUrlsUseCase
import javax.inject.Inject

class GetFilmImageUrlsUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : GetFilmImageUrlsUseCase {
    override suspend operator fun invoke(filmId: Int): Result<List<String>> {
        return try {
            val response = repository.getFilmImageUrls(filmId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
