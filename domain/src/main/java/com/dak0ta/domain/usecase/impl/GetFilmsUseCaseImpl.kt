package com.dak0ta.domain.usecase.impl

import com.dak0ta.domain.entity.Film
import com.dak0ta.domain.repository.AppRepository
import com.dak0ta.domain.usecase.GetFilmsUseCase
import javax.inject.Inject

class GetFilmsUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : GetFilmsUseCase {
    override suspend operator fun invoke(page: Int): Result<List<Film>> {
        return try {
            val response = appRepository.getFilms(page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}