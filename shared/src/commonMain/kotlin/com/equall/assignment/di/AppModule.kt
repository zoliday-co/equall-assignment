package com.equall.assignment.di

import com.equall.assignment.data.DefaultHomeRepository
import com.equall.assignment.data.HomeRepository
import com.equall.assignment.data.remote.FakeHomeApi
import com.equall.assignment.domain.usecase.CheckEligibilityUseCase
import com.equall.assignment.domain.usecase.LoadHomeUseCase
import com.equall.assignment.ui.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { FakeHomeApi() }
    single<HomeRepository> { DefaultHomeRepository(get()) }
    singleOf(::LoadHomeUseCase)
    singleOf(::CheckEligibilityUseCase)
    viewModelOf(::HomeViewModel)
}
