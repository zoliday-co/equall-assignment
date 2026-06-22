package com.equall.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.equall.assignment.theme.Dimens
import com.equall.assignment.theme.EquallColors

@Composable
fun EquallCard(
    modifier: Modifier = Modifier,
    color: Color = EquallColors.CardSurface,
    border: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(Dimens.lg),
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(Dimens.cardRadius)
    Column(
        modifier = modifier
            .clip(shape)
            .background(color)
            .then(
                if (border) Modifier.border(1.dp, EquallColors.CardBorder, shape)
                else Modifier,
            )
            .padding(contentPadding),
        content = content,
    )
}
