package com.dak0ta.domain.usecase.impl

import com.dak0ta.domain.entity.FilmDetails
import com.dak0ta.domain.repository.AppRepository
import com.dak0ta.domain.usecase.GetFilmDetailsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetFilmDetailsUseCaseImpl @Inject constructor(
    private val getFilmImageUrlsUseCase: GetFilmImageUrlsUseCaseImpl,
    private val getReviewsUseCase: GetReviewsUseCaseImpl,
    private val appRepository: AppRepository
) : GetFilmDetailsUseCase {

    override suspend fun invoke(filmId: Int): Result<FilmDetails> {
        return try {
            coroutineScope {
                val filmDetailsDeferred = async { appRepository.getFilmDetails(filmId) }
                val reviewsDeferred = async { getReviewsUseCase.invoke(filmId) }
                val imagesDeferred = async { getFilmImageUrlsUseCase.invoke(filmId) }

                val filmDetails = filmDetailsDeferred.await()
                val reviews = reviewsDeferred.await().getOrDefault(emptyList())
                val imageUrls = imagesDeferred.await().getOrDefault(emptyList())

                Result.success(
                    filmDetails.copy(
                        reviews = reviews,
                        filmImageUrls = imageUrls
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}