package com.toucheese.presentation.member.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toucheese.presentation.R
import com.toucheese.presentation.common.component.appbar.BasicTopAppbarComponent
import com.toucheese.presentation.common.component.button.ButtonComponent
import com.toucheese.presentation.common.component.textfield.OutlinedTextFieldErrorComponent
import com.toucheese.presentation.member.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onLeadingIconClicked: () -> Unit,
    onNextClicked: (String, String) -> Unit,
) {

    val coroutine = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val emailState by viewModel.emailState.collectAsStateWithLifecycle()
    val isValidateEmail by viewModel.isValidateEmail.collectAsStateWithLifecycle()
    val passwordState by viewModel.passwordState.collectAsStateWithLifecycle()
    val isValidatePassword by viewModel.isValidatePassword.collectAsStateWithLifecycle()
    val matchingPasswordState by viewModel.matchingPasswordState.collectAsStateWithLifecycle()
    val isMatchingPassword by viewModel.isMatchingPassword.collectAsStateWithLifecycle()



    Scaffold(
        topBar = {
            BasicTopAppbarComponent(
                title = stringResource(R.string.title_sign_up),
                leadingIcon = R.drawable.icon_arrow_back,
                trailingIcon = R.drawable.icon_arrow_back,
                showLeadingIcon = true,
                showTrailingIcon = false,
                modifier = Modifier.fillMaxWidth(),
                onClickLeadingIcon = onLeadingIconClicked
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 24.dp),
            ) {
                ButtonComponent(
                    label = R.string.label_next,
                    enabled = isValidateEmail && isValidatePassword && isMatchingPassword,
                    buttonShape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        keyboardController?.hide() // 키보드 닫기
                        val email = emailState
                        val password = passwordState
                        onNextClicked(email, password)
                    },
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() }
                .padding(innerPadding)
        ) {

            // 안내문
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "로그인을 위한\n" +
                            "이메일과 비밀번호를 입력하세요",

                )
            }

            // 이메일
            item {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "이메일"
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextFieldErrorComponent(
                    inputText = emailState,
                    placeHolder = R.string.placeholder_email,
                    isError = emailState.isNotEmpty() && !isValidateEmail,
                    errorMessage = R.string.error_message_email,
                    cornerShape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    onValueChange = {
                        viewModel.setEmail(it)
                        coroutine.launch {
                            bringIntoViewRequester.bringIntoView() // 입력 시 자동 스크롤
                        }
                    }
                )
            }

            // 비밀번호
            item {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "비밀번호"
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextFieldErrorComponent(
                    inputText = passwordState,
                    placeHolder = R.string.placeholder_password,
                    isError = passwordState.isNotEmpty() && !isValidatePassword,
                    errorMessage = R.string.error_message_password,
                    cornerShape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        viewModel.setPassword(it)
                        coroutine.launch {
                            bringIntoViewRequester.bringIntoView() // 입력 시 자동 스크롤
                        }
                    }
                )
            }

            // 비밀번호 확인
            item {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "비밀번호 확인"
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextFieldErrorComponent(
                    inputText = matchingPasswordState,
                    placeHolder = R.string.placeholder_password_double_check,
                    isError = matchingPasswordState.isNotEmpty() && !isMatchingPassword,
                    errorMessage = R.string.error_message_password_double_check,
                    cornerShape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        viewModel.setMatchingPassword(it)
                        coroutine.launch {
                            bringIntoViewRequester.bringIntoView() // 입력 시 자동 스크롤
                        }
                    }
                )
            }
        }
    }
}