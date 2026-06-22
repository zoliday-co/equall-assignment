package com.equall.assignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.equall.assignment.model.LoanLifecycle
import com.equall.assignment.model.User
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors
import com.equall.assignment.ui.components.PillButton
import com.equall.assignment.ui.components.SectionCaption
import com.equall.assignment.ui.components.TrustIqRow
import com.equall.assignment.util.toInr

@Composable
fun ActiveLoanScreen(user: User, state: LoanLifecycle.ActiveLoan, onPayNow: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.lg),
        verticalArrangement = Arrangement.spacedBy(Dimens.lg),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.sm)) {
            SectionCaption("Your existing loan")
            OutstandingCard(state)
        }
        EmiAlertBanner(emiAmount = state.emiAmount, dueInDays = state.nextEmiDueInDays, onPayNow = onPayNow)
        TrustIqRow(trustIq = user.trustIq)
    }
}

@Composable
private fun OutstandingCard(state: LoanLifecycle.ActiveLoan) {
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(Dimens.cardRadius))) {
        Box(modifier = Modifier.fillMaxWidth().background(EquallColors.DarkNavy)) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 60.dp, y = (-50).dp)
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.04f)),
            )
            Column(
                modifier = Modifier.padding(Dimens.lg),
                verticalArrangement = Arrangement.spacedBy(Dimens.sm),
            ) {
                Text(
                    "OUTSTANDING",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = EquallColors.OnDarkMuted,
                )
                Text(state.outstanding.toInr(), fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = EquallColors.OnDark)
                Text(
                    "Personal loan · ${state.tenureMonths} months · ${state.aprPercent}% p.a.",
                    fontSize = 14.sp,
                    color = EquallColors.OnDarkMuted,
                )
                Spacer(Modifier.height(Dimens.xs))
                SegmentedBar(total = state.segmentsTotal, paid = state.segmentsPaid)
            }
        }
        Column(modifier = Modifier.fillMaxWidth().background(EquallColors.CardSurface)) {
            MetricsRow(
                emi = state.emiAmount,
                dueLabel = state.dueDateLabel,
                aprPercent = state.aprPercent,
            )
            Box(Modifier.fillMaxWidth().height(1.dp).background(EquallColors.CardBorder))
            AutodebitRow(bank = state.autodebitBank)
        }
    }
}

@Composable
private fun SegmentedBar(total: Int, paid: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.xs),
    ) {
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(Dimens.barRadius))
                    .background(if (index < paid) EquallColors.SegmentFilled else EquallColors.SegmentEmpty),
            )
        }
    }
}

@Composable
private fun MetricsRow(emi: Long, dueLabel: String, aprPercent: Double) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min).padding(Dimens.lg),
    ) {
        MetricCell(value = emi.toInr(), label = "EMI", modifier = Modifier.weight(1f))
        MetricDivider()
        MetricCell(value = dueLabel, label = "Due", modifier = Modifier.weight(1f))
        MetricDivider()
        MetricCell(value = "$aprPercent%", label = "Rate p.a.", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun MetricCell(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = Dimens.md), verticalArrangement = Arrangement.spacedBy(Dimens.xs)) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EquallColors.InkPrimary)
        Text(label, fontSize = 13.sp, color = EquallColors.InkSecondary)
    }
}

@Composable
private fun MetricDivider() {
    Box(Modifier.width(1.dp).fillMaxHeight().background(EquallColors.CardBorder))
}

@Composable
private fun AutodebitRow(bank: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.lg, vertical = Dimens.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(EquallColors.Excellent))
        Spacer(Modifier.width(Dimens.sm))
        Text("Autodebit on · $bank", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = EquallColors.Excellent)
        Spacer(Modifier.weight(1f))
        Text("Details ›", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = EquallColors.BrandIndigo)
    }
}

@Composable
private fun EmiAlertBanner(emiAmount: Long, dueInDays: Int, onPayNow: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardRadius))
            .background(EquallColors.Amber)
            .padding(Dimens.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.xs)) {
            Text("Next EMI · due in $dueInDays days", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = EquallColors.AmberText)
            Text(emiAmount.toInr(), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = EquallColors.InkPrimary)
        }
        PillButton(
            text = "Pay now",
            onClick = onPayNow,
            background = EquallColors.InkPrimary,
            contentColor = Color.White,
        )
    }
}
