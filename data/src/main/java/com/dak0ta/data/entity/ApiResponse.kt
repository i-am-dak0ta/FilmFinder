package com.dak0ta.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val docs: List<T>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int
)