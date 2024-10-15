package com.dak0ta.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val id: Int,
    val author: String,
    val title: String?,
    val type: String,
    val review: String,
    val userRating: Int
)