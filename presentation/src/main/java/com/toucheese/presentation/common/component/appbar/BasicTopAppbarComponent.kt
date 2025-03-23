package com.toucheese.presentation.common.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toucheese.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppbarComponent(
    title: String,
    @DrawableRes leadingIcon: Int,
    @DrawableRes trailingIcon: Int,
    showLeadingIcon: Boolean = false,
    showTrailingIcon: Boolean = false,
    modifier: Modifier = Modifier,
    onClickLeadingIcon: (() -> Unit)? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = modifier,
            )
        },
        navigationIcon = {
            if (showLeadingIcon && onClickLeadingIcon != null) {
                IconButton(onClick = onClickLeadingIcon) {
                    Icon(
                        painter = painterResource(leadingIcon),
                        contentDescription = stringResource(R.string.topbar_image_leadingIcon),
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                // 빈 공간 차지
                IconButton(
                    enabled = false,
                    onClick = {}
                ) { }
            }
        },
        actions = {
            if (showTrailingIcon && onClickTrailingIcon != null) {
                IconButton(onClick = onClickTrailingIcon) {
                    Icon(
                        painter = painterResource(trailingIcon),
                        contentDescription = stringResource(R.string.topbar_image_trailingIcon),
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                // 빈 공간 차지
                IconButton(
                    enabled = false,
                    onClick = {}
                ) { }
            }
        }
    )

}