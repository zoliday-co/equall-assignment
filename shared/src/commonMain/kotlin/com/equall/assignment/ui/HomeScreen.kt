package com.equall.assignment.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.equall.assignment.model.HomeData
import com.equall.assignment.model.HomeUiState
import com.equall.assignment.model.LoanLifecycle
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors
import com.equall.assignment.ui.components.PillButton
import com.equall.assignment.ui.screens.ActiveLoanScreen
import com.equall.assignment.ui.screens.DiscoveryScreen
import com.equall.assignment.ui.screens.ReviewScreen

@Composable
fun HomeScreen(
    state: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state,
        animationSpec = tween(durationMillis = 280),
        label = "home-state",
        modifier = modifier,
    ) { current ->
        when (current) {
            HomeUiState.Loading -> LoadingState()
            is HomeUiState.Error -> ErrorState(
                message = current.message,
                onRetry = { onEvent(HomeEvent.Retry) },
            )
            is HomeUiState.Content -> HomeContent(
                data = current.data,
                onEvent = onEvent,
            )
        }
    }
}

@Composable
private fun HomeContent(
    data: HomeData,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.lg),
    ) {
        when (val lifecycle = data.lifecycle) {
            is LoanLifecycle.Discovery -> DiscoveryScreen(
                user = data.user,
                state = lifecycle,
                onCheckEligibility = { onEvent(HomeEvent.CheckEligibility) },
            )
            is LoanLifecycle.PendingReview -> ReviewScreen(lifecycle)
            is LoanLifecycle.ActiveLoan -> ActiveLoanScreen(
                user = data.user,
                state = lifecycle,
                onPayNow = { onEvent(HomeEvent.PayNow) },
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = EquallColors.BrandIndigo)
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(Dimens.xl),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.md),
        ) {
            Text(
                "Something went wrong",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EquallColors.InkPrimary,
            )
            Text(
                message,
                fontSize = 14.sp,
                color = EquallColors.InkSecondary,
                textAlign = TextAlign.Center,
            )
            PillButton(
                text = "Try again",
                onClick = onRetry,
                background = EquallColors.BrandIndigo,
                contentColor = EquallColors.OnDark,
            )
        }
    }
}
