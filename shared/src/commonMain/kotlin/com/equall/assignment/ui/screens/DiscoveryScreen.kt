package com.equall.assignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.equall.assignment.model.ExploreProduct
import com.equall.assignment.model.LoanLifecycle
import com.equall.assignment.model.User
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors
import com.equall.assignment.ui.components.EquallCard
import com.equall.assignment.ui.components.PillButton
import com.equall.assignment.ui.components.SectionCaption
import com.equall.assignment.ui.components.StatusPill
import com.equall.assignment.ui.components.TrustIqRow
import com.equall.assignment.util.toInr

@Composable
fun DiscoveryScreen(
    user: User,
    state: LoanLifecycle.Discovery,
    onCheckEligibility: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.lg),
        verticalArrangement = Arrangement.spacedBy(Dimens.lg),
    ) {
        GreetingCard(userName = user.name)
        PreQualifiedCard(
            amount = state.preQualifiedAmount,
            tenureMonths = state.tenureMonths,
            onCheckEligibility = onCheckEligibility,
        )

        Column(verticalArrangement = Arrangement.spacedBy(Dimens.md)) {
            SectionCaption("Explore products")
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.md)) {
                state.exploreProducts.forEach { product ->
                    ProductTile(product = product, modifier = Modifier.weight(1f))
                }
            }
        }

        TrustIqRow(trustIq = user.trustIq)
    }
}

@Composable
private fun GreetingCard(userName: String) {
    EquallCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Dimens.lg, vertical = Dimens.xl),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.sm),
        ) {
            Box(
                modifier = Modifier.size(84.dp).clip(CircleShape).background(EquallColors.ChipNeutral),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = userName.take(1).uppercase(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = EquallColors.InkPrimary,
                )
            }
            Text("Hi, $userName", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = EquallColors.InkPrimary)
            Text(
                "Let's get you set up with your first loan.",
                fontSize = 15.sp,
                color = EquallColors.InkSecondary,
            )
        }
    }
}

@Composable
private fun PreQualifiedCard(amount: Long, tenureMonths: Int, onCheckEligibility: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardRadius))
            .background(EquallColors.Green),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 50.dp, y = (-40).dp)
                .size(190.dp)
                .clip(CircleShape)
                .background(EquallColors.GreenOverlay),
        )
        Column(
            modifier = Modifier.padding(Dimens.lg),
            verticalArrangement = Arrangement.spacedBy(Dimens.sm),
        ) {
            StatusPill(
                text = "NEW · PRE-QUALIFIED",
                background = Color.White.copy(alpha = 0.18f),
                contentColor = EquallColors.OnGreen,
            )
            Spacer(Modifier.height(Dimens.xs))
            Text("You're pre-qualified for", fontSize = 16.sp, color = EquallColors.OnGreen)
            Text(amount.toInr(), fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = EquallColors.OnGreen)
            Text(
                "Personal loan · $tenureMonths months · Indicative",
                fontSize = 14.sp,
                color = EquallColors.OnGreenMuted,
            )
            Spacer(Modifier.height(Dimens.sm))
            PillButton(
                text = "Check eligibility  →",
                onClick = onCheckEligibility,
                background = Color.White,
                contentColor = EquallColors.Green,
            )
        }
    }
}

@Composable
private fun ProductTile(product: ExploreProduct, modifier: Modifier = Modifier) {
    EquallCard(modifier = modifier, contentPadding = PaddingValues(Dimens.md)) {
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.sm)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.chipRadius))
                    .background(EquallColors.BrandIndigo.copy(alpha = 0.12f))
                    .padding(horizontal = Dimens.sm, vertical = Dimens.xs),
            ) {
                Text(product.code, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EquallColors.BrandIndigo)
            }
            Text(product.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = EquallColors.InkPrimary)
            Text(product.subtitle, fontSize = 13.sp, color = EquallColors.InkSecondary)
        }
    }
}
