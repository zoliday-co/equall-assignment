package com.equall.assignment.model

sealed interface LoanLifecycle {

    data class Discovery(
        val preQualifiedAmount: Long,
        val tenureMonths: Int,
        val exploreProducts: List<ExploreProduct>,
    ) : LoanLifecycle

    data class PendingReview(
        val amount: Long,
        val steps: List<TrackerStep>,
    ) : LoanLifecycle

    data class ActiveLoan(
        val outstanding: Long,
        val tenureMonths: Int,
        val segmentsTotal: Int,
        val segmentsPaid: Int,
        val aprPercent: Double,
        val emiAmount: Long,
        val dueDateLabel: String,
        val autodebitBank: String,
        val nextEmiDueInDays: Int,
    ) : LoanLifecycle
}
