package com.equall.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.equall.assignment.domain.usecase.CheckEligibilityUseCase
import com.equall.assignment.domain.usecase.LoadHomeUseCase
import com.equall.assignment.model.HomeUiState
import com.equall.assignment.model.LifecycleKind
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadHome: LoadHomeUseCase,
    private val checkEligibility: CheckEligibilityUseCase,
) : ViewModel() {

    private val _selectedKind = MutableStateFlow(LifecycleKind.Discovery)
    val selectedKind: StateFlow<LifecycleKind> = _selectedKind.asStateFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        load(LifecycleKind.Discovery)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SelectLifecycle -> select(event.kind)
            HomeEvent.CheckEligibility -> onCheckEligibility()
            HomeEvent.PayNow -> load(_selectedKind.value)
            HomeEvent.Retry -> load(_selectedKind.value)
        }
    }

    private fun onCheckEligibility() {
        val current = _uiState.value
        if (current is HomeUiState.Content && checkEligibility(current.data.user)) {
            select(LifecycleKind.Review)
        }
    }

    private fun select(kind: LifecycleKind) {
        _selectedKind.value = kind
        load(kind)
    }

    private fun load(kind: LifecycleKind) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                _uiState.value = HomeUiState.Content(loadHome(kind))
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Something went wrong.")
            }
        }
    }
}
