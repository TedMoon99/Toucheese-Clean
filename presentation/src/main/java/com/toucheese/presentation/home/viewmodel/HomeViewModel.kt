package com.toucheese.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmoon99.domain.home.model.HomeEvent
import com.tedmoon99.domain.home.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {

                is HomeEvent.GetHome -> {
                    // 데이터 초기화
                    _state.value = _state.value.copy(conceptId = -1)
                }

                is HomeEvent.CardClick -> {
                    // 클릭한 카드 데이터 입력
                    _state.value = _state.value.copy(conceptId = event.conceptId)
                    Log.d(TAG, "클릭한 카드 id: ${event.conceptId}")
                }
                else -> {

                }
            }
        }
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }
}