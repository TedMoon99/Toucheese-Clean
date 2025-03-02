package com.toucheese.presentation.ui.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.presentation.R

@Composable
fun CarouselItemComponent(imageUrl: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(imageUrl)
    val state by painter.state.collectAsStateWithLifecycle()

    when (state) {
        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator()
        }
        is AsyncImagePainter.State.Success -> {
            Card(
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier.width(92.dp).height(120.dp)
                )
            }
        }
        is AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Error -> {
            Card(
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_toucheese),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier.width(92.dp).height(120.dp)
                )
            }
        }
    }
}