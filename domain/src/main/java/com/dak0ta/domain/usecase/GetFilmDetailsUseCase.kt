package com.dak0ta.domain.usecase

import com.dak0ta.domain.entity.FilmDetails

interface GetFilmDetailsUseCase {
    suspend operator fun invoke(filmId: Int): Result<FilmDetails>
}