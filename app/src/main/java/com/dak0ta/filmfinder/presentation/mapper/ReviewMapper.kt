package com.dak0ta.filmfinder.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.dak0ta.domain.entity.Review
import com.dak0ta.filmfinder.presentation.entity.ReviewDisplay

fun Review.toPresentation(): ReviewDisplay {
    val typeColor = this.getTypeColor()
    val userRatingText = if (this.userRating > 0) {
        "$userRating ${getReviewWordForm(this.userRating)}"
    } else {
        ""
    }

    return ReviewDisplay(
        id = this.id,
        displayAuthor = this.author,
        displayTitle = this.title,
        typeColor = typeColor,
        displayReview = this.review
            .normalizeText(),
        displayUserRating = userRatingText
    )
}

private fun Review.getTypeColor(): Color {
    return when (this.type) {
        "Позитивный" -> Color(0xFFEBF7EB)
        "Негативный" -> Color(0xFFFFEBEB)
        "Нейтральный" -> Color(0xFFF2F2F2)
        else -> Color(0xFFF2F2F2)
    }
}

private fun String.normalizeText(): String {
    return this.replace(Regex(" +"), " ")
        .replace(Regex("\n\\s+"), "\n")
        .trim()
}

fun getReviewWordForm(count: Int): String {
    val mod100 = count % 100
    val mod10 = count % 10
    return when {
        mod100 in 11..19 -> "рецензий"
        mod10 == 1 -> "рецензия"
        mod10 in 2..4 -> "рецензии"
        else -> "рецензий"
    }
}
