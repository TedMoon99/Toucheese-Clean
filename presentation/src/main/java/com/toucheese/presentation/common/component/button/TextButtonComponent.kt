package com.toucheese.presentation.common.component.button

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TextButtonComponent(
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = stringResource(label),
        modifier = modifier.clickable {
            onClick()
        }
    )
}