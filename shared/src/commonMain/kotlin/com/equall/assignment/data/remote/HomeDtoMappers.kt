package com.equall.assignment.data.remote

import com.equall.assignment.model.ExploreProduct
import com.equall.assignment.model.HomeData
import com.equall.assignment.model.LoanLifecycle
import com.equall.assignment.model.StepStatus
import com.equall.assignment.model.TrackerStep
import com.equall.assignment.model.TrustIq
import com.equall.assignment.model.User

fun HomeResponseDto.toDomain(): HomeData = HomeData(
    user = user.toDomain(),
    lifecycle = lifecycle.toDomain(),
)

private fun UserDto.toDomain(): User = User(
    name = name,
    trustIq = TrustIq(tier = trustTier, score = trustScore, trend = trustTrend),
)

private fun LifecycleDto.toDomain(): LoanLifecycle = when (type) {
    LifecycleType.DISCOVERY -> requireNotNull(discovery).let {
        LoanLifecycle.Discovery(
            preQualifiedAmount = it.preQualifiedAmount,
            tenureMonths = it.tenureMonths,
            exploreProducts = it.products.map { p ->
                ExploreProduct(p.code, p.title, p.subtitle, p.comingSoon)
            },
        )
    }
    LifecycleType.REVIEW -> requireNotNull(review).let {
        LoanLifecycle.PendingReview(
            amount = it.amount,
            steps = it.steps.map { s ->
                TrackerStep(s.title, s.detail, s.status.toStepStatus(), s.timestamp)
            },
        )
    }
    LifecycleType.ACTIVE -> requireNotNull(active).let {
        LoanLifecycle.ActiveLoan(
            outstanding = it.outstanding,
            tenureMonths = it.tenureMonths,
            segmentsTotal = it.segmentsTotal,
            segmentsPaid = it.segmentsPaid,
            aprPercent = it.aprPercent,
            emiAmount = it.emiAmount,
            dueDateLabel = it.dueDateLabel,
            autodebitBank = it.autodebitBank,
            nextEmiDueInDays = it.nextEmiDueInDays,
        )
    }
    else -> error("Unknown lifecycle type: $type")
}

private fun String.toStepStatus(): StepStatus = when (this) {
    "completed" -> StepStatus.COMPLETED
    "current" -> StepStatus.CURRENT
    "pending" -> StepStatus.PENDING
    else -> error("Unknown step status: $this")
}

fun HomeData.toDto(): HomeResponseDto = HomeResponseDto(
    user = UserDto(
        name = user.name,
        trustTier = user.trustIq.tier,
        trustScore = user.trustIq.score,
        trustTrend = user.trustIq.trend,
    ),
    lifecycle = lifecycle.toDto(),
)

private fun LoanLifecycle.toDto(): LifecycleDto = when (this) {
    is LoanLifecycle.Discovery -> LifecycleDto(
        type = LifecycleType.DISCOVERY,
        discovery = DiscoveryDto(
            preQualifiedAmount = preQualifiedAmount,
            tenureMonths = tenureMonths,
            products = exploreProducts.map { ProductDto(it.code, it.title, it.subtitle, it.comingSoon) },
        ),
    )
    is LoanLifecycle.PendingReview -> LifecycleDto(
        type = LifecycleType.REVIEW,
        review = ReviewDto(
            amount = amount,
            steps = steps.map { StepDto(it.title, it.detail, it.status.toWire(), it.timestamp) },
        ),
    )
    is LoanLifecycle.ActiveLoan -> LifecycleDto(
        type = LifecycleType.ACTIVE,
        active = ActiveDto(
            outstanding = outstanding,
            tenureMonths = tenureMonths,
            segmentsTotal = segmentsTotal,
            segmentsPaid = segmentsPaid,
            aprPercent = aprPercent,
            emiAmount = emiAmount,
            dueDateLabel = dueDateLabel,
            autodebitBank = autodebitBank,
            nextEmiDueInDays = nextEmiDueInDays,
        ),
    )
}

private fun StepStatus.toWire(): String = when (this) {
    StepStatus.COMPLETED -> "completed"
    StepStatus.CURRENT -> "current"
    StepStatus.PENDING -> "pending"
}

private object LifecycleType {
    const val DISCOVERY = "discovery"
    const val REVIEW = "review"
    const val ACTIVE = "active"
}
