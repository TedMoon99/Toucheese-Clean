package com.toucheese.presentation.common.component.carousel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toucheese.presentation.common.component.card.CarouselItemComponent

@Composable
fun CarouselComponent(
    imageUrlList: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(imageUrlList) {imageUrl ->
            CarouselItemComponent(imageUrl)
        }
    }
}