package com.equall.assignment.model

data class TrackerStep(
    val title: String,
    val detail: String,
    val status: StepStatus,
    val timestamp: String? = null,
)
