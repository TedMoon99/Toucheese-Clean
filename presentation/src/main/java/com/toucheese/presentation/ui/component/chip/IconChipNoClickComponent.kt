package com.toucheese.presentation.ui.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toucheese.presentation.R

@Composable
fun IconChipNoClickComponent(
    label: String,
    @DrawableRes leadingIcon: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(1.dp, colorResource(R.color.border_stroke)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_chip))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.wrapContentWidth().padding(vertical = 4.dp, horizontal = 8.dp),
        ) {
            // Icon
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = null,
            )
            Spacer(modifier= Modifier.width(2.dp))
            // Label
            Text(
                text = label,
            )
        }
    }
}
