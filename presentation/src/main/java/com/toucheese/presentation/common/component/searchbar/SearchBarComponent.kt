package com.toucheese.presentation.common.component.searchbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.toucheese.presentation.R

@Composable
fun SearchBarComponent(
    @DrawableRes leadingIcon: Int,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {

        Icon(
            ImageVector.vectorResource(leadingIcon),
            contentDescription = stringResource(R.string.searchbar_icon)
        )

        Spacer(modifier = Modifier.width(8.dp))


        Text(
            text = stringResource(placeholder)
        )
    }
}