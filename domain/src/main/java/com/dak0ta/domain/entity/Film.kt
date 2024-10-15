package com.dak0ta.domain.entity

data class Film(
    val id: Int,
    val name: String,
    val alternativeName: String,
    val year: Int,
    val startReleaseYears: Int,
    val endReleaseYears: Int,
    val countries: List<String>,
    val genres: List<String>,
    val isSeries: Boolean,
    val posterUrl: String
)