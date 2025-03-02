package com.tedmoon99.domain.intent.filter

sealed class FilterEvent {
    data class EnterFilter(val conceptId: Int): FilterEvent()
    data object ExitFilter: FilterEvent()
}