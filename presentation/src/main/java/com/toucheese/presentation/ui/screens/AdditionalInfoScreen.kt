package com.toucheese.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tedmoon99.domain.intent.member.UpdateInfoResult
import com.toucheese.presentation.R
import com.toucheese.presentation.ui.component.appbar.BasicTopAppbarComponent
import com.toucheese.presentation.ui.component.button.ButtonComponent
import com.toucheese.presentation.ui.component.textfield.OutlinedTextFieldErrorComponent
import com.toucheese.presentation.ui.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdditionalInfoScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    hostState: SnackbarHostState,
    onNextClicked: (UpdateInfoResult) -> Unit,
) {

    val coroutine = rememberCoroutineScope()
    val nameState by viewModel.nameState.collectAsStateWithLifecycle() // 이름
    val contactState by viewModel.contactState.collectAsStateWithLifecycle() // 연락처
    val isValidateName by viewModel.isValidateName.collectAsStateWithLifecycle() // 이름 유효성
    val isValidateContact by viewModel.isValidateContact.collectAsStateWithLifecycle() // 연락처 유효성
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        },
        topBar = {
            BasicTopAppbarComponent(
                title = stringResource(R.string.label_additioinalInfo),
                leadingIcon = R.drawable.icon_info, // 안 보임
                trailingIcon = R.drawable.icon_info, // 안 보임
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
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
                    label = R.string.label_complete,
                    enabled = isValidateName && isValidateContact,
                    buttonShape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        keyboardController?.hide() // 키보드 닫기
                        viewModel.requestUpdate(onNextClicked)
                    },
                )
            }
        }

    ) { innerPadding ->

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() } // 빈 화면 터치 시 키보드 내림
                .padding(innerPadding)
                .imePadding(),

            ) {

            // 로고
            item {
                // 빈 공간
                Spacer(modifier = Modifier.height(84.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                ){
                    Image(
                        painter = painterResource(R.drawable.logo_toucheese),
                        contentDescription = stringResource(R.string.toucheese_logo),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // 공지문
            item {
                // 빈 공간
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    text = "더 빠르고 편리한 가입을 위해\n" +
                            "카카오 로그인 시 추가적인 정보가 필요합니다.\n" +
                            "이름과 전화번호를 입력해주세요.",
                    fontWeight = FontWeight.Bold
                )
            }

            // 이름
            item {
                // 빈 공간
                Spacer(modifier = Modifier.height(16.dp))

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
            }

            // 전화번호
            item {
                // 빈 공간
                Spacer(modifier = Modifier.height(16.dp))

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
}