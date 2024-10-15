package com.dak0ta.domain.usecase

import com.dak0ta.domain.entity.Film

interface GetFilmsUseCase {
    suspend operator fun invoke(page: Int): Result<List<Film>>
}