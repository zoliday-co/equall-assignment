package com.equall.assignment.data.remote

data class HomeResponseDto(
    val user: UserDto,
    val lifecycle: LifecycleDto,
)

data class UserDto(
    val name: String,
    val trustTier: String,
    val trustScore: Int? = null,
    val trustTrend: String? = null,
)

data class LifecycleDto(
    val type: String,
    val discovery: DiscoveryDto? = null,
    val review: ReviewDto? = null,
    val active: ActiveDto? = null,
)

data class DiscoveryDto(
    val preQualifiedAmount: Long,
    val tenureMonths: Int,
    val products: List<ProductDto>,
)

data class ProductDto(
    val code: String,
    val title: String,
    val subtitle: String,
    val comingSoon: Boolean = false,
)

data class ReviewDto(
    val amount: Long,
    val steps: List<StepDto>,
)

data class StepDto(
    val title: String,
    val detail: String,
    val status: String,
    val timestamp: String? = null,
)

data class ActiveDto(
    val outstanding: Long,
    val tenureMonths: Int,
    val segmentsTotal: Int,
    val segmentsPaid: Int,
    val aprPercent: Double,
    val emiAmount: Long,
    val dueDateLabel: String,
    val autodebitBank: String,
    val nextEmiDueInDays: Int,
)
