package com.equall.assignment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.equall.assignment.di.appModule
import com.equall.assignment.theme.EquallColors
import com.equall.assignment.ui.HomeEvent
import com.equall.assignment.ui.HomeScreen
import com.equall.assignment.ui.HomeViewModel
import com.equall.assignment.ui.screens.PlaceholderScreen
import com.equall.assignment.ui.shell.EquallBottomNav
import com.equall.assignment.ui.shell.EquallTopBar
import com.equall.assignment.ui.shell.StateToggle
import com.equall.assignment.ui.shell.Tab
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModule) }) {
        AppContent()
    }
}

@Composable
private fun AppContent() {
    MaterialTheme {
        val viewModel: HomeViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val selectedKind by viewModel.selectedKind.collectAsStateWithLifecycle()
        var selectedTab by remember { mutableStateOf(Tab.Home) }

        Scaffold(
            containerColor = EquallColors.ScreenBackground,
            topBar = {
                Column {
                    EquallTopBar()
                    if (selectedTab == Tab.Home) {
                        StateToggle(
                            selected = selectedKind,
                            onSelect = { viewModel.onEvent(HomeEvent.SelectLifecycle(it)) },
                        )
                    }
                }
            },
            bottomBar = {
                EquallBottomNav(selected = selectedTab, onSelect = { selectedTab = it })
            },
        ) { innerPadding ->
            val contentModifier = Modifier.padding(innerPadding)
            when (selectedTab) {
                Tab.Home -> HomeScreen(
                    state = uiState,
                    onEvent = viewModel::onEvent,
                    modifier = contentModifier,
                )
                Tab.Loans -> PlaceholderScreen(
                    glyph = Tab.Loans.glyph,
                    title = "Loans",
                    message = "Your active and past loans will appear here.",
                    modifier = contentModifier,
                )
                Tab.Explore -> PlaceholderScreen(
                    glyph = Tab.Explore.glyph,
                    title = "Explore",
                    message = "Browse credit products tailored to you.",
                    modifier = contentModifier,
                )
                Tab.Profile -> PlaceholderScreen(
                    glyph = Tab.Profile.glyph,
                    title = "Profile",
                    message = "Manage your account, KYC and preferences.",
                    modifier = contentModifier,
                )
            }
        }
    }
}
