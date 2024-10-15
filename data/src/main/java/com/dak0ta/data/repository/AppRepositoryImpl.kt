package com.dak0ta.data.repository

import com.dak0ta.data.api.KinopoiskApi
import com.dak0ta.data.mapper.toDomain
import com.dak0ta.domain.entity.Film
import com.dak0ta.domain.entity.FilmDetails
import com.dak0ta.domain.entity.Review
import com.dak0ta.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : AppRepository {

    companion object {
        private val FILMS_SELECT_FIELDS = listOf(
            "id", "name", "alternativeName", "year", "releaseYears",
            "countries", "genres", "isSeries", "poster"
        )
        private val IMAGE_SELECT_FIELDS = listOf("url")
        private const val DEFAULT_SORT_FIELD = "votes.kp"
        private const val DEFAULT_SORT_TYPE = "-1"
        private const val FILMS_LIMIT = 20
        private const val IMAGE_URLS_LIMIT = 20
    }

    override suspend fun getFilms(page: Int): List<Film> {
        val response = api.getFilms(
            page = page,
            limit = FILMS_LIMIT,
            sortField = DEFAULT_SORT_FIELD,
            sortType = DEFAULT_SORT_TYPE,
            selectFields = FILMS_SELECT_FIELDS
        )
        return response.docs.map { it.toDomain() }
    }

    override suspend fun getFilmDetails(filmId: Int): FilmDetails {
        val response = api.getFilmDetails(filmId)
        return response.toDomain()
    }

    override suspend fun getTotalPages(): Int {
        val response = api.getFilms(
            page = 1,
            limit = FILMS_LIMIT,
            sortField = DEFAULT_SORT_FIELD,
            sortType = DEFAULT_SORT_TYPE,
            selectFields = FILMS_SELECT_FIELDS
        )
        return response.pages
    }

    override suspend fun getReviews(filmId: Int): List<Review> {
        val response = api.getReviews(filmId)
        return response.docs.map { it.toDomain() }
    }

    override suspend fun getFilmImageUrls(filmId: Int): List<String> {
        val response = api.getFilmImageUrls(
            filmId = filmId,
            limit = IMAGE_URLS_LIMIT,
            selectFields = IMAGE_SELECT_FIELDS
        )
        return response.docs.map { it.url }
    }
}