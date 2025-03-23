package com.toucheese.presentation.common.component.button

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxButtonComponent(
    checked: Boolean = false,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    onCheckChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.clickable {
            onCheckChange(!checked)
        },
    ) {
        // CheckBox
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            modifier = modifier,
        )

        Spacer(modifier = Modifier.width(1.dp))

        // label
        Text(
            text = stringResource(label),
            modifier = modifier
        )
    }
}