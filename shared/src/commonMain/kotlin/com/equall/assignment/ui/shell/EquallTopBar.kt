package com.equall.assignment.ui.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors

@Composable
fun EquallTopBar(modifier: Modifier = Modifier) {
    Surface(color = EquallColors.CardSurface) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = Dimens.lg, vertical = Dimens.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = EquallColors.BrandIndigo)) { append("EQU") }
                        withStyle(SpanStyle(color = EquallColors.InkPrimary)) { append("ALL") }
                    },
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )
                Spacer(Modifier.weight(1f))
                Text("?", color = EquallColors.InkSecondary, fontSize = 18.sp)
                Spacer(Modifier.width(Dimens.sm))
                Text("·", color = EquallColors.InkSecondary, fontSize = 18.sp)
                Spacer(Modifier.width(Dimens.sm))
                Text("🔔", fontSize = 16.sp)
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(EquallColors.CardBorder))
        }
    }
}
