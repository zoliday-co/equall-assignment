package com.equall.assignment.data

import com.equall.assignment.data.remote.FakeHomeApi
import com.equall.assignment.data.remote.toDomain
import com.equall.assignment.model.HomeData
import com.equall.assignment.model.LifecycleKind

interface HomeRepository {
    suspend fun loadHome(kind: LifecycleKind): HomeData
}

class DefaultHomeRepository(
    private val api: FakeHomeApi,
) : HomeRepository {
    override suspend fun loadHome(kind: LifecycleKind): HomeData =
        api.fetchHome(kind).toDomain()
}
