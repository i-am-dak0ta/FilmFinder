package com.dak0ta.data.mapper

import com.dak0ta.data.entity.FilmResponse
import com.dak0ta.domain.entity.Film

fun FilmResponse.toDomain(): Film {
    return Film(
        id = this.id,
        name = this.name.orEmpty(),
        alternativeName = this.alternativeName.orEmpty(),
        year = this.year ?: -1,
        startReleaseYears = this.releaseYears?.firstOrNull()?.start ?: -1,
        endReleaseYears = this.releaseYears?.firstOrNull()?.end ?: -1,
        countries = this.countries?.map { it.name } ?: emptyList(),
        genres = this.genres?.map { it.name } ?: emptyList(),
        isSeries = this.isSeries,
        posterUrl = this.poster?.url.orEmpty()
    )
}
