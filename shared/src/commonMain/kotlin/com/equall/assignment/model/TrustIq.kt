package com.equall.assignment.model

data class TrustIq(
    val tier: String,
    val score: Int? = null,
    val trend: String? = null,
)
