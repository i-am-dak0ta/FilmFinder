package com.dak0ta.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class FilmDetailsResponse(
    val id: Int,
    val name: String? = null,
    val alternativeName: String? = null,
    val year: Int? = null,
    val releaseYears: List<ReleaseYearsResponse>? = null,
    val countries: List<CountryResponse>? = null,
    val genres: List<GenreResponse>? = null,
    val isSeries: Boolean,
    val poster: PosterResponse? = null,
    val description: String? = null,
    val rating: RatingResponse? = null
)

@Serializable
data class RatingResponse(
    val kp: Double?
)