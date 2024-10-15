package com.dak0ta.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    val id: Int,
    val name: String? = null,
    val alternativeName: String? = null,
    val year: Int? = null,
    val releaseYears: List<ReleaseYearsResponse>? = null,
    val countries: List<CountryResponse>? = null,
    val genres: List<GenreResponse>? = null,
    val isSeries: Boolean,
    val poster: PosterResponse? = null
)

@Serializable
data class ReleaseYearsResponse(
    val start: Int?,
    val end: Int?
)

@Serializable
data class CountryResponse(
    val name: String
)

@Serializable
data class GenreResponse(
    val name: String
)

@Serializable
data class PosterResponse(
    val url: String?
)