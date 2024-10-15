package com.dak0ta.domain.usecase

interface GetFilmImageUrlsUseCase {
    suspend operator fun invoke(filmId: Int): Result<List<String>>
}