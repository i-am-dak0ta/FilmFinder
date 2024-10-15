package com.dak0ta.domain.entity

data class Review(
    val id: Int,
    val author: String,
    val title: String,
    val type: String,
    val review: String,
    val userRating: Int
)