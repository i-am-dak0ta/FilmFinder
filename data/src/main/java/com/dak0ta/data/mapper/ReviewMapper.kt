package com.dak0ta.data.mapper

import com.dak0ta.data.entity.ReviewResponse
import com.dak0ta.domain.entity.Review

fun ReviewResponse.toDomain(): Review {
    return Review(
        id = this.id,
        author = this.author,
        title = this.title.orEmpty(),
        type = this.type,
        review = this.review,
        userRating = this.userRating
    )
}
