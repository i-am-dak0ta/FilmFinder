package com.dak0ta.data.api

import com.dak0ta.data.entity.ApiResponse
import com.dak0ta.data.entity.FilmDetailsResponse
import com.dak0ta.data.entity.FilmImageUrlsResponse
import com.dak0ta.data.entity.FilmResponse
import com.dak0ta.data.entity.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("/v1.4/movie")
    suspend fun getFilms(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sortField") sortField: String,
        @Query("sortType") sortType: String,
        @Query("selectFields") selectFields: List<String>
    ): ApiResponse<FilmResponse>

    @GET("/v1.4/movie/{id}")
    suspend fun getFilmDetails(
        @Path("id") filmId: Int
    ): FilmDetailsResponse

    @GET("/v1.4/review")
    suspend fun getReviews(
        @Query("movieId") filmId: Int
    ): ApiResponse<ReviewResponse>

    @GET("/v1.4/image")
    suspend fun getFilmImageUrls(
        @Query("movieId") filmId: Int,
        @Query("limit") limit: Int,
        @Query("selectFields") selectFields: List<String>
    ): ApiResponse<FilmImageUrlsResponse>
}