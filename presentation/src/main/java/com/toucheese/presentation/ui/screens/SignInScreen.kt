package com.toucheese.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.toucheese.presentation.R
import com.toucheese.presentation.ui.component.button.ButtonComponent
import com.toucheese.presentation.ui.component.button.CheckBoxButtonComponent
import com.toucheese.presentation.ui.component.button.TextButtonComponent
import com.toucheese.presentation.ui.component.textfield.OutlinedTextFieldComponent

@Composable
fun SignInScreen(

    hostState: SnackbarHostState,
    onSignInClicked: () -> Unit,
    onKakaoSignInClicked: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = hostState)

        }
    ) { innerPadding ->

        LazyColumn(

            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // 빈 공간
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }

            // 안내 문구
            item {
                Image(
                    painter = painterResource(R.drawable.toucheese_text),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.fillMaxWidth(0.6f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.text_introduce1),

                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.text_introduce2)
                )

            }

            // 빈 공간
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }

            // 입력 필드
            item {
                OutlinedTextFieldComponent(
                    inputText = "",
                    placeHolder = R.string.placeholder_email,
                    cornerShape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    onValueChange = {

                    }
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextFieldComponent(
                    inputText = "",
                    placeHolder = R.string.placeholder_password,
                    cornerShape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    onValueChange = {

                    }
                )

                // 빈 공간
                Spacer(modifier = Modifier.height(8.dp))

                // 자동 로그인, 회원가입, ID 및 password 찾기
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 자동 로그인
                    CheckBoxButtonComponent(
                        checked = false,
                        label = R.string.label_autoSignIn,
                        modifier = Modifier.padding(1.dp),
                        onCheckChange = { }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // 회원가입
                    TextButtonComponent(
                        label = R.string.label_signUp,
                        onClick = {}
                    )
                    Text(
                        text = "/"
                    )
                    // ID 및 password 찾기
                    TextButtonComponent(
                        label = R.string.label_findAccount,
                        onClick = {}
                    )
                }
            }


            // 로그인 버튼
            item {
                // 빈 공간
                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(
                    label = R.string.label_signIn,
                    enabled = true,
                    buttonShape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }
                )
            }

            // SNS 로그인 버튼
            item {
                Spacer(modifier = Modifier.height(85.dp))

                ButtonComponent(
                    label = R.string.label_kakao_signIn,
                    enabled = true,
                    buttonShape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }
                )
            }




        }

    }

}