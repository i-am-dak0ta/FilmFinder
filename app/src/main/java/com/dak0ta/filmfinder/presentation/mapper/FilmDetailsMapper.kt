package com.dak0ta.filmfinder.presentation.mapper

import com.dak0ta.domain.entity.FilmDetails
import com.dak0ta.filmfinder.presentation.entity.FilmDetailsDisplay

fun FilmDetails.toPresentation(): FilmDetailsDisplay {

    val displayName = this.film.getDisplayName()
    val displayAlternativeName = this.film.getAlternativeName(displayName)
    val displayYear = this.film.getDisplayYear()

    return FilmDetailsDisplay(
        id = this.film.id,
        displayName = displayName,
        displayAlternativeName = displayAlternativeName,
        displayRating = this.rating.toString(),
        displayGenres = this.film.genres.joinToString(", "),
        displayYear = displayYear,
        displayCountries = this.film.countries.joinToString(", "),
        displayDescription = this.description,
        posterUrl = this.film.posterUrl,
        reviews = this.reviews.map { it.toPresentation() },
        filmImageUrls = this.filmImageUrls
    )
}