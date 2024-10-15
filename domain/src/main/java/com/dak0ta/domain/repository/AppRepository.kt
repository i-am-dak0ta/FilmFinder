package com.dak0ta.domain.repository

import com.dak0ta.domain.entity.Film
import com.dak0ta.domain.entity.FilmDetails
import com.dak0ta.domain.entity.Review

interface AppRepository {
    suspend fun getFilms(page: Int): List<Film>

    suspend fun getFilmDetails(filmId: Int): FilmDetails

    suspend fun getTotalPages(): Int

    suspend fun getReviews(filmId: Int): List<Review>

    suspend fun getFilmImageUrls(filmId: Int): List<String>
}