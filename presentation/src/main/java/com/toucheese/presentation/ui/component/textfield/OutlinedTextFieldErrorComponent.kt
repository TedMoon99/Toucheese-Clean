package com.toucheese.presentation.ui.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldErrorComponent(
    inputText: String,
    @StringRes placeHolder: Int,
    @StringRes errorMessage: Int,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    singleLine: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = inputText,
        placeholder = {
            Text(
                text = stringResource(placeHolder),
                modifier = modifier
            )
        },
        isError = isError,
        supportingText = {
            if (isError){
                Text(
                    text = stringResource(errorMessage),
                    modifier = modifier
                )
            }
        },
        shape = cornerShape,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        modifier = modifier,
        onValueChange = onValueChange,

        )

}