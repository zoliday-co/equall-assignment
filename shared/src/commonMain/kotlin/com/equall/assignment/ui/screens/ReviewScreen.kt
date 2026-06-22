package com.equall.assignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.equall.assignment.model.LoanLifecycle
import com.equall.assignment.model.StepStatus
import com.equall.assignment.model.TrackerStep
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors
import com.equall.assignment.ui.components.SectionCaption
import com.equall.assignment.ui.components.StatusPill
import com.equall.assignment.util.toInr

@Composable
fun ReviewScreen(state: LoanLifecycle.PendingReview) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.lg),
        verticalArrangement = Arrangement.spacedBy(Dimens.lg),
    ) {
        ApplicationCard(amount = state.amount, steps = state.steps)
        ReassuranceFooter()
    }
}

@Composable
private fun ApplicationCard(amount: Long, steps: List<TrackerStep>) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(Dimens.cardRadius)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(EquallColors.Blue, EquallColors.BlueDeep)))
                .padding(Dimens.lg),
            verticalArrangement = Arrangement.spacedBy(Dimens.sm),
        ) {
            Text(
                "PERSONAL LOAN APPLICATION",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = EquallColors.OnBlueMuted,
            )
            Text(amount.toInr(), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = EquallColors.OnBlue)
            StatusPill(
                text = "UNDER REVIEW",
                background = Color.White.copy(alpha = 0.18f),
                contentColor = EquallColors.OnBlue,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().background(EquallColors.CardSurface).padding(Dimens.lg),
        ) {
            steps.forEachIndexed { index, step ->
                StepRow(index = index, step = step, isLast = index == steps.lastIndex)
            }
        }
    }
}

@Composable
private fun StepRow(index: Int, step: TrackerStep, isLast: Boolean) {
    val isCurrent = step.status == StepStatus.CURRENT
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Box(modifier = Modifier.width(28.dp).fillMaxHeight()) {
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 28.dp)
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(if (step.status == StepStatus.COMPLETED) EquallColors.Blue else EquallColors.StepLine),
                )
            }
            StepCircle(status = step.status, index = index, modifier = Modifier.align(Alignment.TopCenter))
        }
        Spacer(Modifier.width(Dimens.md))
        Column(
            modifier = Modifier.padding(bottom = if (isLast) 0.dp else Dimens.lg),
            verticalArrangement = Arrangement.spacedBy(Dimens.xs),
        ) {
            Text(
                step.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCurrent) EquallColors.Blue else EquallColors.InkPrimary,
            )
            Text(step.detail, fontSize = 14.sp, color = EquallColors.InkSecondary)
            step.timestamp?.let { TimestampChip(it) }
        }
    }
}

@Composable
private fun StepCircle(status: StepStatus, index: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(28.dp), contentAlignment = Alignment.Center) {
        when (status) {
            StepStatus.COMPLETED -> Box(
                modifier = Modifier.size(26.dp).clip(CircleShape).background(EquallColors.Blue),
                contentAlignment = Alignment.Center,
            ) {
                Text("✓", color = EquallColors.OnBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            StepStatus.CURRENT -> {
                Box(Modifier.size(28.dp).clip(CircleShape).background(EquallColors.Blue.copy(alpha = 0.15f)))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(EquallColors.CardSurface)
                        .border(2.dp, EquallColors.Blue, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("${index + 1}", color = EquallColors.Blue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            StepStatus.PENDING -> Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(EquallColors.CardSurface)
                    .border(2.dp, EquallColors.StepPending, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text("${index + 1}", color = EquallColors.StepPending, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun TimestampChip(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = EquallColors.InkSecondary,
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.chipRadius))
            .background(EquallColors.ChipNeutral)
            .padding(horizontal = Dimens.sm, vertical = Dimens.xs),
    )
}

@Composable
private fun ReassuranceFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardRadius))
            .dashedBorder(EquallColors.StepLine, Dimens.cardRadius)
            .padding(Dimens.lg),
        verticalArrangement = Arrangement.spacedBy(Dimens.sm),
    ) {
        SectionCaption("While you wait")
        Text(
            "We'll push you the decision when it's ready. No need to keep checking.",
            fontSize = 14.sp,
            color = EquallColors.InkSecondary,
        )
    }
}

private fun Modifier.dashedBorder(color: Color, radius: androidx.compose.ui.unit.Dp) = drawBehind {
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(radius.toPx()),
        style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))),
    )
}
