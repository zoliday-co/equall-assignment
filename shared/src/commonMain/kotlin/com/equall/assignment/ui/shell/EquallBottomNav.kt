package com.equall.assignment.ui.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors

enum class Tab(val glyph: String, val label: String) {
    Home("⌂", "Home"),
    Loans("₹", "Loans"),
    Explore("✦", "Explore"),
    Profile("◉", "Profile"),
}

@Composable
fun EquallBottomNav(
    selected: Tab,
    onSelect: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(color = EquallColors.CardSurface, shadowElevation = 8.dp) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(top = Dimens.sm, bottom = Dimens.sm),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Tab.entries.forEach { tab ->
                NavCell(
                    tab = tab,
                    selected = tab == selected,
                    onClick = { onSelect(tab) },
                )
            }
        }
    }
}

@Composable
private fun NavCell(tab: Tab, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.chipRadius))
            .clickable(onClick = onClick)
            .padding(horizontal = Dimens.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.xs),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(Dimens.chipRadius))
                .background(if (selected) EquallColors.BrandIndigo else EquallColors.ChipNeutral),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = tab.glyph,
                color = if (selected) EquallColors.OnDark else EquallColors.InkSecondary,
                fontSize = 18.sp,
            )
        }
        Text(
            text = tab.label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) EquallColors.InkPrimary else EquallColors.InkSecondary,
        )
    }
}
