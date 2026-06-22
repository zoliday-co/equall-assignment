package com.equall.assignment.model

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Content(val data: HomeData) : HomeUiState
}
