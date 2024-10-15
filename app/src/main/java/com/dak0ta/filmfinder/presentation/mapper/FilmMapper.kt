package com.dak0ta.filmfinder.presentation.mapper

import com.dak0ta.domain.entity.Film
import com.dak0ta.filmfinder.presentation.entity.FilmDisplay

fun Film.toPresentation(): FilmDisplay {
    val displayName = this.getDisplayName()
    val displayYear = this.getDisplayYear()
    val displayAlternativeNameAndYear = this.getAlternativeNameAndYear(displayName, displayYear)
    val displayCountriesAndGenres = this.getCountriesAndGenres()

    return FilmDisplay(
        id = this.id,
        displayName = displayName,
        displayAlternativeNameAndYear = displayAlternativeNameAndYear,
        displayCountriesAndGenres = displayCountriesAndGenres,
        posterUrl = this.posterUrl
    )
}