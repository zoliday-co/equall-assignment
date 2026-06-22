package com.equall.assignment.data.remote

import com.equall.assignment.model.LifecycleKind
import com.equall.assignment.model.SampleData
import kotlinx.coroutines.delay

class FakeHomeApi(
    private val latencyMillis: Long = 700,
    private val failFirstAttempt: Boolean = false,
) {
    private val attempts = mutableMapOf<LifecycleKind, Int>()

    suspend fun fetchHome(kind: LifecycleKind): HomeResponseDto {
        delay(latencyMillis)

        val attempt = (attempts[kind] ?: 0) + 1
        attempts[kind] = attempt
        if (failFirstAttempt && attempt == 1) {
            throw HomeApiException("Couldn't reach Equall servers. Check your connection.")
        }

        return seedFor(kind).toDto()
    }

    private fun seedFor(kind: LifecycleKind) = when (kind) {
        LifecycleKind.Discovery -> SampleData.discovery
        LifecycleKind.Review -> SampleData.pendingReview
        LifecycleKind.Active -> SampleData.activeLoan
    }
}

class HomeApiException(message: String) : Exception(message)
