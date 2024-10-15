package com.dak0ta.domain.entity

data class FilmDetails(
    val film: Film,
    val description: String,
    val rating: Double,
    val reviews: List<Review>,
    val filmImageUrls: List<String>
)