package com.dak0ta.filmfinder.presentation.entity

data class FilmDisplay(
    val id: Int,
    val displayName: String,
    val displayAlternativeNameAndYear: String,
    val displayCountriesAndGenres: String,
    val posterUrl: String
)