package com.dak0ta.domain.usecase.impl

import com.dak0ta.domain.repository.AppRepository
import com.dak0ta.domain.usecase.GetTotalPagesUseCase
import javax.inject.Inject

class GetTotalPagesUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : GetTotalPagesUseCase {
    override suspend operator fun invoke(): Result<Int> {
        return try {
            val response = appRepository.getTotalPages()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
