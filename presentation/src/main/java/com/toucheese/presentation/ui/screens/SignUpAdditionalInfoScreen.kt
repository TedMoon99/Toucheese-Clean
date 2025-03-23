package com.toucheese.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toucheese.presentation.R
import com.toucheese.presentation.ui.component.appbar.BasicTopAppbarComponent
import com.toucheese.presentation.ui.component.button.ButtonComponent
import com.toucheese.presentation.ui.component.textfield.OutlinedTextFieldErrorComponent
import com.toucheese.presentation.ui.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpAdditionalInfoScreen(
    email: String,
    password: String,
    hostState: SnackbarHostState,
    viewModel: SignUpViewModel = hiltViewModel(),
    onLeadingIconClicked: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val coroutine = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val nameState by viewModel.nameState.collectAsStateWithLifecycle()
    val contactState by viewModel.contactState.collectAsStateWithLifecycle()
    val isValidateName by viewModel.isValidateName.collectAsStateWithLifecycle()
    val isValidateContact by viewModel.isValidateContact.collectAsStateWithLifecycle()


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
                    label = R.string.label_request_sign_up,
                    enabled = isValidateName && isValidateContact && true,
                    buttonShape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        keyboardController?.hide() // 키보드 닫기
                        val name = nameState
                        val contact = contactState
                        viewModel.requestSignUp(
                            email = email,
                            password = password,
                            name = name,
                            phone = contact,
                            callback = { result: Boolean ->
                                if (result) {
                                    navigateToHome()
                                } else {
                                    coroutine.launch {
                                        hostState.showSnackbar(
                                            message = "회원가입 실패.",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        )
                    },
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
                .padding(16.dp)
                .padding(innerPadding)
                .imePadding(),
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "본인 확인을 위한\n" +
                        "이름과 연락처를 입력해주세요.",

                )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "이름"
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextFieldErrorComponent(
                inputText = nameState,
                placeHolder = R.string.placeholder_name,
                isError = nameState.isNotEmpty() && !isValidateName,
                errorMessage = R.string.error_message_name,
                cornerShape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.setName(it)
                    coroutine.launch {
                        bringIntoViewRequester.bringIntoView() // 입력 시 자동 스크롤
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "전화번호"
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 전화번호
            OutlinedTextFieldErrorComponent(
                inputText = contactState,
                placeHolder = R.string.placeholder_phone,
                isError = contactState.isNotEmpty() && !isValidateContact,
                errorMessage = R.string.error_message_contact,
                cornerShape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.setContact(it)
                    coroutine.launch {
                        bringIntoViewRequester.bringIntoView() // 입력 시 자동 스크롤
                    }
                }
            )
        }
    }
}