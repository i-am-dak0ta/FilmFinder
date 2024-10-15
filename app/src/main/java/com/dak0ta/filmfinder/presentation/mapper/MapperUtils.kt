package com.dak0ta.filmfinder.presentation.mapper

import com.dak0ta.domain.entity.Film

fun Film.getDisplayName(): String {
    return this.name.ifEmpty { this.alternativeName }
}

fun Film.getDisplayYear(): String {
    return if (this.isSeries) {
        val startYear = if (this.startReleaseYears == -1) "..." else this.startReleaseYears.toString()
        val endYear = if (this.endReleaseYears == -1) "..." else this.endReleaseYears.toString()

        if (startYear == "..." && endYear == "...") ""
        else if (startYear != endYear) "$startYear – $endYear" else startYear
    } else {
        if (this.year != -1) this.year.toString() else ""
    }
}

fun Film.getAlternativeName(displayName: String): String {
    return if (displayName == this.alternativeName) "" else this.alternativeName
}

fun Film.getAlternativeNameAndYear(displayName: String, displayYear: String): String {
    return when {
        displayName == this.alternativeName && displayYear.isEmpty() -> ""
        displayName == this.alternativeName || this.alternativeName.isEmpty() -> displayYear
        displayYear.isEmpty() -> this.alternativeName
        else -> "${this.alternativeName}, $displayYear".trimEnd(',').trim()
    }
}

fun Film.getCountriesAndGenres(): String {
    return listOfNotNull(
        this.countries.joinToString(", ").takeIf { it.isNotEmpty() },
        this.genres.joinToString(", ").takeIf { it.isNotEmpty() }
    ).joinToString(" • ")
}
