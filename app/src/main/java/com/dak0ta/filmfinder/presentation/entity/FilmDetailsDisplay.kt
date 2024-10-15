package com.dak0ta.filmfinder.presentation.entity

data class FilmDetailsDisplay(
    val id: Int,
    val displayName: String,
    val displayAlternativeName: String,
    val displayRating: String,
    val displayGenres: String,
    val displayYear: String,
    val displayCountries: String,
    val displayDescription: String,
    val posterUrl: String,
    val reviews: List<ReviewDisplay>,
    val filmImageUrls: List<String>
)