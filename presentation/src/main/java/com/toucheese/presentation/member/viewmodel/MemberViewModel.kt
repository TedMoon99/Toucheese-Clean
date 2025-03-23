package com.toucheese.presentation.member.viewmodel

import androidx.lifecycle.ViewModel
import com.tedmoon99.domain.member.usecase.MemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberUseCase: MemberUseCase
): ViewModel() {

    val signOutState = memberUseCase.getSignOutState()

}