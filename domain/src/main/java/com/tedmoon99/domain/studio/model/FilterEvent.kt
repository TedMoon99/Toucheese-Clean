package com.tedmoon99.domain.studio.model

sealed class FilterEvent {
    data class EnterFilter(val conceptId: Int): FilterEvent()
    data object ExitFilter: FilterEvent()
}