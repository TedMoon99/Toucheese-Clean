package com.toucheese.presentation.ui.component.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageCardComponent(
    @DrawableRes image: Int,
    @StringRes imageDescription: Int,
    imageNumber: Int,
    showLabel: Boolean = false,
    brushColors: List<Color> = listOf(Color.Transparent, Color.Black),
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        onClick = onCardClick,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = stringResource(imageDescription, "$imageNumber"),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.TopStart
            )

            if (showLabel){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(brushColors),
                            shape = RectangleShape,
                        )
                ) {
                    Text(
                        text = stringResource(imageDescription),
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}

