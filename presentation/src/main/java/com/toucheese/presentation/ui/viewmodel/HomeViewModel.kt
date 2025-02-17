package com.toucheese.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tedmoon99.domain.entity.remote.concept.studios.StudioEntity
import com.tedmoon99.domain.usecase.studio.StudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val studioUseCase: StudioUseCase
) : ViewModel() {



    private val _studioState = MutableStateFlow<PagingData<StudioEntity>>(value = PagingData.empty())
    val studioState: Flow<PagingData<StudioEntity>> = _studioState

    init {
        onEvent(HomeEvent.GetHome)
    }

    private fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getStudio(1)
                }
            }
        }

    }

    private suspend fun getStudio(conceptId: Int) {
        studioUseCase.getStudio(conceptId)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect{
                _studioState.value = it
            }
    }


}


sealed class HomeEvent {
    data object GetHome: HomeEvent()
}

data class HomeState(
    val studios: List<StudioEntity> = emptyList()
)
