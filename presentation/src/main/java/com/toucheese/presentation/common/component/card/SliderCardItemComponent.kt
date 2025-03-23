package com.toucheese.presentation.common.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.tedmoon99.domain.usecase.studio.StudioUseCase.Companion.makeTruncation
import com.toucheese.presentation.R
import com.toucheese.presentation.common.component.carousel.CarouselComponent
import com.toucheese.presentation.common.component.chip.IconChipNoClickComponent

@Composable
fun SliderCardItemComponent(
    imageUrl: String,
    studioName: String,
    studioRating: Double,
    studioPrice: Int,
    @DrawableRes icon: Int,
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    val painter = rememberAsyncImagePainter(imageUrl)
    val state by painter.state.collectAsStateWithLifecycle()

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
        ) {

            // 상단
            Row(
                modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator()
                    }
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = stringResource(R.string.studio_profile_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(8.dp)
                                .clip(
                                    RoundedCornerShape(50.dp)
                                )
                        )
                    }
                    is AsyncImagePainter.State.Empty,
                    is AsyncImagePainter.State.Error -> {
                        Image(
                            painter = painterResource(R.drawable.logo_toucheese),
                            contentDescription = stringResource(R.string.studio_profile_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(8.dp)
                                .clip(
                                    RoundedCornerShape(50.dp)
                                )
                        )
                    }
                }


                Spacer(modifier= Modifier.width(4.dp))

                Text(
                    text = studioName,
                )

                Spacer(modifier= Modifier.weight(1f))

                Icon(
                    painter = painterResource(icon),
                    contentDescription = null
                )
            }

            // 중단
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {

                // 평점
                if (studioRating != 0.0){
                    IconChipNoClickComponent(
                        label = studioRating.toString(),
                        leadingIcon = R.drawable.icon_star
                    )
                }
                // 가격
                if (studioPrice != 0) {
                    IconChipNoClickComponent(
                        label = makeTruncation(studioPrice),
                        leadingIcon = R.drawable.icon_credit_card
                    )
                }

            }

            // 하단
            CarouselComponent(
                imageUrlList = imageUrls,
                modifier = Modifier.padding()
            )
        }
    }
}