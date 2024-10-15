package com.dak0ta.filmfinder.presentation.entity

import androidx.compose.ui.graphics.Color

data class ReviewDisplay(
    val id: Int,
    val displayAuthor: String,
    val displayTitle: String,
    val typeColor: Color,
    val displayReview: String,
    val displayUserRating: String
)