package com.toucheese.presentation.common.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.toucheese.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTopAppbarComponent(
    @DrawableRes titleImage: Int,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    showLeadingIcon: Boolean = false,
    showTrailingIcon: Boolean = false,
    modifier: Modifier = Modifier,
    onClickLeadingIcon: (() -> Unit)? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
) {

    TopAppBar(
        title = {
            Image(
                painter = painterResource(titleImage),
                contentDescription = stringResource(R.string.topbar_image_trailingIcon),
                modifier = modifier,
                contentScale = ContentScale.Fit
            )
        },
        navigationIcon = {
            if (showLeadingIcon && onClickLeadingIcon != null) {
                IconButton(onClick = onClickLeadingIcon) {
                    Icon(
                        imageVector = leadingIcon ?: Icons.Default.Close,
                        contentDescription = stringResource(R.string.topbar_image_leadingIcon)
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
                        imageVector = trailingIcon ?: Icons.Default.Close, // 장바구니 버튼
                        contentDescription = stringResource(R.string.topbar_image_trailingIcon)
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