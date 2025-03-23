package com.toucheese.presentation.studio.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tedmoon99.domain.entity.remote.studio.StudioEntity
import com.tedmoon99.domain.intent.filter.FilterEvent
import com.tedmoon99.domain.usecase.studio.StudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioFilterViewModel @Inject constructor(
    private val studioUseCase: StudioUseCase,
): ViewModel() {

    private val _conceptName = MutableStateFlow("")
    val conceptName: StateFlow<String> = _conceptName

    private val _studioListState = MutableStateFlow<PagingData<StudioEntity>>(value = PagingData.empty())
    val studioListState: Flow<PagingData<StudioEntity>> = _studioListState

    fun onEvent(event: FilterEvent) {
        viewModelScope.launch {
            when (event) {
                is FilterEvent.EnterFilter -> {
                    // 클릭한 concept 조회
                    val conceptId = event.conceptId
                    // 선택한 conceptId 데이터 조회
                    getData(conceptId)
                }

                is FilterEvent.ExitFilter -> {
                    removeData()
                }

                else -> {

                }
            }
        }

    }

    private suspend fun getData(conceptId: Int) {
        val conceptName = studioUseCase.getConceptName(conceptId)
        _conceptName.value = conceptName
        Log.d(TAG, "선택한 컨셉 이름: ${conceptName}")
        Log.d(TAG, "선택한 컨셉: ${conceptId}")
        studioUseCase.getStudio(conceptId)
            .distinctUntilChanged() // 중복 데이터 제거
            .cachedIn(viewModelScope) // 데이터를 캐싱하여 재사용 설정
            .collectLatest { pagingData -> // 최신 데이터만 반영
                _studioListState.value = pagingData
            }
    }

    private fun removeData() {
        _conceptName.value = ""
        _studioListState.value = PagingData.empty()
    }

    companion object{
        private const val TAG = "StudioFilterViewModel"
    }
}