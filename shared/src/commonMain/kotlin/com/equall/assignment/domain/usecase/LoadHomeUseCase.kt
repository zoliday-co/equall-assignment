package com.equall.assignment.domain.usecase

import com.equall.assignment.data.HomeRepository
import com.equall.assignment.model.HomeData
import com.equall.assignment.model.LifecycleKind

class LoadHomeUseCase(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(kind: LifecycleKind): HomeData =
        repository.loadHome(kind)
}
