package com.equall.assignment.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.equall.assignment.model.TrustIq
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors

@Composable
fun TrustIqRow(
    trustIq: TrustIq,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val title = if (trustIq.score != null) {
        buildString {
            append("TrustIQ ${trustIq.score}")
            trustIq.trend?.let { append(" • $it") }
        }
    } else {
        "Your TrustIQ score"
    }

    EquallCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Dimens.lg, vertical = Dimens.md),
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusPill(
                text = trustIq.tier,
                background = EquallColors.Excellent,
                contentColor = Color.White,
            )
            Spacer(Modifier.width(Dimens.md))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = EquallColors.InkPrimary,
                )
                Text(
                    text = "Check your TrustIQ score",
                    fontSize = 13.sp,
                    color = EquallColors.InkSecondary,
                )
            }
            Text("›", color = EquallColors.InkSecondary, fontSize = 20.sp)
        }
    }
}
