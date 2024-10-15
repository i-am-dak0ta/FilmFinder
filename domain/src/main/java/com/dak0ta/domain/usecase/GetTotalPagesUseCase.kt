package com.dak0ta.domain.usecase

interface GetTotalPagesUseCase {
    suspend operator fun invoke(): Result<Int>
}