package com.equall.assignment.ui.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.equall.assignment.model.LifecycleKind
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors

@Composable
fun StateToggle(
    selected: LifecycleKind,
    onSelect: (LifecycleKind) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = listOf(
        "A · Discovery" to LifecycleKind.Discovery,
        "B · Review" to LifecycleKind.Review,
        "C · Active" to LifecycleKind.Active,
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(EquallColors.ScreenBackground)
            .padding(horizontal = Dimens.lg, vertical = Dimens.sm),
        horizontalArrangement = Arrangement.spacedBy(Dimens.sm),
    ) {
        options.forEach { (label, kind) ->
            val isSelected = selected == kind
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(Dimens.buttonRadius))
                    .background(if (isSelected) EquallColors.BrandIndigo else EquallColors.ChipNeutral)
                    .clickable { onSelect(kind) }
                    .padding(vertical = Dimens.sm),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) EquallColors.OnDark else EquallColors.InkSecondary,
                )
            }
        }
    }
}
