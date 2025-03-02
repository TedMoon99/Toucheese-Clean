package com.toucheese.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.tedmoon99.domain.usecase.member.MemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberUseCase: MemberUseCase
): ViewModel() {

    val signOutState = memberUseCase.getSignOutState()

}