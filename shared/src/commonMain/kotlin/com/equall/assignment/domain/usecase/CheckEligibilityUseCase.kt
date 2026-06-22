package com.equall.assignment.domain.usecase

import com.equall.assignment.model.User

class CheckEligibilityUseCase {
    operator fun invoke(user: User): Boolean =
        user.trustIq.tier.equals(ELIGIBLE_TIER, ignoreCase = true)

    private companion object {
        const val ELIGIBLE_TIER = "EXCELLENT"
    }
}
