package com.equall.assignment.ui

import com.equall.assignment.model.LifecycleKind

sealed interface HomeEvent {
    data class SelectLifecycle(val kind: LifecycleKind) : HomeEvent
    data object CheckEligibility : HomeEvent
    data object PayNow : HomeEvent
    data object Retry : HomeEvent
}
